package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderHandlerService;
import pt.sensae.services.data.decoder.master.backend.application.RoutingKeysProvider;

@Component
public class SensorDataEmitter {

    public SensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, DataDecoderHandlerService service, RoutingKeysProvider provider) {
        var info = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
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
