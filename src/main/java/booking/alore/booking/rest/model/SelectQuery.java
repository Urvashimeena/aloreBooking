package booking.alore.booking.rest.model;

import lombok.Data;

@Data
public class SelectQuery {
    private String query;
    private Value[] values;
}
