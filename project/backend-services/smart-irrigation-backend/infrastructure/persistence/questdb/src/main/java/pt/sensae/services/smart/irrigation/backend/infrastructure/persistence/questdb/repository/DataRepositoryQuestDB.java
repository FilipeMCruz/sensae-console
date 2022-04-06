package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.repository;

import org.springframework.data.repository.CrudRepository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.model.DataQuestDB;

public interface DataRepositoryQuestDB extends CrudRepository<DataQuestDB, Long> {
}
