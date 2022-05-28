package pt.sensae.services.notification.management.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentTypeRepository;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationRepository;

import javax.annotation.PostConstruct;

@Service
public class ContentTypeArchiver {

    private final NotificationPublisher publisher;

    private final ContentTypeRepository repository;

    public ContentTypeArchiver(NotificationPublisher publisher, ContentTypeRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    @PostConstruct
    private void init() {
        publisher.getSinglePublisher().subscribe(contentType -> repository.save(contentType.type()));
    }
}
