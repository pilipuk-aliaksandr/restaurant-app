package by.pilipuk.mapper;

import by.pilipuk.dto.KitchenOrderRequestDto;
import by.pilipuk.entity.KitchenOrder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Component
public class KitchenOrderSpecificationMapper {

    private KitchenOrderSpecificationMapper() {}

    public Specification<KitchenOrder> kitchenOrderSpecification(KitchenOrderRequestDto kitchenOrderRequestDto) {
        return Specification.allOf(
                hasIds(kitchenOrderRequestDto.getId()),
                hasOrderIds(kitchenOrderRequestDto.getOrderId()),
                hasStatuses(kitchenOrderRequestDto.getStatus()),
                hasItems(kitchenOrderRequestDto.getItems())
        );
    }

    public static Specification<KitchenOrder> hasIds(List<Long> ids) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(ids)) {
                return criteriaBuilder.conjunction();
            }
            else if (ids.size() == 1) {
                return criteriaBuilder.equal(root.get("id"), ids.getFirst());
            }
            else return root.get("id").in(ids);
        };
    }

    public static Specification<KitchenOrder> hasOrderIds(List<Long> ordersIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(ordersIds)) {
                return criteriaBuilder.conjunction();
            }
            else if (ordersIds.size() == 1) {
                return criteriaBuilder.equal(root.get("orderId"), ordersIds.getFirst());
            }
            else return root.get("orderId").in(ordersIds);
        };
    }

    public static Specification<KitchenOrder> hasStatuses(List<String> statuses) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(statuses)) {
                return criteriaBuilder.conjunction();
            }
            else if (statuses.size() == 1) {
                return criteriaBuilder.equal(root.get("status"), statuses.getFirst());
            }
            else return root.get("status").in(statuses);
        };
    }

    public static Specification<KitchenOrder> hasItems(List<String> items) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(items)) {
                return criteriaBuilder.conjunction();
            }
            else return root.join("items").get("name").in(items);
        };
    }
}
