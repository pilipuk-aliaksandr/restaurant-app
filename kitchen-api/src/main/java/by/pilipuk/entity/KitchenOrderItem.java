package by.pilipuk.entity;

import by.pilipuk.entity.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "kitchen_order_items")
@Getter
@Setter
public class KitchenOrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "kitchen_order_id")
    private KitchenOrder kitchenOrder;

    @Column(name = "item_name")
    private String name;

    @Column(name = "is_cooked")
    private boolean cooked;

}
