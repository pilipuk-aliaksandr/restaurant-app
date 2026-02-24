package by.pilipuk.entity;

import by.pilipuk.entity.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "kitchen_orders")
@Getter
@Setter
public class KitchenOrder extends BaseEntity {

    @Column(name = "order_id")
    private Long orderId;

    @OneToMany(mappedBy = "kitchenOrder", cascade = CascadeType.ALL)
    private List<KitchenOrderItem> items;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

}