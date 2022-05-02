package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    public static final String FIND_ALL = "SELECT * FROM productTable";
    public static final String FIND_BY_ID = "SELECT * FROM productTable WHERE id=";
    public static final String UPDATE_NAME = "UPDATE productTable SET name=? WHERE id=?";
    public static final String UPDATE_PRICE = "UPDATE productTable SET price=? WHERE id=?";
    public static final String SAVE = "INSERT INTO PRODUCTTABLE (NAME, PRICE) VALUES (?, ?)";
    public static final String DELETE = "DELETE FROM PRODUCTTABLE WHERE ID=?";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";

    public static final String SCHEMA = "schema.sql";
    public static final String DATA = "data.sql";

    private Connection connection;

    public ProductsRepositoryJdbcImpl() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .addScript(SCHEMA)
                .addScript(DATA)
                .build();
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();

        try {
            ResultSet set = connection
                    .createStatement()
                    .executeQuery(FIND_ALL);

            while (set.next()) {
                Product product = new Product(
                        set.getLong(ID),
                        set.getString(NAME),
                        set.getInt(PRICE));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            ResultSet set = connection
                    .createStatement()
                    .executeQuery(FIND_BY_ID + id);

            set.next();

            Product product = new Product(
                    set.getLong(ID),
                    set.getString(NAME),
                    set.getInt(PRICE));

            return Optional.of(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection
                    .prepareStatement(UPDATE_NAME);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement(UPDATE_PRICE);
            preparedStatement.setInt(1, product.getPrice());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
