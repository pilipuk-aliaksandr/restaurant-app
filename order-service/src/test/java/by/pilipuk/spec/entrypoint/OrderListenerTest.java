package by.pilipuk.spec.entrypoint;

import by.pilipuk.environment.service.OrderTestService;
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
        topics = {"ready_orders"},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers",
        count = 1
)
@DisplayName("Test KafkaOrderListener")
class OrderListenerTest extends BaseControllerTest {

    @Autowired
    private OrderTestService orderTestService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    @WithMockUser(username = "admin")
    void listen() throws InterruptedException {
        // given
        var event = orderTestService.createOrderReadyEvent();
        var jsonEvent = objectMapper.writeValueAsString(event);
        orderTestService.saveOrder();

        // when
        kafkaTemplate.send("ready_orders", jsonEvent);
        Thread.sleep(3000);

        var savedOrderDto = orderTestService.getOrderDtoFromDB();

        var expectedOrderDto = orderTestService.createOrderDto();
        expectedOrderDto.setStatus("READY");

        // then
        assertEquals(savedOrderDto, expectedOrderDto);
    }
}