package by.pilipuk.entrypoint;

import by.pilipuk.api.KitchenApi;
import by.pilipuk.dto.KitchenDto;
import by.pilipuk.dto.KitchenRequestDto;
import by.pilipuk.business.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KitchenController implements KitchenApi {

    private final KitchenService kitchenService;

    @Override
    public KitchenDto findById(Long id) {
        return kitchenService.findKitchenOrderById(id);
    }

    @Override
    public List<KitchenDto> findKitchens(KitchenRequestDto kitchenRequestDto) {
        return kitchenService.findKitchenOrders(kitchenRequestDto);
    }
}