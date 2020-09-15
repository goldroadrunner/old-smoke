package emorymerryman;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * Error Handler.
 **/
@Controller
public final class ErrorControllerImpl implements ErrorController {
    /**
     * Handle errors that occur.
     *
     * @param request the request
     * @return an error message for the user.
     **/
    @RequestMapping("/error")
    @ResponseBody
    public String handleError(final HttpServletRequest request) {
        Integer statusCode =
            (Integer) request.getAttribute("javax.servlet.error.status_code");
        return String.format("<html><body>"
                             + "<h2>Error Page</h2>"
                             + "<div>Status code: <b>%s</b></div>"
                             + "<a href=\"/randomizer/address\">"
                             + "Random Address</a>"
                             + "<body></html>",
                             statusCode
                             );
    }

    /**
     * {@InheritDoc}.
     *
     * @return {@InheritDoc}
     **/
    @Override
    public String getErrorPath() {
        return null;
    }
}
