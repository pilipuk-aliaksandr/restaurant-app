package by.pilipuk.business.service;

import by.pilipuk.business.mapper.KitchenMapper;
import by.pilipuk.core.exception.validationException.ValidationException;
import by.pilipuk.dto.KitchenDto;
import by.pilipuk.dto.KitchenRequestDto;
import by.pilipuk.model.dto.OrderCreatedEvent;
import by.pilipuk.model.entity.Kitchen;
import by.pilipuk.model.entity.Status;
import by.pilipuk.business.mapper.KitchenSpecificationMapper;
import by.pilipuk.business.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;
import static by.pilipuk.core.exception.validationException.ValidationCode.NOT_FOUND_BY_FILTER;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenService {

    private final KitchenRepository kitchenRepository;
    private final KitchenMapper kitchenMapper;
    private final KitchenSpecificationMapper kitchenSpecificationMapper;

    public KitchenDto findKitchenOrderById(Long id) {
        return kitchenMapper.toDto(kitchenRepository.findByIdOrElseThrow(id));
    }

    public List<KitchenDto> findKitchenOrders(KitchenRequestDto kitchenRequestDto) {
        var spec = kitchenSpecificationMapper.kitchenOrderSpecification(kitchenRequestDto);

        var ordersDtoList = kitchenRepository.findAll(spec).stream()
                .map(kitchenMapper::toDto).toList();
        if (CollectionUtils.isEmpty(ordersDtoList)) {
            throw ValidationException.create(NOT_FOUND_BY_FILTER, kitchenRequestDto.toString());
        }
        else return ordersDtoList;
    }

    @Transactional
    public void acceptNewOrdersFromKafka(OrderCreatedEvent event) {
        Kitchen kitchen = kitchenMapper.toEntity(event);
        kitchen.setStatus(Status.ACCEPTED);
        kitchenRepository.save(kitchen);
        log.info("Accepted new order: {} in cooking process", kitchen.getOrderId());
    }
}