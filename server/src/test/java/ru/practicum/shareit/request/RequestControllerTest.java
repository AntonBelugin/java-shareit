package ru.practicum.shareit.request;

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
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.dto.RequestWithItemDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RequestController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestControllerTest {
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;
    private static RequestResponseDto requestResponseDto;
    private static RequestDto requestDto;
    private static RequestWithItemDto requestWithItemDto;
    private static Long requestId;
    private static Long userId;

    @MockBean
    private RequestService requestService;

    @BeforeAll
    static void setUp() {
        requestId = 1L;
        userId = 1L;
        String description = "description";
        LocalDateTime created = LocalDateTime.now();

        requestDto = new RequestDto(description);
        requestResponseDto = RequestResponseDto.builder()
                .id(requestId)
                .description(description)
                .created(created)
                .build();
        requestWithItemDto = RequestWithItemDto.builder()
                .id(requestId)
                .description(description)
                .created(created)
                .items(new ArrayList<>())
                .build();
    }

    @Test
    @SneakyThrows
    void addItemRequestTest() {
        Mockito.when(requestService.create(userId, requestDto)).thenReturn(requestResponseDto);

        mockMvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(requestDto))
                        .header(Constants.HEADER_USER, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestResponseDto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(requestResponseDto.getDescription())))
                .andExpect(jsonPath("$.created", is(requestResponseDto.getCreated()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
    }

    @Test
    @SneakyThrows
    void getRequestTest() {
        Mockito.when(requestService.getRequestById(requestId)).thenReturn(requestWithItemDto);

        mockMvc.perform(get("/requests/{requestId}", requestId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestWithItemDto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(requestWithItemDto.getDescription())))
                .andExpect(jsonPath("$.created", is(requestWithItemDto.getCreated()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
    }

    @Test
    @SneakyThrows
    void getRequestsTest() {
        List<RequestWithItemDto> list = List.of(requestWithItemDto);
        Mockito.when(requestService.getRequestsBySearcherId(userId, 0, 10)).thenReturn(list);

        mockMvc.perform(get("/requests")
                        .header(Constants.HEADER_USER, userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(requestWithItemDto))));
    }
}
