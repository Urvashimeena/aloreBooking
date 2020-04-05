package booking.alore.booking.rest.model;

import lombok.Data;

@Data
public class InsertQuery {
    private String query;
    private Value[][] values;
}
