import sql from "k6/x/sql";

export function createDetectHigherTempRule(upperLimitTemp, domain) {
  rulesDB.exec(`INSERT INTO public.rule (applied, deleted, content, id, owners)
    VALUES (false, false, 'package rules.project.test;

    import pt.sharespot.iot.core.data.model.data.DataUnitReadingsDTO;
    import pt.sharespot.iot.core.data.model.DataUnitDTO;
    import pt.sharespot.iot.core.data.model.device.records.DeviceRecordEntryDTO;
    import pt.sharespot.iot.core.data.model.properties.PropertyName;
    import pt.sharespot.iot.core.alert.model.AlertBuilder;
    import pt.sharespot.iot.core.alert.model.CorrelationDataBuilder;
    import java.util.List;
    import java.util.UUID
    import pt.sharespot.iot.core.alert.model.AlertLevel
    
    global pt.sharespot.iot.core.alert.model.AlertDispatcherService dispatcher;
    
    dialect "mvel"
    
    rule "Measure with Temperature above ${upperLimitTemp}"
        when
            $data : DataUnitDTO(
                getSensorData().hasProperty(PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE),
                getSensorData().hasProperty(PropertyName.TEMPERATURE) &&
                getSensorData().temperature.celsius > ${upperLimitTemp}
            )
        then
            dispatcher.publish(AlertBuilder.create()
                                .setCategory("perfTest")
                                .setSubCategory("alarmPerformance")
                                .setDescription($data.dataId + ";" + $data.reportedAt + ";" + $data.device.id)
                                .setLevel(AlertLevel.CRITICAL)
                                .setContext(CorrelationDataBuilder.create()
                                    .setDeviceIds($data.device.id)
                                    .build())
                                .build());
    end', 'Performance Test', '{"${domain}"}')
    `);
}

export function clearRules() {
  rulesDB.exec("TRUNCATE public.rule CASCADE;");
  rulesDB.close();
}

const rulesDB = sql.open(
  "postgres",
  `postgres://user:${__ENV.SENSAE_COMMON_DATABASE_PASSWORD}@localhost:5494/rule?sslmode=disable`
);
