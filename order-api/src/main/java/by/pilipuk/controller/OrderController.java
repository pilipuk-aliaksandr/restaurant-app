package by.pilipuk.controller;

import by.pilipuk.api.OrdersApi;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Void> createOrder(OrderWriteDto orderWriteDto) {
        return orderService.createOrder(orderWriteDto);
    }

    @Override
    public ResponseEntity<OrderDto> getOrderById(Long id) {
        return orderService.findOrderById(id);
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrders(OrderRequestDto orderRequestDto) {
        return orderService.findOrders(orderRequestDto);
    }
}
