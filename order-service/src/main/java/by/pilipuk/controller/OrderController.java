package by.pilipuk.controller;

import by.pilipuk.api.OrdersApi;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

//TODO никаких респонс энтити в ответе - ПОЗОР - смотри конфиг старого проекта
@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {

    private final OrderService orderService;

    //TODO create
    @Override
    public ResponseEntity<OrderDto> createOrder(OrderWriteDto orderWriteDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(orderWriteDto));
    }

    //TODO findById
    @Override
    public ResponseEntity<OrderDto> getOrderById(Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    //TODO findOrders
    @Override
    public ResponseEntity<List<OrderDto>> getOrders(OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.findOrders(orderRequestDto));
    }
}