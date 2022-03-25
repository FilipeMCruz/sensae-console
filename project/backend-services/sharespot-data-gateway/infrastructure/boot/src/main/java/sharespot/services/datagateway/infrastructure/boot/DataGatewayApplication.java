package sharespot.services.datagateway.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.nativex.hint.SerializationHint;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;
import pt.sharespot.iot.core.routing.MessageSupplied;

@SpringBootApplication
@ComponentScan(basePackages = {"sharespot.services", "pt.sharespot"})
@TypeHint(types = MessageSupplied.class, access = {TypeAccess.PUBLIC_FIELDS, TypeAccess.DECLARED_CONSTRUCTORS, })
public class DataGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataGatewayApplication.class, args);
    }
}
