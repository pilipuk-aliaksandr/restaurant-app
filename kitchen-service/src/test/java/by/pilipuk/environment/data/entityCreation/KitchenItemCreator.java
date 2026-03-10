package by.pilipuk.environment.data.entityCreation;

import by.pilipuk.business.repository.KitchenItemRepository;
import by.pilipuk.model.entity.Kitchen;
import by.pilipuk.model.entity.KitchenItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KitchenItemCreator {

    private final KitchenItemRepository kitchenItemRepository;

    private KitchenItem createKitchenItem(Long id, Kitchen kitchen, String name) {
        KitchenItem kitchenItem = new KitchenItem();
        kitchenItem.setId(id);
        kitchenItem.setKitchen(kitchen);
        kitchenItem.setName(name);
        kitchenItem.setCooked(false);
        kitchenItem.setActive(true);
        kitchenItem.setCreatedAt(LocalDateTime.now());

        return kitchenItem;
    }

    public List<KitchenItem> createKitchenItems(Kitchen kitchen) {
        return List.of(
                createKitchenItem(1L, kitchen, "Pizza"),
                createKitchenItem(2L, kitchen, "Coca-Cola")
        );
    }

    private KitchenItem saveKitchenItem(KitchenItem kitchenItem) {
        kitchenItem.setId(null);
        return kitchenItemRepository.save(kitchenItem);
    }

    public List<KitchenItem> saveKitchenItems(Kitchen kitchen) {
        return List.of(
                saveKitchenItem(createKitchenItem(1L, kitchen, "Pizza")),
                saveKitchenItem(createKitchenItem(2L, kitchen, "Coca-Cola"))
        );
    }
}