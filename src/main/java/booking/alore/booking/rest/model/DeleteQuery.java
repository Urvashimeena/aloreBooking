package booking.alore.booking.rest.model;

import lombok.Data;

@Data
public class DeleteQuery {
    private String query;
    private Value[] values;
}
