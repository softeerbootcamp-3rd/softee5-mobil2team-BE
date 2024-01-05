package com.softee5.mobil2team.repository;

import com.softee5.mobil2team.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /* 해당 시간 이후 작성된 포스트 조회 */
    List<Post> findAllByStationIdAndCreatedDatetimeAfter(Long id, Date createdDatetime);
}
