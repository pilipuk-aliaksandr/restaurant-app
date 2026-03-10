package by.pilipuk.kitchen.entrypoint;

import by.pilipuk.kitchen.api.KitchenApi;
import by.pilipuk.kitchen.dto.OrderDto;
import by.pilipuk.kitchen.dto.OrderRequestDto;
import by.pilipuk.kitchen.business.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KitchenController implements KitchenApi {

    private final OrderService orderService;

    @Override
    public OrderDto findById(Long id) {
        return orderService.findOrderById(id);
    }

    @Override
    public List<OrderDto> findOrders(OrderRequestDto kitchenRequestDto) {
        return orderService.findOrders(kitchenRequestDto);
    }
}