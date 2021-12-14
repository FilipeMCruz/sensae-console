package sharespot.services.datastore.infrastructure.persistence.mongodb;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import sharespot.services.datastore.application.DataRepository;

@Repository
public class SensorDataRepository implements DataRepository {

    private final MongoTemplate template;

    public SensorDataRepository(MongoTemplate template) {
        this.template = template;
    }

    public void insert(String collection, ObjectNode data) {
        if (!template.collectionExists(collection))
            template.createCollection(collection);
        template.getCollection(collection).insertOne(Document.parse(data.toString()));
    }

    public void insert(ObjectNode data) {
        if (!template.collectionExists("SensorData"))
            template.createCollection("SensorData");

        template.getCollection("SensorData").insertOne(Document.parse(data.toString()));
    }
}
