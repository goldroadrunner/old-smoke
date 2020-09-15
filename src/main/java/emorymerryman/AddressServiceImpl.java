package emorymerryman;

import java.util.Random;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * Address Business Logic.
 **/
@Service
public class AddressServiceImpl implements AddressService {
    /**
     * For injecting randomness.
     **/
    private final Random random;

    /**
     * The number of digits in a house number.
     **/
    private static final int HOUSE_NUMBER_DIGITS = 5;

    /**
     * A collection of street names.
     **/
    private static final String[]STREETS = {
        "Falton Drive NE",
        "MISIÓN DE SAN LORENZO",
        "Hamerstraat",
        "W REARDON ST"
    };

    /**
     * The number of digits in a postal code.
     **/
    private static final int POSTAL_CODE_DIGITS = 9;

    /**
     * A collection of city names.
     **/
    private static final String[]CITIES = {
        "Cloverdale",
        "Villahermosa",
        "Grou",
        "Golden Triangle"
    };

    /**
     * A collection of county names.
     **/
    private static final String[]COUNTIES = {
        "Edmonton",
        "Centro",
        "Leeuwarden",
        "Denver"
    };

    /**
     * A collection of state names.
     **/
    private static final String[]STATES = {
        "Alberta",
        "Tabasco",
        "Friesland",
        "Colorado"
    };

    /**
     * A collection of state codes.
     **/
    private static final String[]STATE_CODES = {"AB", "TAB", "FR", "CO" };

    /**
     * A collection of country names.
     **/
    private static final String[]COUNTRIES = {
        "Canada",
        "Mexico",
        "Netherlands",
        "United States of America"
    };

    /**
     * A collection of country codes.
     **/
    private static final String[]COUNTRY_CODES = {"CAN", "MEX", "NLD", "USA" };

    /**
     * Construct an AddressService based on the specified Random.
     *
     * Since the randomness is injected in, this class is
     * deterministic.
     *
     * @param randomParameter the specified injected randomness
     **/
    AddressServiceImpl(final Random randomParameter) {
        random = randomParameter;
    }

    /**
     * Pick a random string from the specified string array.
     *
     * @param arr the specified string array
     * @return a random string
     **/
    private String pick(final String[]arr) {
        return arr[random.nextInt(arr.length)];
    }

    /**
     * Raise to the specified positive power of 10.
     *
     * @param digits the specified power
     * @return a power of ten
     **/
    private int pow10(final int digits) {
        if (digits <= 0) {
            return 1;
        } else {
            return 10 * pow10(digits - 1);
        }
    }

    /**
     * Generate a random left-zero padded number of the specified length.
     *
     * @param digits the specified length
     * @return a left-zero padded number of the specified length
     **/
    private String number(final int digits) {
        return format("%0" + digits + "d", random.nextInt(pow10(digits)));
    }

    /**
     * Generate a random address.
     *
     * @return a random address
     **/
    @Override
    public final Address address() {
        final String house = number(HOUSE_NUMBER_DIGITS);
        final String street = pick(STREETS);
        final String postalCode = number(POSTAL_CODE_DIGITS);
        final String city = pick(CITIES);
        final String county = pick(COUNTIES);
        final String state = pick(STATES);
        final String stateCode = pick(STATE_CODES);
        final String country = pick(COUNTRIES);
        final String countryCode = pick(COUNTRY_CODES);
        return new Address() {
            @Override
            public String getHouse() {
                return house;
            }

            @Override
            public String getStreet() {
                return street;
            }

            @Override
            public String getPostalCode() {
                return postalCode;
            }

            @Override
            public String getCity() {
                return city;
            }

            @Override
            public String getCounty() {
                return county;
            }

            @Override
            public String getState() {
                return state;
            }

            @Override
            public String getStateCode() {
                return stateCode;
            }

            @Override
            public String getCountry() {
                return country;
            }

            @Override
            public String getCountryCode() {
                return countryCode;
            }
        };
    };
}
