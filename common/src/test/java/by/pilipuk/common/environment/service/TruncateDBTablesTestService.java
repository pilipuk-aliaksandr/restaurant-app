package by.pilipuk.common.environment.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.PreparedStatement;
import java.util.List;

@Service
public class TruncateDBTablesTestService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TruncateDBTablesTestService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String TRUNCATE_TABLES = """
                        TRUNCATE TABLE %s RESTART IDENTITY CASCADE;
                        """;

    public void truncateAllTables() {
        TABLES_NAMES.forEach(table -> jdbcTemplate.execute(TRUNCATE_TABLES.formatted(table), PreparedStatement::execute));
    }

    public static final List<String> TABLES_NAMES = List.of(
            "orders.orders",
            "orders.order_items",
            "kitchens.orders",
            "kitchens.order_items",
            "users.users"
    );
}
