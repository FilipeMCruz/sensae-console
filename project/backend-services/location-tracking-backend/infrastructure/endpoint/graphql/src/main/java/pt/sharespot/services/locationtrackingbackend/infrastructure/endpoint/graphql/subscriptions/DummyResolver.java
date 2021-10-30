package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class DummyResolver {

    @QueryMapping
    public String dummy() {
        return "FIX this bug Spring Devs: i always need a Query Resolver, even in a Subscription only Service";
    }
}
