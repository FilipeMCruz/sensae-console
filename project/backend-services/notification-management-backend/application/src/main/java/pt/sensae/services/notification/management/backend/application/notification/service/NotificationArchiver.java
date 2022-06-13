package pt.sensae.services.notification.management.backend.application.notification.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationRepository;

import javax.annotation.PostConstruct;

@Service
public class NotificationArchiver {

    private final NotificationPublisher publisher;

    private final NotificationRepository repository;

    public NotificationArchiver(NotificationPublisher publisher, NotificationRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    @PostConstruct
    private void init() {
        publisher.getSinglePublisher().subscribe(repository::save);
    }
}
