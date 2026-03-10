package by.pilipuk.spec.entrypoint;

import by.pilipuk.environment.service.TruncateDBTablesTestService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TruncateDBTablesTestService DBTestService;

    @BeforeEach
    protected void setup() {
        DBTestService.truncateAllTables();
    }

    protected String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    protected void performPostRequest(String url, Object postDto, Object expectedEntity) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(postDto)))
                    .andExpectAll(
                            status().isCreated(),
                            content().contentType(MediaType.APPLICATION_JSON),
                            content().json(toJson(expectedEntity))
                    );
        } catch (Exception e) {
            fail("Error executing POST request to " + url + ": " + e.getMessage());
        }
    }

    protected void performPostRequest(String url, Object postDto) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(postDto)))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            fail("Error executing POST request to " + url + ": " + e.getMessage());
        }
    }

    protected void performGetRequest(String url, Object urlVariables, Object expectedDto) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(url, urlVariables))
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON),
                            content().json(toJson(expectedDto))
                    );
        } catch (Exception e) {
            fail("Error executing GET request with path variable: " + e.getMessage());
        }
    }

    protected void performPostSearchRequest(String url, Object requestBody, Object expectedDto) {
        try {
            var requestBuilder = MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(requestBody));

            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON),
                            content().json(toJson(expectedDto))
                    );
        } catch (Exception e) {
            fail("Error executing POST search request: " + e.getMessage());
        }
    }

    protected void performAuthRequest(String url, Object authRequest, Object expectedResponse) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(authRequest)))
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON),
                            content().json(toJson(expectedResponse))
                    );
        } catch (Exception e) {
            fail("Error executing Auth POST request to " + url + ": " + e.getMessage());
        }
    }
}