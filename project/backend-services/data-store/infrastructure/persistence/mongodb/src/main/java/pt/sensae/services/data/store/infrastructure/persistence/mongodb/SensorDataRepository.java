package pt.sensae.services.data.store.infrastructure.persistence.mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pt.sensae.services.data.store.application.DataRepository;

@Repository
public class SensorDataRepository implements DataRepository {

    private final MongoTemplate template;

    private final ObjectMapper mapper;

    public SensorDataRepository(MongoTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public void insert(String collection, Object data) {
        if (!template.collectionExists(collection))
            template.createCollection(collection);
        try {
            template.getCollection(collection).insertOne(Document.parse(mapper.writeValueAsString(data)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Object data) {
        if (!template.collectionExists("SensorData"))
            template.createCollection("SensorData");

        template.getCollection("SensorData").insertOne(Document.parse(data.toString()));
    }
}
