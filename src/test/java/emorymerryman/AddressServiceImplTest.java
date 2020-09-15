package emorymerryman;

import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the AddressService.
 **/
final class AddressServiceImplTest {
    /**
     * Test 5 addresses.
     * Manual inspection of the verification code,
     * suggests to me that it is random.
     **/
    @Test
    public void testAddress() {
        final AddressService service = new AddressServiceImpl(new Random(8242));
        verifyAddress(
                      service.address(), "00270", "Hamerstraat", "120669222",
                      "Villahermosa", "Centro", "Friesland", "CO",
                      "Netherlands", "NLD"
                      );
        verifyAddress(
                      service.address(), "70119", "Falton Drive NE",
                      "697426324", "Grou", "Leeuwarden", "Colorado", "FR",
                      "United States of America", "USA"
                      );
        verifyAddress(
                      service.address(), "83032", "Falton Drive NE",
                      "462267020", "Villahermosa", "Edmonton", "Tabasco", "AB",
                      "Canada", "NLD");
        verifyAddress(
                      service.address(), "61674", "Hamerstraat", "019715140",
                      "Villahermosa", "Denver", "Friesland", "AB",
                      "United States of America", "MEX"
                      );
        verifyAddress(
                      service.address(), "49468", "W REARDON ST", "537459230",
                      "Villahermosa", "Denver", "Friesland", "CO",
                      "Netherlands", "MEX"
                      );
    }

    /**
     * Verify the specified address against the specified particulars.
     *
     * @param address the specified address
     * @param house the specified house
     * @param street the specified street
     * @param postalCode the specified postalCode
     * @param city the specified city
     * @param county the specified county
     * @param state the specified state
     * @param stateCode the specified stateCode
     * @param country the specified country
     * @param countryCode the specified countryCode
     **/
    private void verifyAddress(
                               final Address address,
                               final String house,
                               final String street,
                               final String postalCode,
                               final String city,
                               final String county,
                               final String state,
                               final String stateCode,
                               final String country,
                               final String countryCode
                               ) {
        assertEquals(house, address.getHouse());
        assertEquals(street, address.getStreet());
        assertEquals(postalCode, address.getPostalCode());
        assertEquals(city, address.getCity());
        assertEquals(county, address.getCounty());
        assertEquals(state, address.getState());
        assertEquals(stateCode, address.getStateCode());
        assertEquals(country, address.getCountry());
        assertEquals(countryCode, address.getCountryCode());
    }
}
