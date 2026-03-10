package by.pilipuk.common.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderReadyEvent {

    private Long orderId;

    private List<String> items;
}
