package booking.alore.booking.rest.service;

import booking.alore.booking.rest.model.SelectQuery;

public interface QueryService {

    void executeSelect(SelectQuery query);
}
