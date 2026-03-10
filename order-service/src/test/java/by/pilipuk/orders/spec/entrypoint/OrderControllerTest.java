package by.pilipuk.orders.spec.entrypoint;

import by.pilipuk.orders.business.mapper.OrderMapper;
import by.pilipuk.orders.environment.service.OrderTestService;
import by.pilipuk.spec.entrypoint.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;

@DisplayName("Test all methods from OrderController.class order-module")
class OrderControllerTest extends BaseControllerTest {

    @Autowired
    OrderTestService orderTestService;

    @Autowired
    OrderMapper orderMapper;

    @Test
    void create() {
        //given
        var expectedOrderDto = orderMapper.toDto(orderTestService.createOrder());

        //when

        //then
        performPostRequest("/v1/orders/new", orderTestService.createOrderWriteDto(), expectedOrderDto);
    }

    @Test
    void findById() {
        //given
        var expectedOrderDto = orderTestService.createOrderDto();

        //when
        orderTestService.saveOrder();

        //then
        performGetRequest("/v1/orders/{id}", 1, expectedOrderDto);
    }

    @Test
    void findOrders() {
        //given
        var orderRequestDto = orderTestService.createOrderRequestDto();
        var expectedOrderDto = Collections.singletonList(orderTestService.createOrderDto());

        //when
        orderTestService.saveOrder();

        //then
        performPostSearchRequest("/v1/orders", orderRequestDto, expectedOrderDto);
    }
}