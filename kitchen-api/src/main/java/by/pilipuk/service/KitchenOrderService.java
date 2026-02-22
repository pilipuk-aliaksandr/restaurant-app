package by.pilipuk.service;

import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.KitchenOrderWriteDto;
import by.pilipuk.mapper.KitchenOrderMapper;
import by.pilipuk.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenOrderService {

    private final KitchenOrderRepository kitchenOrderRepository;
    private final KitchenOrderMapper kitchenOrderMapper;

    public KitchenOrderDto createKitchenOrder(KitchenOrderWriteDto kitchenOrderWriteDto) {
        return kitchenOrderMapper.toDto(kitchenOrderRepository.save(kitchenOrderMapper.toEntity(kitchenOrderWriteDto)));
    }

    public KitchenOrderDto findKitchenOrderById(Long id) {
        return kitchenOrderMapper.toDto(kitchenOrderRepository.findByIdOrElseThrow(id));
    }

    public List<KitchenOrderDto> findKitchenOrders() {
        return kitchenOrderRepository.findAll().stream()
                .map(kitchenOrderMapper::toDto)
                .toList();
    }
}