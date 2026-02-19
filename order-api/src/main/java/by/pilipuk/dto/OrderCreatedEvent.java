package by.pilipuk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreatedEvent {

    private String id;

    private Integer tableNumber;

    private List<String> items;
}
