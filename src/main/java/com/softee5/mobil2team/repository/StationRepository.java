package com.softee5.mobil2team.repository;

import com.softee5.mobil2team.dto.BriefInfoDto;
import com.softee5.mobil2team.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    @Query(value = "SELECT s.id AS stationId, " +
            "       s.name AS stationName, " +
            "       (SELECT p1.tag_id FROM post p1 WHERE p1.station_id = s.id AND p1.created_datetime >= CURRENT_TIMESTAMP - INTERVAL '2' HOUR GROUP BY p1.tag_id ORDER BY COUNT(p1.id) DESC LIMIT 1) AS tagId, " +
            "       COUNT(p.id) AS contentCount " +
            "FROM station s " +
            "LEFT JOIN post p ON s.id = p.station_id AND p.created_datetime >= CURRENT_TIMESTAMP - INTERVAL '2' HOUR " +
            "GROUP BY s.id, s.name", nativeQuery = true)
    List<BriefInfoDto.StationInfo> getBriefStationInfo();

    @Query(value = "SELECT id FROM station s " +
            "ORDER BY SQRT(POWER(s.location_x - :inputX, 2)" +
            "   + POWER(s.location_y - :inputY, 2)) " +
            "LIMIT :cnt", nativeQuery = true)
    List<Long> findNearestStations(@Param("inputX") double inputX, @Param("inputY") double inputY, @Param("cnt") int cnt);

    @Query(value = "SELECT s.id from Station s join Post p on p.station_id = s.id AND p.created_datetime >= CURRENT_TIMESTAMP - INTERVAL '2' HOUR"+
            "   group by s.id order by count(s.id) desc limit 3", nativeQuery = true)
    List<Long> getHotStations();

    @Query(value = "SELECT s.id FROM Station s ORDER BY s.id ASC")
    List<Long> findAllId();
}
