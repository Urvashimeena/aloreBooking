package booking.alore.booking.rest.util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class StreamingJsonResultSetExtractor implements ResultSetExtractor
{
    private final OutputStream os;

    public StreamingJsonResultSetExtractor(final OutputStream os) {
        this.os = os;
    }

    @Override
    public Void extractData(final ResultSet rs) {
        final ObjectMapper objectMapper = new ObjectMapper();




        try  {
            JsonGenerator jg = objectMapper.getFactory().createGenerator(
                os, JsonEncoding.UTF8);
            writeResultSetToJson(rs, jg);
            jg.flush();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static void writeResultSetToJson(final ResultSet rs,
                                             final JsonGenerator jg)
        throws SQLException, IOException {
        final ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        jg.writeStartArray();
        while (rs.next()) {
            jg.writeStartObject();
            for (int i = 1; i <= columnCount; i++) {
                jg.writeObjectField(rsmd.getColumnName(i), rs.getObject(i));
            }
            jg.writeEndObject();
        }
        jg.writeEndArray();
    }
}
