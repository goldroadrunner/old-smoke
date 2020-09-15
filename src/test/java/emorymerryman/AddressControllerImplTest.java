package emorymerryman;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;

/**
 * Test the AddressController.
 **/
final class AddressControllerImplTest {
    /**
     * Test the address method.
     *
     * <OL>
     *      <LI> It should return an EntityResponse with
     *           the same Address that the AddressService
     *           generated.
     *      <LI> It should set the status to 200 - OK.
     *      <LI> It should expire the cache - because this
     *           is a random address it should never be
     *           cached.
     * </OL>
     **/
    @Test
    public void testAddress() {
        final AddressService service = mock(AddressService.class);
        final Address address = mock(Address.class);
        final AddressController controller =
            new AddressControllerImpl(service);
        when(service.address()).thenReturn(address);
        ResponseEntity responseEntity = controller.address();
        assertSame(
                   address,
                   responseEntity.getBody(),
                   "Return the same address created by the service.");
        assertEquals(
                     200,
                     responseEntity.getStatusCodeValue(),
                     "The controller returns OK status.");
        assertEquals(
                     "max-age=0",
                     responseEntity.getHeaders().get(CACHE_CONTROL).get(0),
                     "The controller expires the cache.");
    }
}
