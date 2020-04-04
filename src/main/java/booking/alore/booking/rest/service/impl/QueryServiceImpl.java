package booking.alore.booking.rest.service.impl;

import booking.alore.booking.rest.model.SelectQuery;
import booking.alore.booking.rest.model.Value;
import booking.alore.booking.rest.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service("QueryService")
public class QueryServiceImpl implements QueryService {

    @Autowired
    private static DataSource dataSource;

    @Override
    public void executeSelect(SelectQuery query) {
        log.info("executing - {}", query);
        try {
            executeSelectQuery(query);
        } catch (SQLException e) {
            log.error("unable to execute sql query");
        }

    }

    static void executeSelectQuery(SelectQuery selectQuery) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = dataSource.getConnection();

            preparedStatement = conn.prepareStatement(selectQuery.getQuery());
            if (ArrayUtils.isNotEmpty(selectQuery.getValues())) {
                setValuesInPreparedStatement(preparedStatement, selectQuery.getValues());
            }
        }
        catch (SQLException ex) {

            String errorMessage = "error occurred while executing query - "
                                  + ex.getMessage();
            log.error(errorMessage, ex);
            throw new SQLException(errorMessage);
        } finally {
            try  {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                String errorMessage = "error while closing db resources - "
                                      + ex.getMessage();
                log.error(errorMessage, ex);
            }
        }

    }

    private static void setValuesInPreparedStatement(PreparedStatement preparedStatement, Value[] rowValues)
        throws SQLException {
        for (Value value : rowValues) {
            switch (value.getValueType()) {
                case STRING:
                    preparedStatement.setString(value.getIndex(), value.getValue());
                    break;
                default:
                    throw new SQLException("invalid value type ");
            }
        }
    }

}
