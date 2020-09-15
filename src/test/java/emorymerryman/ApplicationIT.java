package emorymerryman;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Integration Tests.
 **/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
@SpringBootTest(webEnvironment = RANDOM_PORT)
public final class ApplicationIT {
    /**
     * Providing a constructor is necessary for RESTAssure.
     **/
    public ApplicationIT() {
    }

    /**
     * The port.
     **/
    @LocalServerPort
    private int port;

    /**
     * Test the happy path.
     *
     * Looks random to me.
     **/
    @Test
    public void testHappyPath() {
        verify(
               "Tabasco", "Netherlands", "CO", "Centro", "145862884", "28345",
               "MISIÓN DE SAN LORENZO", "USA", "Cloverdale"
               );
        verify(
               "Alberta", "United States of America", "AB", "Edmonton",
               "388987889", "08398", "MISIÓN DE SAN LORENZO", "CAN", "Grou"
               );
        verify(
               "Tabasco", "Mexico", "FR", "Centro", "291349504", "33722",
               "Hamerstraat", "CAN", "Golden Triangle"
               );
        verify(
               "Colorado", "Netherlands", "CO", "Denver", "723448415", "61541",
               "Falton Drive NE", "USA", "Grou"
               );
        verify(
               "Tabasco", "Mexico", "TAB", "Edmonton", "469604646", "93239",
               "Falton Drive NE", "USA", "Golden Triangle"
               );
    }

    /**
     * Verify an address against the specified
     * values.
     *
     * @param state the specified state
     * @param country the specified country
     * @param stateCode the specified stateCode
     * @param county the specified county
     * @param postalCode the specified postalCode
     * @param house the sepcified house
     * @param street the specified street
     * @param countryCode the specified countryCode
     * @param city the specified city
     **/
    private void verify(
                        final String state,
                        final String country,
                        final String stateCode,
                        final String county,
                        final String postalCode,
                        final String house,
                        final String street,
                        final String countryCode,
                        final String city
                        ) {
        given()
            .port(port)
            .when()
            .get("/randomizer/address")
            .then()
            .statusCode(200)
            .header("Cache-Control", equalTo("max-age=0"))
            .body("state", equalTo(state))
            .body("country", equalTo(country))
            .body("stateCode", equalTo(stateCode))
            .body("county", equalTo(county))
            .body("postalCode", equalTo(postalCode))
            .body("house", equalTo(house))
            .body("street", equalTo(street))
            // City is capitalized on purpose
            .body("City", equalTo(city));
    }
}
