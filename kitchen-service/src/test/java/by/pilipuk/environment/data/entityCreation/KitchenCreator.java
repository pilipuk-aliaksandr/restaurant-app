package by.pilipuk.environment.data.entityCreation;

import by.pilipuk.business.repository.KitchenRepository;
import by.pilipuk.model.entity.Kitchen;
import by.pilipuk.model.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class KitchenCreator {

    private final KitchenRepository kitchenRepository;

    public Kitchen createKitchen() {
        Kitchen kitchen = new Kitchen();
        kitchen.setId(1L);
        kitchen.setOrderId(1L);
        kitchen.setStatus(Status.ACCEPTED);
        kitchen.setActive(true);
        kitchen.setCreatedAt(LocalDateTime.now());

        return kitchen;
    }

    public Kitchen saveKitchen(Kitchen kitchen) {
        kitchen.setId(null);
        return kitchenRepository.save(kitchen);
    }
}