package pt.sensae.services.data.decoder.master.backend.application.helpers;

import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeScript;
import pt.sensae.services.data.decoder.master.backend.domainservices.auth.TenantInfo;

import java.util.Collections;
import java.util.List;

public class CommonObjectsFactory {

    public static SensorTypeId sensorTypeId() {
        return SensorTypeId.of("lgt92");
    }

    public static DataDecoder dataDecoder() {
        return new DataDecoder(SensorTypeId.of("lgt92"), SensorTypeScript.of("asmc"));
    }
    
    public static DataDecoder otherDataDecoder() {
        return new DataDecoder(SensorTypeId.of("em300th"), SensorTypeScript.of("alskd"));
    }

    public static TenantInfo validTenantInfo() {
        var tenantInfo = new TenantInfo();
        tenantInfo.permissions = List.of("data_decoders:decoders:edit", "data_decoders:decoders:delete", "data_decoders:decoders:read");
        return tenantInfo;
    }

    public static TenantInfo invalidTenantInfo() {
        var tenantInfo = new TenantInfo();
        tenantInfo.permissions = Collections.emptyList();
        return tenantInfo;
    }
}
