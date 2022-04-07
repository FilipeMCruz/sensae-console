package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.LedgerEntryPostgres;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface LedgerRepositoryPostgres extends CrudRepository<LedgerEntryPostgres, Long> {

    Optional<LedgerEntryPostgres> findByDeviceIdAndCloseAtNull(String deviceId);

    @Query(value = "SELECT * FROM ledger_entries WHERE Cast(ownership AS text[]) && Cast(:ownership AS text[]) AND close_at IS NULL")
    Stream<LedgerEntryPostgres> findLatestWithOwnership(@Param("ownership") String ownership);


    @Query(value = "SELECT * FROM ledger_entries WHERE Cast(ownership AS text[]) && Cast(:ownership AS text[]) AND (open_at <= :closeDate AND (close_at IS NULL OR :openDate <= close_at))")
    Stream<LedgerEntryPostgres> findOldWithOwnership(@Param("ownership") String ownership, @Param("openDate") Timestamp openDate, @Param("closeDate") Timestamp closeDate);
}
