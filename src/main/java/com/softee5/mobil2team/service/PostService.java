package com.softee5.mobil2team.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.softee5.mobil2team.config.GeneralException;
import com.softee5.mobil2team.config.ResponseCode;
import com.softee5.mobil2team.dto.*;
import com.softee5.mobil2team.entity.Image;
import com.softee5.mobil2team.entity.Post;
import com.softee5.mobil2team.entity.Station;
import com.softee5.mobil2team.entity.Tag;
import com.softee5.mobil2team.repository.ImageRepository;
import com.softee5.mobil2team.repository.PostRepository;
import com.softee5.mobil2team.repository.StationRepository;
import com.softee5.mobil2team.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static String[][] nickname_modifier = {
            { "출근하기 싫은", "연차 쓰고 싶은" },
            { "칼퇴에 신난", "발걸음이 빨라진" },
            { "야근으로 피곤한", "침대에 눕고 싶은" },
            { "통학에 지친", "자취하고 싶은" },
            { "밤새서 피곤한", "과제에 뒤덮인" },
            { "늦잠자버린", "비몽사몽한" },
            { "숙취에 찌든", "술에 취한" },
            { "배고파서 어지러운", "배에서 소리나는" },
            { "에어팟 끼고있는", "버즈 끼고있는" },
            { "지옥철이 답답한", "당장 내리고 싶은" },
            { "깜짝놀란", "생생정보통" }
    };
    private static String[] nickname_noun = {
            "회사원", "대학생", "박명수", "정준하", "유재석", "하하", "정형돈", "노홍철", "광희", "짱구", "짱아"
    };

    /* 글 업로드 */
    public DataResponseDto<Void> uploadPost(PostDto postDto, MultipartFile file) {

        try {
            Post post = new Post();

            // 이미지 s3에 저장
            String imageUrl = null;
            if (file != null && !file.isEmpty()) {
                imageUrl = saveFile(file);
            }

            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            int idx = postDto.getTagId() != null ? postDto.getTagId().intValue() - 1 : random.nextInt(11);
            int idxModifier = random.nextInt(2);
            int idxNoun = random.nextInt(nickname_noun.length);
            String nickname = nickname_modifier[idx][idxModifier] + " " + nickname_noun[idxNoun];

            post.setNickname(nickname);
            post.setContent(postDto.getContent());
            post.setLiked(0);

            post.setStation(Station.builder().id(postDto.getStationId()).build()); // 필수
            post.setTag(postDto.getTagId() != null ? Tag.builder().id(postDto.getTagId()).build() : null); // 선택
            post.setImage(postDto.getImageId() != null ? Image.builder().id(postDto.getImageId()).build() : null); // 선택
            post.setImageUrl(imageUrl);

            postRepository.save(post);

            return DataResponseDto.of(null);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(
                new PutObjectRequest(bucket, originalFilename, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    /* 이미지 리스트 조회 */
    public DataResponseDto<ImageListDto> getAllImages() {

        List<Image> imageList = imageRepository.findAll();

        // stream 사용
        List<ImageDto> dtoList = imageList
                .stream()
                .map(d -> new ImageDto(d.getId(), d.getImageUrl()))
                .collect(Collectors.toList());

        return DataResponseDto.of(new ImageListDto(dtoList));
    }

    /* 좋아요 추가 */
    public boolean updateLiked(LikeDto likeDto) {
        // 게시글 id로 포스트 조회
        Optional<Post> post = postRepository.findById(likeDto.getId());

        // 게시글 있을 경우 좋아요 수 업데이트
        if (post.isPresent()) {
            int liked = post.get().getLiked();
            post.get().setLiked(liked + likeDto.getCount());

            postRepository.save(post.get());

            return true;
        }

        return false;
    }

    /* 게시글 리스트 조회 */
    public PageResponseDto<PostListDto> getPostList(Long stationId, Integer pageSize, Integer pageNumber, Long tagId) {
        // stationID 예외처리
        if (!stationRepository.existsById(stationId)) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 역 ID입니다.");
        }

        // 페이지 넘버 예외처리
        if(pageNumber <= 0) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "잘못된 페이지 번호입니다.");
        }

        /* pageable 객체 생성 */
        Pageable pageable = PageRequest.of(pageNumber - 1,
                pageSize, Sort.by("createdDatetime").descending());

        // 현재 시간에서 24시간 전 계산
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date date = calendar.getTime();


        Page<Post> postList;
        if (tagId != null) {
            // stationId & tagId로 포스트 조회
            postList = postRepository.findByStationIdAndTagIdAndCreatedDatetimeAfter(stationId, tagId, pageable, date);

        } else {
            // stationId로 포스트 조회
            postList = postRepository.findByStationIdAndCreatedDatetimeAfter(stationId, pageable, date);
        }

        List<PostInfoDto> results = new ArrayList<>();

        for (Post p : postList) {
            String imageUrl = p.getImage() != null ? p.getImage().getImageUrl() : null;
            Long tag = p.getTag() != null ? p.getTag().getId() : null;
            PostInfoDto postInfoDto = new PostInfoDto(p.getId(), p.getNickname(), p.getCreatedDatetime(),
                    p.getContent(), imageUrl, tag, p.getLiked());
            results.add(postInfoDto);
        }

        PageInfoDto pageInfoDto = new PageInfoDto(pageNumber, pageSize, postList.getTotalElements(), postList.getTotalPages());
        return PageResponseDto.of(new PostListDto(results), pageInfoDto);
    }

}
