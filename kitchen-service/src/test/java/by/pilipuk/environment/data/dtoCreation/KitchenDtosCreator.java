package by.pilipuk.environment.data.dtoCreation;

import by.pilipuk.dto.KitchenDto;
import by.pilipuk.dto.KitchenRequestDto;
import org.springframework.stereotype.Component;

@Component
public class KitchenDtosCreator {

    public KitchenDto createKitchenDto() {
        var kitchenDto = new KitchenDto();
        kitchenDto.setId(1L);
        kitchenDto.setOrderId(1L);
        kitchenDto.setStatus(KitchenDto.StatusEnum.ACCEPTED);

        return kitchenDto;
    }

    public KitchenRequestDto createKitchenRequestDto() {
        return new KitchenRequestDto();
    }
}