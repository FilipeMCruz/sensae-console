package pt.sensae.services.data.store.application;


public interface DataRepository {

    void insert(String collection, Object data);

    void insert(Object data);
}
