package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    public static final String SELECT_FROM_MESSAGE_TABLE_WHERE_ID = "SELECT FROM messageTable WHERE id=?";
    public static final String SELECT_FROM_MESSAGE_TABLE = "SELECT * FROM messageTable";
    public static final String INSERT_INTO_MESSAGE_TABLE_TEXT_TIME_VALUES = "INSERT INTO messageTable (text, time) VALUES (?, ?)";
    public static final String UPDATE_MESSAGE_TABLE_SET_TEXT_TIME_WHERE_ID = "UPDATE messageTable SET text=?, time=? WHERE id=?";
    public static final String DELETE_FROM_MESSAGE_TABLE_WHERE_ID = "DELETE FROM messageTable WHERE id=?";
    private final JdbcTemplate jdbcTemplate;

    public MessageRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Message findById(Long id) {
        return jdbcTemplate.query(
                        SELECT_FROM_MESSAGE_TABLE_WHERE_ID,
                new Object[]{id},
                new int[]{},
                new BeanPropertyRowMapper<>(Message.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query(SELECT_FROM_MESSAGE_TABLE,
                new BeanPropertyRowMapper<>(Message.class));
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update(INSERT_INTO_MESSAGE_TABLE_TEXT_TIME_VALUES,
                entity.getText(),
                entity.getTime());
    }

    @Override
    public void update(Message entity) {
        jdbcTemplate.update(UPDATE_MESSAGE_TABLE_SET_TEXT_TIME_WHERE_ID,
                entity.getText(),
                entity.getTime(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_FROM_MESSAGE_TABLE_WHERE_ID, id);
    }
}
