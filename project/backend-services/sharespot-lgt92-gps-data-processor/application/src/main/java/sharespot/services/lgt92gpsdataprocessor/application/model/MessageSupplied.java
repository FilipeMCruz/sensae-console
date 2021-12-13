package sharespot.services.lgt92gpsdataprocessor.application.model;

public class MessageSupplied<T> {

    public RoutingKeys routingKeys;
    public T data;

    public MessageSupplied(RoutingKeys routingKeys, T data) {
        this.routingKeys = routingKeys;
        this.data = data;
    }

    public MessageSupplied() {
    }
}
