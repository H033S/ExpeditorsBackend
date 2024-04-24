package expeditors.backend.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.MimeType;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static java.util.stream.Collectors.toList;

/**
 * Two ways to handle global exceptions.  One is this class, the other
 * one is the LastStopHandler.
 * One key difference is that in the LastStopHandler you extend the ResponseEntityExceptionHandler
 * and override methods you are interested in.  Look at the code in ResponseEntityExceptionHandler
 * to see what you can override.
 * This approach seems like the easier one, and is preferred by Spring.  That is, if you have an
 * exception handler declared here, it will be called in preference
 * to anything declared in the LastStopHandler.
 * @author whynot
 */
@RestControllerAdvice
public class GlobalErrorHandler {


//    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<?> lastPortOfCall(Exception ex, WebRequest request) {
        return ResponseEntity.internalServerError().body("Last Port ;Unexpected Exception: " + ex);
    }
    /**
     * Handle validation errors for automatic validation, i.e with the @Valid annotation.
     * For this to be invoked, you have to have a controller argument of object type
     * to which you have attached the @Valid annotation.
     * Look at the end of StudentRestController for an example, which may be commented out
     * by default.
     * @param ex the exception thrown
     * @param request the incoming request
     * @return a bad request + restresult that contains the errors
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                             WebRequest request) {

        var errors = ex.getFieldErrors();

        List<String> errMsgs = errors.stream()
              .map(error -> "@Valid error:" + error.getField() + ": " + error.getDefaultMessage()
                    + ", supplied Value: " + error.getRejectedValue())
              .collect(toList());

        ResponseEntity<?> rr = ResponseEntity.badRequest().body(errMsgs);

        return rr;
    }
}