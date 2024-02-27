package com.example.hotelbookingassignment.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoomDto {
    private UUID id;
    private String name;
    private String section;
    private boolean available;

    public RoomDto(UUID id, String name, String section, boolean available) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.available = available;
    }
}
