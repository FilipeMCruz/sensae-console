package pt.sensae.services.data.processor.flow.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sensae.services.data.processor.flow.domain.DataProcessorRepository;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformations;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DataProcessorExecutor {

    @Inject
    DataProcessorRepository cache;

    @Inject
    ObjectMapper mapper;

    public Optional<SensorDataDTO> inToOut(JsonNode inDto, SensorTypeId typeId) {
        return cache.findById(typeId)
                .flatMap(dt -> process(dt.transform(), inDto));
    }

    public Optional<SensorDataDTO> process(PropertyTransformations transformations, JsonNode in) {
        ObjectNode newNode = this.mapper.createObjectNode();
        transformations.getTransform().forEach(property -> property.transfer(in, newNode));

        try {
            return Optional.of(this.mapper.treeToValue(newNode, SensorDataDTO.class));
        } catch (JsonProcessingException var5) {
            return Optional.empty();
        }
    }
}
