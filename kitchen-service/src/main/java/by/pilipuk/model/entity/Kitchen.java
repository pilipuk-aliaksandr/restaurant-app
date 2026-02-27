package by.pilipuk.model.entity;

import by.pilipuk.model.entity.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Kitchen extends BaseEntity {

    @Column(name = "order_id")
    private Long orderId;

    @OneToMany(mappedBy = "kitchen", cascade = CascadeType.ALL)
    private List<KitchenItem> items;

    @Enumerated(EnumType.STRING)
    private Status status;

}