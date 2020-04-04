package booking.alore.booking.rest.model;

import booking.alore.booking.rest.util.VALUE_TYPE;
import lombok.Data;

@Data
public class Value {

    private int index;

    private VALUE_TYPE valueType;

    private String value;


}
