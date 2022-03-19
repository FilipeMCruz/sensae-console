package sharespot.services.datadecodermaster.infrastructure.persistence.postgres.mapper;

import sharespot.services.datadecodermaster.domain.DataDecoder;
import sharespot.services.datadecodermaster.domain.SensorTypeId;
import sharespot.services.datadecodermaster.domain.SensorTypeScript;
import sharespot.services.datadecodermaster.infrastructure.persistence.postgres.model.DataDecoderPostgres;

public class DataDecoderMapper {

    public static DataDecoder postgresToDomain(DataDecoderPostgres postgres) {
        var device = SensorTypeId.of(postgres.deviceType);
        var script = SensorTypeScript.of(postgres.script);
        return new DataDecoder(device, script);
    }

    public static DataDecoderPostgres domainToPostgres(DataDecoder postgres) {
        var dataDecoderPostgres = new DataDecoderPostgres();
        dataDecoderPostgres.deviceType = postgres.getId().getValue();
        dataDecoderPostgres.script = postgres.getScript().getValue();
        return dataDecoderPostgres;
    }
}
