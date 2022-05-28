package pt.sensae.services.notification.management.backend.domain.contentType;

import java.util.stream.Stream;

public interface ContentTypeRepository {

    Stream<ContentType> findAll();

    ContentType save(ContentType contentType);
}
