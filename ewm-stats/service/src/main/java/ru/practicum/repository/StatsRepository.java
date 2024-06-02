package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.Hit;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT h.app as app, h.uri as uri, COUNT(h.ip) as hits" +
            " from Hit h where h.timestamp > ?1 and h.timestamp < ?2" +
            " GROUP BY h.app, h.uri" +
            " ORDER BY hits DESC")
    List<HitDto> findAllInInterval(LocalDateTime start, LocalDateTime end);

    @Query("SELECT h.app as app, h.uri as uri, COUNT(DISTINCT(h.ip)) as hits" +
            " from Hit h where h.timestamp > ?1 and h.timestamp < ?2" +
            " GROUP BY h.app, h.uri" +
            " ORDER BY hits DESC")
    List<HitDto> findAllUniqueInInterval(LocalDateTime start, LocalDateTime end);

    @Query("SELECT h.app as app, h.uri as uri, COUNT(h.ip) as hits" +
            " from Hit h where h.timestamp > ?1 and h.timestamp < ?2 and uri = ?3" +
            " GROUP BY h.app, h.uri" +
            " ORDER BY hits DESC")
    List<HitDto> findByUri(LocalDateTime start, LocalDateTime end, String uri);

    @Query("SELECT h.app as app, h.uri as uri, COUNT(DISTINCT(h.ip)) as hits" +
            " from Hit h where h.timestamp > ?1 and h.timestamp < ?2 and uri = ?3" +
            " GROUP BY h.app, h.uri" +
            " ORDER BY hits DESC")
    List<HitDto> findByUriUnique(LocalDateTime start, LocalDateTime end, String uri);
}
