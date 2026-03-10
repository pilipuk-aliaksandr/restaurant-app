package by.pilipuk.common.business.repository;

import by.pilipuk.common.model.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    @Query(value = """
        SELECT * FROM common_outbox.outbox_events
        WHERE active = true
        ORDER BY created_at ASC 
        LIMIT 1 
        FOR UPDATE SKIP LOCKED;
        """, nativeQuery = true)
    Optional<OutboxEvent> findNextForProcessing();
}
