package sharespot.services.datastore.application;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface DataRepository {

    void insert(String collection, ObjectNode data);

    void insert(ObjectNode data);
}
