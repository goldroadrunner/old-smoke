package emorymerryman;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.springframework.http.CacheControl.maxAge;
import static org.springframework.http.ResponseEntity.ok;

/**
 * The application.
 **/
@Controller
final class AddressControllerImpl implements AddressController {
    /**
     * Used for generating addresses.
     **/
    private final AddressService addressService;

    /**
     * Constructs an AddressControllerImpl using the specified service.
     *
     * @param addressServiceParam the specified service
     **/
    AddressControllerImpl(final AddressService addressServiceParam) {
        this.addressService = addressServiceParam;
    }

    /**
     * Get a random address.
     *
     * @return a random address
     **/
    @Override
    @GetMapping("/randomizer/address")
    public ResponseEntity<Address> address() {
        return ok()
            .cacheControl(maxAge(0, NANOSECONDS))
            .body(addressService.address());
    };
}
