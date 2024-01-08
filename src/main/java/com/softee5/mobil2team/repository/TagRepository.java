package com.softee5.mobil2team.repository;

import com.softee5.mobil2team.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT t.id from tag t " +
            "left outer join " +
            "(select p.tag_id as tagId, count(tag_id) as count " +
            "from post p " +
            "where p.station_id = :id " +
            "AND p.created_datetime >= CURRENT_TIMESTAMP - INTERVAL '2' HOUR " +
            "GROUP BY tag_id) as a " +
            "ON t.id = a.tagId " +
            "ORDER BY a.count desc", nativeQuery = true)
    List<Long> getTagList(@Param("id") Long id);
}
