package by.pilipuk.mapper;

import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.entity.Order;
import by.pilipuk.entity.baseEntity.BaseEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class OrderSpecificationMapper {

    private OrderSpecificationMapper() {}

    public Specification<Order> orderSpecification(OrderRequestDto orderRequestDto) {
        return Specification.allOf(
            hasIds(orderRequestDto.getId()),
            hasTableNumbers(orderRequestDto.getTableNumber()),
            hasStatuses(orderRequestDto.getStatus()),
            hasItems(orderRequestDto.getItems())
        );
    }

    public static Specification<Order> hasIds(List<Long> ids) {
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

    public static Specification<Order> hasTableNumbers(List<Integer> tableNumbers) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(tableNumbers)) {
                return criteriaBuilder.conjunction();
            }
            else if (tableNumbers.size() == 1) {
                return criteriaBuilder.equal(root.get("tableNumber"), tableNumbers.getFirst());
            }
            else return root.get("tableNumber").in(tableNumbers);
        };
    }

    public static Specification<Order> hasStatuses(List<String> statuses) {
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

    public static Specification<Order> hasItems(List<String> items) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(items)) {
                return criteriaBuilder.conjunction();
            }
            else return root.join("items").get("name").in(items);
        };
    }
}