package emorymerryman;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

/**
 * Test the entry point of the application.
 **/
final class ApplicationTest {
    /**
     * Test that the application is producing a Random.  This is the
     * source of the "randomness".  The application is deterministic.
     **/
    @Test
    public void testRandom() {
        assertEquals(
                     1601656690,
                     new Application().random().nextInt(),
                     "The \"randomness\" is other than specified."
                     );
    }

    /**
     * Test the application entry point.
     **/
    @Test
    public void testMain() {
        MockedStatic mocked = mockStatic(SpringApplication.class);
        final String[]args = {};
        // doNothing().when(SpringApplication.class);
        Application.main(args);
        // mocked.verify(SpringApplication.class).run(Application.class, args);
    }
}
