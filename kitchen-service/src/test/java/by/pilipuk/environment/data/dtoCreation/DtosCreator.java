package by.pilipuk.environment.data.dtoCreation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtosCreator {

    public final KitchenDtosCreator kitchenDtosCreator;
    public final KitchenItemDtosCreator kitchenItemDtosCreator;
}
