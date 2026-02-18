package by.pilipuk.controller;

import by.pilipuk.api.OrdersApi;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Void> createOrder(OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }
}
