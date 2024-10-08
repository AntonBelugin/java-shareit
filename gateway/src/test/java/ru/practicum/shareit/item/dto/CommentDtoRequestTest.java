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
public class CommentDtoRequestTest {
    private final JacksonTester<CommentDtoRequest> json;

    @Test
    @SneakyThrows
    void testCommentDtoRequest() {
        String text = "comment";
        CommentDtoRequest commentDto = CommentDtoRequest.builder()
                .text(text)
                .build();

        JsonContent<CommentDtoRequest> jsonContent = json.write(commentDto);

        assertThat(jsonContent).extractingJsonPathStringValue("$.text").isEqualTo(text);
    }

}
