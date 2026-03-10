package by.pilipuk.spec.entrypoint;

import by.pilipuk.business.mapper.OrderMapper;
import by.pilipuk.environment.service.OrderTestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.Collections;

@DisplayName("Test all methods from OrderController.class")
class OrderControllerTest extends BaseControllerTest {

    @Autowired
    OrderTestService orderTestService;

    @Autowired
    OrderMapper orderMapper;

    @Test
    @WithMockUser(username = "admin")
    void create() {
        //given
        var expectedOrderDto = orderMapper.toDto(orderTestService.createOrder());

        //when


        //then
        performPostRequest("/v1/orders/new", orderTestService.createOrderWriteDto(), expectedOrderDto);
    }

    @Test
    @WithMockUser(username = "admin")
    void findById() {
        //given
        var expectedOrderDto = orderTestService.createOrderDto();

        //when
        orderTestService.saveOrder();

        //then
        performGetRequest("/v1/orders/{id}", 1, expectedOrderDto);
    }

    @Test
    @WithMockUser(username = "admin")
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