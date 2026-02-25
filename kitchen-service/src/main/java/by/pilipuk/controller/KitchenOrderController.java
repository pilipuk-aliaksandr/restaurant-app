package by.pilipuk.controller;

import by.pilipuk.api.KitchenOrdersApi;
import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.KitchenOrderRequestDto;
import by.pilipuk.dto.KitchenOrderWriteDto;
import by.pilipuk.service.KitchenOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KitchenOrderController implements KitchenOrdersApi {

    private final KitchenOrderService kitchenOrderService;

    @Override
    public ResponseEntity<KitchenOrderDto> getKitchenOrderById(Long id) {
        return ResponseEntity.ok(kitchenOrderService.findKitchenOrderById(id));
    }

    @Override
    public ResponseEntity<List<KitchenOrderDto>> getKitchenOrders(KitchenOrderRequestDto kitchenOrderRequestDto) {
        return ResponseEntity.ok(kitchenOrderService.findKitchenOrders(kitchenOrderRequestDto));
    }
}
