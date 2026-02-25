package by.pilipuk.service;

import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.KitchenOrderRequestDto;
import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.entity.KitchenOrder;
import by.pilipuk.entity.Status;
import by.pilipuk.mapper.KitchenOrderMapper;
import by.pilipuk.mapper.KitchenOrderSpecificationMapper;
import by.pilipuk.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenOrderService {

    private final KitchenOrderRepository kitchenOrderRepository;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final KitchenOrderSpecificationMapper kitchenOrderSpecificationMapper;

    public KitchenOrderDto findKitchenOrderById(Long id) {
        return kitchenOrderMapper.toDto(kitchenOrderRepository.findByIdOrElseThrow(id));
    }

    public List<KitchenOrderDto> findKitchenOrders(KitchenOrderRequestDto kitchenOrderRequestDto) {
        var spec = kitchenOrderSpecificationMapper.kitchenOrderSpecification(kitchenOrderRequestDto);

        var ordersDtoList = kitchenOrderRepository.findAll(spec).stream()
                .map(kitchenOrderMapper::toDto).toList();
        if (CollectionUtils.isEmpty(ordersDtoList)) {
            throw new RuntimeException("NOT_FOUND_BY_FILTER" + kitchenOrderRequestDto);
        }
        else return ordersDtoList;
    }

    @Transactional
    public void acceptNewOrdersFromKafka(OrderCreatedEvent event) {
        KitchenOrder kitchenOrder = kitchenOrderMapper.toEntity(event);
        kitchenOrder.setStatus(Status.ACCEPTED);
        kitchenOrderRepository.save(kitchenOrder);
        log.info("Accepted new order: {} in cooking process", kitchenOrder.getOrderId());
    }
}