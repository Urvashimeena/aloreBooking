package booking.alore.booking.rest.controller;
import booking.alore.booking.rest.model.UpdateQuery;
import booking.alore.booking.rest.model.Value;
import booking.alore.booking.rest.util.VALUE_TYPE;
import org.junit.Test;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class QueryControllerTest
{
    @Autowired
    QueryController queryController;

    @Test
    public void testUpdateData()
    {
        UpdateQuery updateQuery = new UpdateQuery();
        Value[]  values = new Value[2];
        Value value = new Value();
        value.setIndex(1);
        value.setValueType(VALUE_TYPE.STRING);
        value.setValue("Lake City Palace Hotel");
        Value value1 = new Value();
        value1.setIndex(1);
        value1.setValueType(VALUE_TYPE.STRING);
        value1.setValue("6");
        values[0] = value;
        values[1] = value1;
        String query = "update hotel set hotelName=? where hotelId=?";
        updateQuery.setQuery(query);
        updateQuery.setValues(values);
        queryController.updateData(updateQuery);
    }
}
