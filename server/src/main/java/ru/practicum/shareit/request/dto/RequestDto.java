package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class RequestDto {
    final String description;

    @JsonCreator
    public RequestDto(@JsonProperty("description") String description) {
        this.description = description;
    }
}

