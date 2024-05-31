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
import java.util.List;

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
        if (!uris.isEmpty()) {
            List<HitDto> result = new ArrayList<>();
            for (String uri : uris) {
                List<HitDto> dtos = repository.findByUri(start, end, uri);
                if (!dtos.isEmpty()) {
                    HitDto dto = dtos.get(0);
                    result.add(dto);
                }
            }
            return  result;
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
