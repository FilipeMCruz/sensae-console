package sharespot.services.datadecoder.infrastructure.persistence.filesystem.mapper;

import sharespot.services.datadecoder.domain.DataDecoder;
import sharespot.services.datadecoder.domain.SensorTypeId;
import sharespot.services.datadecoder.domain.SensorTypeScript;
import sharespot.services.datadecoder.infrastructure.persistence.filesystem.model.DataDecoderPostgres;

public class DataDecoderMapper {

    public static DataDecoder postgresToDomain(DataDecoderPostgres postgres) {
        var device = SensorTypeId.of(postgres.deviceType);
        var script = SensorTypeScript.of(postgres.script);
        return new DataDecoder(device, script);
    }
}
