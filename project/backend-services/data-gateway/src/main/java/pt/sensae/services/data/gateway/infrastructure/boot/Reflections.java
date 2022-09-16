package pt.sensae.services.data.gateway.infrastructure.boot;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.data.gateway.application.ExceptionDetail;

@RegisterForReflection(targets = {
        ExceptionDetail.class,
})
public class Reflections {
}
