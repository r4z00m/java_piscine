package edu.school21.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmbeddedDataSourceTest {

    public static final String SCHEMA_SQL = "schema.sql";
    public static final String DATA_SQL = "data.sql";

    private DataSource dataSource;

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .addScript(SCHEMA_SQL)
                .addScript(DATA_SQL)
                .build();
    }

    @Test
    void connectionTest() throws SQLException {
        assertNotNull(dataSource.getConnection());
    }
}
