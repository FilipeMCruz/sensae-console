package sharespot.services.lgt92gpsdataprocessor.application.model;

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
