package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class DummyResolver implements GraphQLQueryResolver {
    
    public String dummy() {
        return "FIX this bug Spring Devs: i always need a Query Resolver, even in a Subscription only Service";
    }
}
