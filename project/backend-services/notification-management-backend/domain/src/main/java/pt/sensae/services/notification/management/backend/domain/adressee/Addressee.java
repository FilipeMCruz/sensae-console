package pt.sensae.services.notification.management.backend.domain.adressee;

import pt.sensae.services.notification.management.backend.domain.notification.Notification;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Addressee(AddresseeId id, Domains domains, AddresseeContacts contacts, Set<AddresseeConfig> configs) {

    public Addressee withContacts(AddresseeContacts contacts) {
        return new Addressee(id, domains, contacts, configs);
    }

    public Addressee withDomains(Domains domains) {
        return new Addressee(id, domains, contacts, configs);
    }

    public Stream<DeliveryType> sendVia(Notification notification) {
        var rules = configs.stream()
                .filter(config -> config.contentType().equals(notification.type()))
                .collect(Collectors.toSet());
        if (rules.isEmpty()) {
            return Stream.of(DeliveryType.NOTIFICATION);
        }
        return rules.stream().filter(AddresseeConfig::mute).map(AddresseeConfig::deliveryType);
    }

    public static Addressee of(AddresseeId id, Set<AddresseeConfig> configs) {
        return new Addressee(id, Domains.empty(), AddresseeContacts.empty(), configs);
    }
}
