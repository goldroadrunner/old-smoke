package emorymerryman;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.servlet.http.HttpServletRequest;

/**
 * Test the Error Controller.
 **/
final class ErrorControllerImplTest {
    /**
     * Test the ErrorController.
     *
     * In the event of an error, the application should
     * return a page showing
     * <OL>
     *      <LI> the error status
     *      <LI> the error message
     *      <LI> and a link to the address page
     * </OL>
     *
     * The getErrorPath method is deprecated but we must
     * implement it.
     * It really does not matter what we return.
     **/
    @Test
    public void testHandleError() {
        final ErrorControllerImpl errorController = new ErrorControllerImpl();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("javax.servlet.error.status_code"))
            .thenReturn(new Integer(3964));
        assertEquals(
                     "<html><body>"
                     + "<h2>Error Page</h2>"
                     + "<div>Status code: <b>3964</b></div>"
                     + "<a href=\"/randomizer/address\">"
                     + "Random Address"
                     + "</a>"
                     + "<body></html>",
                     errorController.handleError(request),
                     "Generate an error page."
                     );
        assertNull(
                   errorController.getErrorPath(),
                   "implementing a deprecated method"
                   );
    }
}
