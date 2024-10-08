package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.item.dto.*;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemControllerTest {
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;
    private static ItemDtoRequest itemDtoRequest;
    private static ItemDtoResponse itemDtoResponse;
    private static ItemDtoWithComments itemDtoWithComments;
    private static Long itemId;
    private static Long userId;

    @MockBean
    private ItemService itemService;

    @BeforeAll
    static void setUp() {
        itemId = 1L;
        userId = 1L;

        itemDtoRequest = ItemDtoRequest.builder()
                .name("name")
                .description("description")
                .available(true)
                .requestId(1L)
                .build();
        itemDtoResponse = ItemDtoResponse.builder()
                .id(itemId)
                .name("name")
                .description("description")
                .available(true)
                .build();
        itemDtoWithComments = ItemDtoWithComments.builder()
                .id(itemId)
                .name("name")
                .description("description")
                .available(true)
                .comments(new ArrayList<>())
                .build();

    }

    @Test
    @SneakyThrows
    void addTest() {
        Mockito.when(itemService.add(userId, itemDtoRequest)).thenReturn(itemDtoResponse);

       mockMvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDtoRequest))
                        .header(Constants.HEADER_USER, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(itemDtoResponse.getId()), Long.class))
               .andExpect(jsonPath("$.name", is(itemDtoResponse.getName())))
               .andExpect(jsonPath("$.description", is(itemDtoResponse.getDescription())))
               .andExpect(jsonPath("$.available", is(itemDtoResponse.getAvailable())));
    }

    @Test
    @SneakyThrows
    void findByIdTest() {
        Mockito.when(itemService.findById(itemId)).thenReturn(itemDtoWithComments);

        mockMvc.perform(get("/items/{id}", itemId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDtoWithComments.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDtoWithComments.getName())))
                .andExpect(jsonPath("$.description", is(itemDtoWithComments.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDtoWithComments.getAvailable())));
    }

    @Test
    @SneakyThrows
    void updateTest() {
        Mockito.when(itemService.update(userId, itemId, itemDtoRequest)).thenReturn(itemDtoResponse);

        mockMvc.perform(patch("/items/{id}", itemId)
                        .content(mapper.writeValueAsString(itemDtoRequest))
                        .header(Constants.HEADER_USER, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDtoResponse.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDtoResponse.getName())))
                .andExpect(jsonPath("$.description", is(itemDtoResponse.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDtoResponse.getAvailable())));
    }

    @Test
    @SneakyThrows
    void findItemsByUserTest() {
       List<ItemDtoWithComments> listItem = List.of(itemDtoWithComments);
        Mockito.when(itemService.findItemsByUser(userId)).thenReturn(listItem);

        mockMvc.perform(get("/items")
                        .header(Constants.HEADER_USER, userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(itemDtoWithComments))));
    }

    @Test
    @SneakyThrows
    void searchByTextTest() {
        String string = "desc";
        List<ItemDtoResponse> listItem = List.of(itemDtoResponse);
        Mockito.when(itemService.searchByText(string)).thenReturn(listItem);

        mockMvc.perform(get("/items/search?text={text}", string)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString
                        (Collections.singletonList(itemDtoResponse))));
    }

    @Test
    @SneakyThrows
    void createTest() {
        long commentId = 1L;
        String comment = "comment";
        String author = "author";
        LocalDateTime created = LocalDateTime.now();
        CommentDtoRequest commentDtoRequest = new CommentDtoRequest(comment);
        CommentDtoResponse commentDtoResponse = CommentDtoResponse.builder()
                .id(commentId)
                .text(comment)
                .authorName(author)
                .created(created)
                .build();

        Mockito.when(itemService.addComment(userId, itemId, commentDtoRequest)).thenReturn(commentDtoResponse);

        mockMvc.perform(post("/items/" + itemId + "/comment")
                        .content(mapper.writeValueAsString(commentDtoRequest))
                        .header(Constants.HEADER_USER, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDtoResponse.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(commentDtoResponse.getText())))
                .andExpect(jsonPath("$.authorName", is(commentDtoResponse.getAuthorName())))
                .andExpect(jsonPath("$.created", is(commentDtoResponse.getCreated()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
    }
}
