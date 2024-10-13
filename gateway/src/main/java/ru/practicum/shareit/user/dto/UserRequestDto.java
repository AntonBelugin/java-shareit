package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.practicum.shareit.validation.ValidationMarker;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    @NotNull(groups = ValidationMarker.OnCreate.class)
    @Email(groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
    @Length(max = 512, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
    String email;
    @NotBlank(groups = ValidationMarker.OnCreate.class)
    @Length(max = 255, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
    String name;
}