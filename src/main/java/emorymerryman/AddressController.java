package emorymerryman;

import org.springframework.http.ResponseEntity;

/**
 * The application.
 **/
interface AddressController {
    /**
     * Respond to a request for a random address.
     *
     * @return a random address response
     **/
    ResponseEntity<Address> address();
}
