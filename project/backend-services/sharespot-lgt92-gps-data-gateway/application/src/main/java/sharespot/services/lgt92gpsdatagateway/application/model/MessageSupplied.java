package sharespot.services.lgt92gpsdatagateway.application.model;

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
