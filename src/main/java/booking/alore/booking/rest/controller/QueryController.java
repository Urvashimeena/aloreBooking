package booking.alore.booking.rest.controller;


import booking.alore.booking.rest.BaseController;
import booking.alore.booking.rest.model.SelectQuery;
import booking.alore.booking.rest.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.xml.transform.stream.StreamResult;
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
    @PostMapping(value = "/selecthotel")
    public StreamingResponseBody selectAsyc(SelectQuery selectQuery)
     {
         return new StreamingResponseBody() {
             @Override
             public void writeTo(OutputStream outputStream) throws IOException {
                 queryService.executeSelect(selectQuery);
             }
         };
     }

}
