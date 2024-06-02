package ru.practicum.dto;

public class HitMapper {

    public static Hit fromCreationDto(HitCreationDto dto) {
        return new Hit(-1, dto.getApp(), dto.getUri(), dto.getIp(), dto.getTimestamp());
    }
}
