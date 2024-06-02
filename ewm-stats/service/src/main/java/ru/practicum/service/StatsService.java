package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitCreationDto;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitMapper;
import ru.practicum.errors.exceptions.IncorrectDateException;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final StatsRepository repository;

    public String addHit(HitCreationDto dto) {
        repository.save(HitMapper.fromCreationDto(dto));
        return "Информация сохранена";
    }

    public List<HitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        validate(start, end);
        if (uris != null && !uris.isEmpty()) {
            List<HitDto> result = new ArrayList<>();
            for (String uri : uris) {
                List<HitDto> dtos;
                if (!unique) {
                    dtos = repository.findByUri(start, end, uri);
                } else {
                    dtos = repository.findByUriUnique(start, end, uri);
                }
                if (!dtos.isEmpty()) {
                    HitDto dto = dtos.get(0);
                    result.add(dto);
                }
            }
            return  result.stream().sorted(Comparator.comparing(HitDto::getHits).reversed()).collect(Collectors.toList());
        }
        if (!unique) {
            return repository.findAllInInterval(start, end);
        }
        return repository.findAllUniqueInInterval(start, end);
    }

    private void validate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IncorrectDateException("Дата начала не может быть после даты конца");
        }
    }
}
