package com.softee5.mobil2team.service;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.TagDto;
import com.softee5.mobil2team.dto.TagListDto;
import com.softee5.mobil2team.entity.Post;
import com.softee5.mobil2team.entity.Tag;
import com.softee5.mobil2team.repository.PostRepository;
import com.softee5.mobil2team.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    PostRepository postRepository;

    /* 역 별 태그 리스트 */
    public DataResponseDto<TagListDto> getTagList(Long id) {
        // tag 목록 전체 조회
        List<Tag> tagList = tagRepository.findAll();

        // 현재 시간에서 2시간 전 계산
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        Date date = calendar.getTime();

        // 2시간 전 ~ 현재 시간까지 작성된 글 리스트 불러오기
        List<Post> posts = postRepository.findAllByStationIdAndCreatedDatetimeAfter(id, date);

        List<TagDto> results = new ArrayList<>();

        // 게시글이 있는 경우
        if(!posts.isEmpty()) {
            // 태그별 카운팅
            Map<Tag, Long> tagCountMap = posts.stream()
                    .collect(Collectors.groupingBy(Post::getTag, Collectors.counting()));

            // 태그 오름차순으로 정렬
            results.addAll(tagCountMap.entrySet().stream()
                    .sorted((tag1, tag2) -> {
                        // 카운트 수 높은 순으로 정렬
                        int compareByCount = tag2.getValue().compareTo(tag1.getValue());

                        // 카운트 수 동일할 경우 id 오름차순으로 정렬
                        if (compareByCount == 0) {
                            return Long.compare(tag1.getKey().getId(), tag2.getKey().getId());
                        }
                        return compareByCount;
                    })
                    .map(tag -> new TagDto(tag.getKey().getId(), tag.getKey().getName()))
                    .toList());
        }

        // 남은 tag 추가
        for (Tag t : tagList) {
            TagDto dto = new TagDto(t.getId(), t.getName());
            if(!results.contains(dto)) {
                results.add(dto);
            }
        }

        return DataResponseDto.of(new TagListDto(results));
    }

}
