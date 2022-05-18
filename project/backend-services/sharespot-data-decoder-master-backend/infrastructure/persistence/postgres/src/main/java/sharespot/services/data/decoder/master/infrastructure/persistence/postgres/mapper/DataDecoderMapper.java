package sharespot.services.data.decoder.master.infrastructure.persistence.postgres.mapper;

import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorTypeId;
import sharespot.services.data.decoder.master.domain.SensorTypeScript;
import sharespot.services.data.decoder.master.infrastructure.persistence.postgres.model.DataDecoderPostgres;

public class DataDecoderMapper {

    public static DataDecoder postgresToDomain(DataDecoderPostgres postgres) {
        var device = SensorTypeId.of(postgres.deviceType);
        var script = SensorTypeScript.of(postgres.script);
        return new DataDecoder(device, script);
    }

    public static DataDecoderPostgres domainToPostgres(DataDecoder postgres) {
        var dataDecoderPostgres = new DataDecoderPostgres();
        dataDecoderPostgres.deviceType = postgres.id().getValue();
        dataDecoderPostgres.script = postgres.script().value();
        return dataDecoderPostgres;
    }
}
