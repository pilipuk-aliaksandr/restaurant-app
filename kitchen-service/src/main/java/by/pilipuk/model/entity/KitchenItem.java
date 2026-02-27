package by.pilipuk.model.entity;

import by.pilipuk.model.entity.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class KitchenItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Kitchen kitchen;

    @Column(name = "item_name")
    private String name;

    @Column(name = "is_cooked")
    private boolean cooked;
}