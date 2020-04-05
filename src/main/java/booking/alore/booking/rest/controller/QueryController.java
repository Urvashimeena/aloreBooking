package booking.alore.booking.rest.controller1;


import booking.alore.booking.rest.BaseController;
import booking.alore.booking.rest.model.DeleteQuery;
import booking.alore.booking.rest.model.InsertQuery;
import booking.alore.booking.rest.model.SelectQuery;
import booking.alore.booking.rest.model.UpdateQuery;
import booking.alore.booking.rest.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping(value = "/restapi/booking/",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController extends BaseController
{
    @Autowired
    QueryService queryService;

    @PostMapping(value = "/selectdata")
    public StreamingResponseBody selectAsyc(@RequestBody SelectQuery selectQuery)
    {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                queryService.executeSelect(selectQuery,outputStream);
            }
        };
    }


    @PostMapping(value = "/insertData")
    public StreamingResponseBody addUserData(@RequestBody  InsertQuery insertQuery)
    {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                queryService.executeInsert(insertQuery);
            }
        };
    }

    @PostMapping(value = "/updateData")
    public StreamingResponseBody updateData(@RequestBody UpdateQuery updateQuery)
    {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                queryService.executeUpdate(updateQuery);
            }
        };
    }

    @PostMapping(value = "/deleteData")
    public StreamingResponseBody deleteData(@RequestBody DeleteQuery deleteQuery)
    {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                queryService.executeDelete(deleteQuery);
            }
        };
    }


}
