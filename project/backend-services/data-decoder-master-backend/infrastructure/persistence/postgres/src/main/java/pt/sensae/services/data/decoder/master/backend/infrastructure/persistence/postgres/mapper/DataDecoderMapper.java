package pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeScript;
import pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.postgres.model.DataDecoderPostgres;

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
