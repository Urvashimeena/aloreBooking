package booking.alore.booking.rest.controller;


import booking.alore.booking.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping(value = "/restapi/booking/",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController extends BaseController
{
    @PostMapping(value = "/select")
    public StreamingResponseBody selectAsync(@RequestBody SelectQuery selectQuery, @PathVariable String externalSysId,
                                             @PathVariable String tenantId) {

        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                queryService.executeSelect(selectQuery, externalSysId, outputStream);
            }
        };

    }
}
