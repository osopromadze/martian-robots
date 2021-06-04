package com.guidesmiths.martian_robot.web;

import com.guidesmiths.martian_robot.dto.InputOutputDto;
import com.guidesmiths.martian_robot.repository.InputOutputRepository;
import com.guidesmiths.martian_robot.service.RobotService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doCallRealMethod;

@WebFluxTest(value = {RobotController.class},
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@TestPropertySource(properties = {
        "size.max=10"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RobotControllerTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private RobotService robotService;

    @MockBean
    private InputOutputRepository inputOutputRepository;

    private WebTestClient testClient;

    @BeforeAll
    void init() {
        testClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .responseTimeout(Duration.ofMillis(900000))
                .build();
    }

    @Test
    void okForSampleInputTest() throws MalformedURLException {

        doCallRealMethod().when(robotService).moveRobots(anyList(), any(InputOutputDto.class));

        URL fileUrl = new URL("file:src/test/resources/files/sample-input.txt");

        EntityExchangeResult<String> response = testClient.post()
                .uri(uriBuilder -> uriBuilder.path("/martian-robots/move-robots")
                        .build())
                .body(BodyInserters.fromResource(new FileUrlResource(fileUrl)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();

        String responseBody = response.getResponseBody();

        String expectedBody = "1 1 E\n" +
                "3 3 N LOST\n" +
                "2 3 S\n";

        assertEquals(expectedBody, responseBody);
    }

    @Test
    void invalidLineCountTest() throws MalformedURLException {
        URL fileUrl = new URL("file:src/test/resources/files/invalid-line-count.txt");

        EntityExchangeResult<String> response = testClient.post()
                .uri(uriBuilder -> uriBuilder.path("/martian-robots/move-robots")
                        .build())
                .body(BodyInserters.fromResource(new FileUrlResource(fileUrl)))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .returnResult();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("Invalid input", response.getResponseBody());
    }

    @Test
    void invalidMarsCoordinatesTest() throws MalformedURLException {
        URL fileUrl = new URL("file:src/test/resources/files/invalid-mars-coordinates.txt");

        EntityExchangeResult<String> response = testClient.post()
                .uri(uriBuilder -> uriBuilder.path("/martian-robots/move-robots")
                        .build())
                .body(BodyInserters.fromResource(new FileUrlResource(fileUrl)))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .returnResult();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("Invalid input", response.getResponseBody());
    }

    @Test
    void invalidRobotPositionsTest() throws MalformedURLException {
        URL fileUrl = new URL("file:src/test/resources/files/invalid-robot-positions.txt");

        EntityExchangeResult<String> response = testClient.post()
                .uri(uriBuilder -> uriBuilder.path("/martian-robots/move-robots")
                        .build())
                .body(BodyInserters.fromResource(new FileUrlResource(fileUrl)))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .returnResult();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("Invalid input", response.getResponseBody());
    }
}