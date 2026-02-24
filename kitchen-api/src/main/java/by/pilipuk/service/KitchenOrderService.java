package by.pilipuk.service;

import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.KitchenOrderRequestDto;
import by.pilipuk.dto.KitchenOrderWriteDto;
import by.pilipuk.mapper.KitchenOrderMapper;
import by.pilipuk.mapper.KitchenOrderSpecificationMapper;
import by.pilipuk.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
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
}