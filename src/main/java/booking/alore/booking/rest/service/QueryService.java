package booking.alore.booking.rest.service;

import booking.alore.booking.rest.model.DeleteQuery;
import booking.alore.booking.rest.model.InsertQuery;
import booking.alore.booking.rest.model.SelectQuery;
import booking.alore.booking.rest.model.UpdateQuery;

import java.io.OutputStream;

public interface QueryService {

    void executeSelect(SelectQuery selectQuery, OutputStream outputStream);
    void executeInsert(InsertQuery insertQuery);
    void executeUpdate(UpdateQuery updateQuery);
    void executeDelete(DeleteQuery deleteQuery);
}
