package by.pilipuk.business.mapper;

import by.pilipuk.dto.KitchenRequestDto;
import by.pilipuk.model.entity.Kitchen;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Component
public class KitchenSpecificationMapper {

    private KitchenSpecificationMapper() {}

    public Specification<Kitchen> kitchenOrderSpecification(KitchenRequestDto kitchenRequestDto) {
        return Specification.allOf(
                hasIds(kitchenRequestDto.getId()),
                hasOrderIds(kitchenRequestDto.getOrderId()),
                hasStatuses(kitchenRequestDto.getStatus()),
                hasItems(kitchenRequestDto.getItems())
        );
    }

    public static Specification<Kitchen> hasIds(List<Long> ids) {
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

    public static Specification<Kitchen> hasOrderIds(List<Long> ordersIds) {
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

    public static Specification<Kitchen> hasStatuses(List<String> statuses) {
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

    public static Specification<Kitchen> hasItems(List<String> items) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(items)) {
                return criteriaBuilder.conjunction();
            }
            else return root.join("items").get("name").in(items);
        };
    }
}
