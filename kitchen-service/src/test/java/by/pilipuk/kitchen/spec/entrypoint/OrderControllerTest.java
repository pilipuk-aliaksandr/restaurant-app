package by.pilipuk.kitchen.spec.entrypoint;

import by.pilipuk.kitchen.business.service.OrderCookerService;
import by.pilipuk.kitchen.environment.service.OrderTestService;
import by.pilipuk.kitchen.dto.OrderDto;
import by.pilipuk.spec.entrypoint.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test all methods from OrderController.class kitchen-module")
class OrderControllerTest extends BaseControllerTest {

    @Autowired
    private OrderTestService orderTestService;

    @MockitoBean
    private OrderCookerService orderCookerService;

    @Test
    void findById() {
        //given
        OrderDto expectedOrderDto = orderTestService.createOrderDto();

        //when
        orderTestService.saveOrder();

        //then
        performGetRequest("/v1/kitchen/orders/{id}", 1, expectedOrderDto);
    }

    @Test
    void findOrders() {
        //given
        var requestDto = orderTestService.createOrderRequestDto();
        var expectedOrderDtos = Collections.singletonList(orderTestService.createOrderDto());

        //when
        orderTestService.saveOrder();

        //then
        performPostSearchRequest("/v1/kitchen/orders", requestDto, expectedOrderDtos);
    }
}