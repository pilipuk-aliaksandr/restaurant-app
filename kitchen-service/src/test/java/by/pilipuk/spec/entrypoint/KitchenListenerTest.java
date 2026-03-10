package by.pilipuk.spec.entrypoint;

import by.pilipuk.environment.service.KitchenTestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = {"orders"},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers",
        count = 1
)
@DisplayName("Test KafkaKitchenListener")
class KitchenListenerTest extends BaseControllerTest {

    @Autowired
    private KitchenTestService kitchenTestService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    @WithMockUser(username = "admin")
    void listen() throws InterruptedException {
        // given
        var event = kitchenTestService.createOrderCreatedEvent();
        String jsonEvent = objectMapper.writeValueAsString(event);

        // when
        kafkaTemplate.send("orders", jsonEvent);
        Thread.sleep(3000);

        var savedKitchenDto = kitchenTestService.getKitchenDtoFromDB();

        var expectedKitchenDto = kitchenTestService.createKitchenDto();

        // then
        assertEquals(savedKitchenDto, expectedKitchenDto);
    }
}