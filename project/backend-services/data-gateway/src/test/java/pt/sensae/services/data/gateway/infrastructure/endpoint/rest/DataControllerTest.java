package pt.sensae.services.data.gateway.infrastructure.endpoint.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class DataControllerTest {

    @Test
    public void testInfoTypeDetection() {
        given().when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "pass")
                .post("/sensor-data/fleet/wrong/lgt92")
                .then()
                .statusCode(400)
                .body("error", containsString("Info Type must be of value encoded or decoded"));
    }

    @Test
    public void testAuthenticationHeaderDetection() {
        given().when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "nopass")
                .post("/sensor-data/fleet/decoded/lgt92")
                .then()
                .statusCode(401);
    }

    @Test
    public void testChannelLongNamePrevention() {
        given().when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "pass")
                .post("/sensor-data/reallylongchannelname/decoded/lgt92")
                .then()
                .statusCode(400)
                .body("error", containsString("Channel info can only have a max of 15 letters or numbers"));
    }

    @Test
    public void testSensorTypeLongNamePrevention() {
        given().when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "pass")
                .post("/sensor-data/fleet/decoded/reallylongsensorname")
                .then()
                .statusCode(400)
                .body("error", containsString("Sensor Type info can only have a max of 15 letters or numbers"));
    }
}
