package com.softee5.mobil2team.service;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.ImageDto;
import com.softee5.mobil2team.dto.ImageListDto;
import com.softee5.mobil2team.dto.PostDto;
import com.softee5.mobil2team.entity.Image;
import com.softee5.mobil2team.entity.Post;
import com.softee5.mobil2team.entity.Station;
import com.softee5.mobil2team.entity.Tag;
import com.softee5.mobil2team.repository.ImageRepository;
import com.softee5.mobil2team.repository.PostRepository;
import com.softee5.mobil2team.repository.StationRepository;
import com.softee5.mobil2team.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class PostService {

    private final StationRepository stationRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    private static String[] nicknameFirst = { "졸린", "피곤한", "집 가고 싶은", "배고픈" };
    private static String[] nicknameSecond = { "츄파춥스", "하이츄", "스니커즈", "황도 알맹이" };

    /* 글 업로드 */
    public DataResponseDto<Void> uploadPost(PostDto postDto) {

        Post post = new Post();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int idx1 = random.nextInt(nicknameFirst.length);
        int idx2 = random.nextInt(nicknameSecond.length);
        String nickname = nicknameFirst[idx1] + " " + nicknameSecond[idx2];

        post.setNickname(nickname);
        post.setContent(postDto.getContent());
        post.setLiked(0);

        Station station = stationRepository.findById(postDto.getStationId()).orElse(null);
        Tag tag = tagRepository.findById(postDto.getTagId()).orElse(null);
        Image image;
        if (postDto.getImageId() != null) {
            image = imageRepository.findById(postDto.getImageId()).orElse(null);
        } else {
            image = null;
        }
        post.setStation(station);
        post.setTag(tag);
        post.setImage(image);

        postRepository.save(post);

        return DataResponseDto.of(null);
    }

    /* 이미지 리스트 조회 */
    public DataResponseDto<ImageListDto> getAllImages() {

        List<Image> imageList = imageRepository.findAll();

        List<ImageDto> dtoList = new ArrayList<>();
        for (Image i : imageList) {
            ImageDto dto = new ImageDto(i.getId(), i.getImageUrl());
            dtoList.add(dto);
        }

        return DataResponseDto.of(new ImageListDto(dtoList));
    }

}
