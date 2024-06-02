package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitCreationDto;
import ru.practicum.dto.HitDto;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    public ResponseEntity<String> addHit(@RequestBody HitCreationDto dto) {
        log.info("Была посещена ссылка {}", dto);
        service.addHit(dto);
        return new ResponseEntity<>("Информация сохранена", HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<HitDto> getStats(@RequestParam(name = "start")
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                           @RequestParam(name = "end")
                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                           @RequestParam(name = "uris", required = false) List<String> uris,
                           @RequestParam(name = "unique", required = false, defaultValue = "false") boolean unique) {
        log.info("Запрос на получение статистики с параметрами start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);
        return service.getStats(start, end, uris, unique);
    }

}
