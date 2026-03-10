package by.pilipuk.environment.service;

import by.pilipuk.business.mapper.KitchenMapper;
import by.pilipuk.business.repository.KitchenRepository;
import by.pilipuk.dto.KitchenDto;
import by.pilipuk.dto.KitchenRequestDto;
import by.pilipuk.environment.data.dtoCreation.DtosCreator;
import by.pilipuk.environment.data.entityCreation.EntityCreator;
import by.pilipuk.environment.data.eventCreation.EventCreator;
import by.pilipuk.model.dto.OrderCreatedEvent;
import by.pilipuk.model.entity.Kitchen;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KitchenTestService {

    private final EntityCreator entityCreator;
    private final DtosCreator dtosCreator;
    private final EventCreator eventCreator;

    private final KitchenRepository kitchenRepository;
    private final KitchenMapper kitchenMapper;

    public Kitchen createKitchen() {
        var kitchen = entityCreator.kitchenCreator.createKitchen();
        var kitchenItems = entityCreator.kitchenItemCreator.createKitchenItems(kitchen);
        kitchen.setItems(kitchenItems);

        return kitchen;
    }

    public Kitchen saveKitchen() {
        var kitchen = entityCreator.kitchenCreator.saveKitchen(
                entityCreator.kitchenCreator.createKitchen()
        );
        var kitchenItems = entityCreator.kitchenItemCreator.saveKitchenItems(kitchen);
        kitchen.setItems(kitchenItems);

        return kitchen;
    }

    public KitchenDto createKitchenDto() {
        var kitchenDto = dtosCreator.kitchenDtosCreator.createKitchenDto();
        var kitchenItemsDto = dtosCreator.kitchenItemDtosCreator.createKitchenItemsDto(kitchenDto);
        kitchenDto.setItems(kitchenItemsDto);

        return kitchenDto;
    }

    public KitchenRequestDto createKitchenRequestDto() {
        return dtosCreator.kitchenDtosCreator.createKitchenRequestDto();
    }

    public OrderCreatedEvent createOrderCreatedEvent() {
        return eventCreator.createOrderCreatedEvent();
    }

    @Transactional
    public KitchenDto getKitchenDtoFromDB() {
        var savedKitchen = kitchenRepository.findByIdOrElseThrow(1L);
        Hibernate.initialize(savedKitchen.getItems());
        return kitchenMapper.toDto(savedKitchen);
    }
}