package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemDtoRequestTest {
    private final JacksonTester<ItemDtoRequest> json;

    @Test
    @SneakyThrows
    void testItemDtoRequest() {
        ItemDtoRequest itemRequestDtoRequest = ItemDtoRequest.builder()
                .name("name")
                .description("description")
                .available(true)
                .requestId(1L)
                .build();

        JsonContent<ItemDtoRequest> jsonContent = json.write(itemRequestDtoRequest);

        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("name");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.available").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.requestId").isEqualTo(1);
    }
}
