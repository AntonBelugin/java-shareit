package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.practicum.shareit.validation.ValidationMarker;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class UserDto {
    long id;
    @NotEmpty(groups = ValidationMarker.OnCreate.class)
    @Email
    @Length(max = 512)
    String email;
    @NotBlank(groups = ValidationMarker.OnCreate.class)
    @Length(max = 255)
    String name;
}