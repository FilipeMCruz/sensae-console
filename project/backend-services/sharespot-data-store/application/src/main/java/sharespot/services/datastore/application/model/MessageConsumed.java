package sharespot.services.datastore.application.model;

public class MessageConsumed<T> {

    public RoutingKeys routingKeys;
    public T data;

    public MessageConsumed(RoutingKeys routingKeys, T data) {
        this.routingKeys = routingKeys;
        this.data = data;
    }

    public MessageConsumed() {
    }
}
