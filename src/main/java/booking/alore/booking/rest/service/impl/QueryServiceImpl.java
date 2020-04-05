package booking.alore.booking.rest.service.impl;

import booking.alore.booking.rest.model.*;
import booking.alore.booking.rest.service.QueryService;
import booking.alore.booking.rest.util.StreamingJsonResultSetExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Service("QueryService")
public class QueryServiceImpl implements QueryService {

    @Autowired
    private final DataSource dataSource;

    public QueryServiceImpl(DataSource dataSource) {this.dataSource = dataSource;}

    @Override
    public void executeSelect(SelectQuery query, OutputStream outputStream) {
        log.info("executing - {}", query);
        try {
            executeSelectQuery(query, outputStream, dataSource.getConnection());
        } catch (SQLException e) {
            log.error("unable to execute sql query");
        }

    }

    @Override
    public void executeUpdate(UpdateQuery updateQuery, OutputStream outputStream)
    {
        log.info("executing update query", updateQuery);
        try
        {
            executeUpdateQuery(updateQuery,dataSource.getConnection());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void executeDelete(DeleteQuery  deleteQuery)
    {
        log.info("executing delete query", deleteQuery);
        try
        {
            executeDeleteQuery(deleteQuery, dataSource.getConnection());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void executeInsert(InsertQuery insertQuery)
    {
        log.info("executing insert query", insertQuery);
        try {
            executeInsertQuery(insertQuery,dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    static  void  executeDeleteQuery(DeleteQuery deleteQuery, Connection conn) throws SQLException {
        log.info("starting to execute - {}", deleteQuery);
        PreparedStatement statement = null;
        try {

            conn.setAutoCommit(false);
            statement = conn.prepareStatement(deleteQuery.getQuery());

            for (Value value : deleteQuery.getValues()) {
                switch (value.getValueType()) {
                    case STRING:
                        statement.setString(value.getIndex(), value.getValue());
                        break;
                    default:
                        throw new SQLException( "invalid value type ");
                }
            }


            boolean isExecuted = statement.execute();
            log.info("statement executed committing transaction");
            conn.commit();
            if (isExecuted) {
                log.info("transaction committed");
            }
        } catch (SQLException e) {
            String errMsg = "error while executing insert statement - " + e.getMessage();
            log.error(errMsg, e);
            throw new SQLException(errMsg);
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error("error while closing resources - ", e);
            }
        }

    }


    static void executeUpdateQuery(UpdateQuery updateQuery, Connection conn) throws SQLException {
        log.info("starting to execute - {}", updateQuery);
        PreparedStatement statement = null;
        try {

            conn.setAutoCommit(false);
            statement = conn.prepareStatement(updateQuery.getQuery());

            for (Value value : updateQuery.getValues()) {
                switch (value.getValueType()) {
                    case STRING:
                        statement.setString(value.getIndex(), value.getValue());
                        break;
                    default:
                        throw new SQLException( "invalid value type ");
                }
            }


            boolean isExecuted = statement.execute();
            log.info("statement executed committing transaction");
            conn.commit();
            if (isExecuted) {
                log.info("transaction committed");
            }
        } catch (SQLException e) {
            String errMsg = "error while executing insert statement - " + e.getMessage();
            log.error(errMsg, e);
            throw new SQLException(errMsg);
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error("error while closing resources - ", e);
            }
        }

    }

    static void executeInsertQuery(InsertQuery insertQuery, Connection conn) throws SQLException {
        int[] updateCounts;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(insertQuery.getQuery());
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(insertQuery.getQuery());

            for (Value[] rowValues: insertQuery.getValues()) {
                setValuesInPreparedStatement(preparedStatement, rowValues);
                preparedStatement.addBatch();
            }


            updateCounts = preparedStatement.executeBatch();
            conn.commit();
            if (updateCounts.length > 0)
            {
                log.info("transaction committed");
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
    static void executeSelectQuery(SelectQuery selectQuery, OutputStream os, Connection conn) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(selectQuery.getQuery());
            conn.setAutoCommit(false);
            if (ArrayUtils.isNotEmpty(selectQuery.getValues())) {
                setValuesInPreparedStatement(preparedStatement, selectQuery.getValues());
            }
            ResultSet queryResult = preparedStatement.executeQuery();

            StreamingJsonResultSetExtractor resultSetExtractor = new StreamingJsonResultSetExtractor(os);
            resultSetExtractor.extractData(queryResult);

            log.info("committing the transaction");
            conn.commit();
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
