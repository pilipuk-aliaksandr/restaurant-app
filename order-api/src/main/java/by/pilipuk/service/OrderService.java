package by.pilipuk.service;


import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.mapper.OrderMapper;
import by.pilipuk.mapper.OrderSpecificationMapper;
import by.pilipuk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecificationMapper orderSpecificationMapper;

    public ResponseEntity<Void> createOrder(OrderWriteDto orderWriteDto) {
        orderRepository.save(orderMapper.toEntity(orderWriteDto));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<OrderDto> findOrderById(Long id) {
        var orderDto = orderMapper.toDto(orderRepository.findByIdOrElseThrow(id));
        return ResponseEntity.ok(orderDto);
    }

    public ResponseEntity<List<OrderDto>> findOrders(OrderRequestDto orderRequestDto) {
        var spec = orderSpecificationMapper.orderSpecification(orderRequestDto);

        var ordersDtoList = orderRepository.findAll(spec).stream().
                map(orderMapper::toDto).toList();
        if (CollectionUtils.isEmpty(ordersDtoList)) {
            throw new RuntimeException("NOT_FOUND_BY_FILTER");
        }
        else return ResponseEntity.ok(ordersDtoList);
    }
}