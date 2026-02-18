package by.pilipuk.service;


import by.pilipuk.dto.OrderDto;
import by.pilipuk.mapper.OrderMapper;
import by.pilipuk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public ResponseEntity<Void> createOrder(OrderDto orderDto) {
        orderRepository.save(orderMapper.toEntity(orderDto));
        return ResponseEntity.ok().build();
    }
}