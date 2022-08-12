package pt.sensae.services.data.gateway.infrastructure.endpoint.rest;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import pt.sensae.services.data.gateway.application.ExceptionDetail;
import pt.sensae.services.data.gateway.application.NotValidException;
import pt.sensae.services.data.gateway.application.SensorDataPublisherService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sensor-data")
public class DataController {

    @Inject
    SensorDataPublisherService service;

    @ConfigProperty(name = "sensae.auth.key")
    String secret;

    @ServerExceptionMapper
    public RestResponse<ExceptionDetail> mapException(NotValidException x) {
        return RestResponse.status(Response.Status.BAD_REQUEST, x.getError());
    }

    @POST
    @Path("{infoType}/{sensorType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<String> postSensorData(
            @RestHeader("Authorization") String auth,
            String sensorData,
            @RestPath String infoType,
            @RestPath String sensorType) {
        if (!secret.equalsIgnoreCase(auth))
            return RestResponse.status(RestResponse.Status.UNAUTHORIZED);
        
        service.registerSensorData(sensorData, "default", infoType, sensorType);
        return RestResponse.accepted();
    }

    @POST
    @Path("{channel}/{infoType}/{sensorType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<String> postSensorData(
            @RestHeader("Authorization") String auth,
            String sensorData,
            @RestPath String channel,
            @RestPath String infoType,
            @RestPath String sensorType) {
        if (!secret.equalsIgnoreCase(auth))
            return RestResponse.status(RestResponse.Status.UNAUTHORIZED);

        service.registerSensorData(sensorData, channel, infoType, sensorType);
        return RestResponse.accepted();
    }
}
