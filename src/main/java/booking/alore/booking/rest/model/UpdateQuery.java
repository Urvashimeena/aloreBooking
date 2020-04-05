package booking.alore.booking.rest.model;

import lombok.Data;

@Data
public class UpdateQuery
{
    private String query;
    private Value[] values;
}
