package sharespot.services.fastdatastore.application.message;

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
