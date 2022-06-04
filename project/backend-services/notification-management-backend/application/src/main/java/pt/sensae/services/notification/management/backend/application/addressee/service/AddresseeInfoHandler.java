package pt.sensae.services.notification.management.backend.application.addressee.service;

import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;

import java.util.stream.Stream;

public interface AddresseeInfoHandler {

    void publish(AddresseeDTO addressee);
}
