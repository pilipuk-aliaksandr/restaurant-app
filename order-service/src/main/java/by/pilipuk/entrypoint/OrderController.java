package by.pilipuk.entrypoint;

import by.pilipuk.api.OrdersApi;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.business.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {

    private final OrderService orderService;

    @Override
    public OrderDto create(OrderWriteDto orderWriteDto) {
        return orderService.createOrder(orderWriteDto);
    }

    @Override
    public OrderDto findById(Long id) {
        return orderService.findOrderById(id);
    }

    @Override
    public List<OrderDto> findOrders(OrderRequestDto orderRequestDto) {
        return orderService.findOrders(orderRequestDto);
    }
}