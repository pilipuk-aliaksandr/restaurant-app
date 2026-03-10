package by.pilipuk.environment.data.dtoCreation;

import by.pilipuk.dto.KitchenDto;
import by.pilipuk.dto.KitchenItemsDto;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class KitchenItemDtosCreator {

    private KitchenItemsDto createKitchenItemDto(Long id, KitchenDto kitchenDto, String name) {
        var kitchenItemDto = new KitchenItemsDto();
        kitchenItemDto.setId(id);
        kitchenItemDto.setKitchenId(kitchenDto.getId());
        kitchenItemDto.setItemName(name);
        kitchenItemDto.setCooked(false);

        return kitchenItemDto;
    }

    public List<KitchenItemsDto> createKitchenItemsDto(KitchenDto kitchenDto) {
        return List.of(
                createKitchenItemDto(1L, kitchenDto, "Pizza"),
                createKitchenItemDto(2L, kitchenDto, "Coca-Cola")
        );
    }
}