package sharespot.services.fastdatastore.application.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class RoutingKeys {

    public String infoType;

    public String sensorTypeId;

    public String channel;

    public String records;

    public String gps;

    public String tempC;

    public RoutingKeys(String infoType, String sensorTypeId, String channel, String records, String gps, String tempC) {
        this.infoType = infoType;
        this.sensorTypeId = sensorTypeId;
        this.channel = channel;
        this.records = records;
        this.gps = gps;
        this.tempC = tempC;
    }

    public RoutingKeys() {
    }

    @Override
    public String toString() {
        return "data." + infoType + "." + sensorTypeId + "." + channel + "." + records + "." + gps + "." + tempC;
    }

    public static RoutingKeysBuilder builder() {
        return new RoutingKeysBuilder(RoutingKeysBuilderOptions.CONSUMER);
    }

    public static RoutingKeysBuilder builder(RoutingKeysBuilderOptions options) {
        return new RoutingKeysBuilder(options);
    }

    public static class RoutingKeysBuilder {

        Logger logger = LoggerFactory.getLogger(RoutingKeysBuilder.class);

        public static final String KEEP = "<keep>";
        public static final String ANY = "*";

        private String infoType;

        private String sensorTypeId;

        private String channel;

        private String records;

        private String gps;

        private String tempC;

        private final RoutingKeysBuilderOptions options;

        private RoutingKeysBuilder(RoutingKeysBuilderOptions options) {
            this.options = options;
        }

        public RoutingKeysBuilder withInfoType(InfoTypeOptions infoType) {
            this.infoType = infoType.value();
            return this;
        }

        public RoutingKeysBuilder withAnyInfoType() {
            this.infoType = ANY;
            return this;
        }

        public RoutingKeysBuilder keepInfoType() {
            this.infoType = KEEP;
            return this;
        }

        public RoutingKeysBuilder withSensorTypeId(String sensorTypeId) {
            this.sensorTypeId = sensorTypeId;
            return this;
        }

        public RoutingKeysBuilder withAnySensorTypeId() {
            this.sensorTypeId = ANY;
            return this;
        }

        public RoutingKeysBuilder keepSensorTypeId() {
            this.sensorTypeId = KEEP;
            return this;
        }

        public RoutingKeysBuilder withChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public RoutingKeysBuilder withAnyChannel() {
            this.channel = ANY;
            return this;
        }

        public RoutingKeysBuilder keepChannel() {
            this.channel = KEEP;
            return this;
        }

        public RoutingKeysBuilder withDefaultChannel() {
            return withChannel("default");
        }

        public RoutingKeysBuilder withRecords(RecordsOptions records) {
            this.records = records.value();
            return this;
        }

        public RoutingKeysBuilder withAnyRecords() {
            this.records = ANY;
            return this;
        }

        public RoutingKeysBuilder keepRecords() {
            this.records = KEEP;
            return this;
        }

        public RoutingKeysBuilder withGps(GPSDataOptions gps) {
            this.gps = gps.value();
            return this;
        }

        public RoutingKeysBuilder withAnyGps() {
            this.gps = ANY;
            return this;
        }

        public RoutingKeysBuilder keepGps() {
            this.gps = KEEP;
            return this;
        }

        public RoutingKeysBuilder withTempC(TempCDataOptions tempC) {
            this.tempC = tempC.value();
            return this;
        }

        public RoutingKeysBuilder withAnyTempC() {
            this.tempC = ANY;
            return this;
        }

        public RoutingKeysBuilder keepTempC() {
            this.tempC = KEEP;
            return this;
        }

        public Optional<RoutingKeys> missingAsAny() {
            this.infoType = (this.infoType == null || this.infoType.isBlank()) ? ANY : this.infoType;
            this.sensorTypeId = (this.sensorTypeId == null || this.sensorTypeId.isBlank()) ? ANY : this.sensorTypeId;
            this.channel = (this.channel == null || this.channel.isBlank()) ? ANY : this.channel;
            this.records = (this.records == null || this.records.isBlank()) ? ANY : this.records;
            this.gps = (this.gps == null || this.gps.isBlank()) ? ANY : this.gps;
            this.tempC = (this.tempC == null || this.tempC.isBlank()) ? ANY : this.tempC;
            return build();
        }

        public Optional<RoutingKeys> from(RoutingKeys consumer) {
            this.infoType = this.infoType.equals(KEEP) ? consumer.infoType : this.infoType;
            this.sensorTypeId = this.sensorTypeId.equals(KEEP) ? consumer.sensorTypeId : this.sensorTypeId;
            this.channel = this.channel.equals(KEEP) ? consumer.channel : this.channel;
            this.records = this.records.equals(KEEP) ? consumer.records : this.records;
            this.gps = this.gps.equals(KEEP) ? consumer.gps : this.gps;
            this.tempC = this.tempC.equals(KEEP) ? consumer.tempC : this.tempC;
            return build();
        }

        public Optional<RoutingKeys> from(String routingKeys) {
            var splinted = routingKeys.split("\\.");
            this.infoType = splinted[1];
            this.sensorTypeId = splinted[2];
            this.channel = splinted[3];
            this.records = splinted[4];
            this.gps = splinted[5];
            this.tempC = splinted[6];
            return build();
        }

        public Optional<RoutingKeys> build() {
            var routingKeys = new RoutingKeys(infoType, sensorTypeId, channel, records, gps, tempC);
            return toOptional(routingKeys);
        }

        private Optional<RoutingKeys> toOptional(RoutingKeys routingKeys) {
            if (routingKeys.infoType == null || routingKeys.infoType.isBlank()) {
                logger.warn("Info Type Routing Key is Missing");
                return Optional.empty();
            }
            if (routingKeys.sensorTypeId == null || routingKeys.sensorTypeId.isBlank()) {
                logger.warn("Sensor Type ID Routing Key is Missing");
                return Optional.empty();
            }
            if (routingKeys.channel == null || routingKeys.channel.isBlank()) {
                logger.warn("Channel Routing Key is Missing");
                return Optional.empty();
            }
            if (routingKeys.records == null || routingKeys.records.isBlank()) {
                logger.warn("Records Routing Key is Missing");
                return Optional.empty();
            }
            if (routingKeys.gps == null || routingKeys.gps.isBlank()) {
                logger.warn("GPSData Routing Key is Missing");
                return Optional.empty();
            }
            if (routingKeys.tempC == null || routingKeys.tempC.isBlank()) {
                logger.warn("TempCData Routing Key is Missing");
                return Optional.empty();
            }
            if (!routingKeys.sensorTypeId.matches("[a-zA-Z0-9]+") && !ANY.equals(routingKeys.sensorTypeId)) {
                logger.warn("Sensor Type ID Routing Key is invalid");
                return Optional.empty();
            }
            if (RoutingKeysBuilderOptions.SUPPLIER.equals(options)) {
                if (ANY.equals(routingKeys.infoType)) {
                    logger.warn("Info Type Routing Key can't be of type *");
                    return Optional.empty();
                }
                if (ANY.equals(routingKeys.sensorTypeId)) {
                    logger.warn("Sensor Type ID Routing Key can't be of type *");
                    return Optional.empty();
                }
                if (ANY.equals(routingKeys.channel)) {
                    logger.warn("Channel Routing Key can't be of type *");
                    return Optional.empty();
                }
                if (ANY.equals(routingKeys.records)) {
                    logger.warn("Records Routing Key can't be of type *");
                    return Optional.empty();
                }
                if (ANY.equals(routingKeys.gps)) {
                    logger.warn("GPSData Routing Key can't be of type *");
                    return Optional.empty();
                }
                if (ANY.equals(routingKeys.tempC)) {
                    logger.warn("TempCData Routing Key can't be of type *");
                    return Optional.empty();
                }
                if (!routingKeys.sensorTypeId.matches("[a-zA-Z0-9]+")) {
                    logger.warn("Sensor Type ID Routing Key is invalid");
                    return Optional.empty();
                }
            }
            return Optional.of(routingKeys);
        }
    }
}
