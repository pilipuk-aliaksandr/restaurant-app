package by.pilipuk.kitchen.spec.entrypoint;

import by.pilipuk.kitchen.environment.service.OrderTestService;
import by.pilipuk.spec.entrypoint.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = {"orders"},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers",
        count = 1
)
@DisplayName("Test KafkaKitchenListener")
class OrderListenerTest extends BaseControllerTest {

    @Autowired
    private OrderTestService orderTestService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void listen() throws InterruptedException {
        // given
        var event = orderTestService.createOrderCreatedEvent();
        String jsonEvent = objectMapper.writeValueAsString(event);

        // when
        kafkaTemplate.send("orders", jsonEvent);
        Thread.sleep(3000);

        var savedKitchenDto = orderTestService.getOrderDtoFromDB();

        var expectedKitchenDto = orderTestService.createOrderDto();

        // then
        assertEquals(savedKitchenDto, expectedKitchenDto);
    }
}