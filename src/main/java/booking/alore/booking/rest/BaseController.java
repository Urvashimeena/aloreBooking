package booking.alore.booking.rest;


import booking.alore.booking.rest.model.ErrorResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;



@Slf4j
@RestControllerAdvice
public class BaseController
{
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<ErrorResponseModel> processTimeOutError(AsyncRequestTimeoutException ex)
    {
        ErrorResponseModel responseModel = new ErrorResponseModel(ex.getMessage(), 500);
        log.error("connection time out");
        return new ResponseEntity<ErrorResponseModel>(responseModel, HttpStatus.BAD_REQUEST);
    }
}
