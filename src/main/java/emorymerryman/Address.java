package emorymerryman;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A representation of an address.
 **/
public interface Address {
    /**
     * House or street number.
     *
     * @return house or street number
     **/
    String getHouse();

    /**
     * Street name(in practice may also contain street number).
     *
     * @return street name(in practice may also contain street number)
     **/
    String getStreet();

    /**
     * An alphanumeric string included in a postal address to facilitate mail
     * sorting (a.k.a. post code, postcode, or ZIP code).
     *
     * @return an alphanumeric string included in a postal address to
     * facilitate mail sorting (a.k.a. post code, postcode, or ZIP code)
     **/
    String getPostalCode();

    /**
     * The name of the primary locality of the place.
     *
     * @return The name of the primary locality of the place.
     **/
    @JsonProperty("City")
    String getCity();

    /**
     * A division of a state; typically a secondary-level administrative
     * division of a country.
     *
     * @return a division of a state; typically a secondary-level
     * administrative division of a country
     **/
    String getCounty();

    /**
     * A division of a country; typically a first-level administrative division
     * of a country and/or a geographical region.
     *
     * @return a division of a country; typically a first-level administrative
     * division of a country and/or a geographical region
     **/
    String getState();

    /**
     * A code/abbreviation for the state division of a country.
     *
     * @return a code/abbreviation for the state division of a country
     **/
    String getStateCode();

    /**
     * The localised country name.
     *
     * @return the localised country name
     **/
    String getCountry();

    /**
     * A three-letter country code.
     *
     * @return a three-letter country code
     **/
    String getCountryCode();
}
