package by.pilipuk.spec.entrypoint;

import by.pilipuk.business.service.KitchenCookerService;
import by.pilipuk.environment.service.KitchenTestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test all methods from KitchenController.class")
class KitchenControllerTest extends BaseControllerTest {

    @Autowired
    private KitchenTestService kitchenTestService;

    @MockitoBean
    private KitchenCookerService kitchenCookerService;

    @Test
    @WithMockUser(username = "admin")
    void findById() {
        //given
        var expectedKitchenDto = kitchenTestService.createKitchenDto();

        //when
        kitchenTestService.saveKitchen();

        //then
        performGetRequest("/v1/kitchen/orders/{id}", 1, expectedKitchenDto);
    }

    @Test
    @WithMockUser(username = "admin")
    void findKitchens() {
        //given
        var requestDto = kitchenTestService.createKitchenRequestDto();
        var expectedKitchenDtos = Collections.singletonList(kitchenTestService.createKitchenDto());

        //when
        kitchenTestService.saveKitchen();

        //then
        performPostSearchRequest("/v1/kitchen/orders", requestDto, expectedKitchenDtos);
    }
}