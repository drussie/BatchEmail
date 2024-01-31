package com.example.email.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpResponseObject_whenSerializeToJson_thenCorrect() throws Exception {
        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp("2021-08-01T12:00:00.000+00:00")
                .statusCode(200)
                .status(null)
                .message("message")
                .developerMessage("developerMessage")
                .path("/path")
                .requestMethod("GET")
                .data(null)
                .build();

        String httpResponseJson = objectMapper.writeValueAsString(httpResponse);

        assertNotNull(httpResponseJson);
        String expectedJson = "{\"timeStamp\":\"2021-08-01T12:00:00.000+00:00\",\"statusCode\":200,\"message\":\"message\",\"developerMessage\":\"developerMessage\",\"path\":\"/path\",\"requestMethod\":\"GET\"}";
        assertEquals(expectedJson, httpResponseJson);
    }

}