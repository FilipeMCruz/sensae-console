package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationEventHandlerService;
import pt.sensae.services.data.processor.master.backend.application.RoutingKeysProvider;

@Component
public class DataProcessorInfoEmitter {

    public DataProcessorInfoEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, DataTransformationEventHandlerService service, RoutingKeysProvider provider) {
        var info = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_PROCESSOR)
                .withContextType(ContextTypeOptions.DATA_PROCESSOR)
                .withOperationType(OperationTypeOptions.INFO)
                .build();
        if (info.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, info.get()
                        .toString(), outData));
    }
}
