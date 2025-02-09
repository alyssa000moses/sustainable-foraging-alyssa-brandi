package learn.foraging.data;

import learn.foraging.models.Forager;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ForagerJdbcClientRepository implements ForagerRepository {

    private final JdbcClient jdbcClient;

    public ForagerJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Forager findById(int forager_id) {

//        public Forager mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Forager forager = new Forager();
//            forager.setId(rs.getInt("forager_id"));
//            forager.setFirstName(rs.getString("first_name"));
//            forager.setLastName(rs.getString("last_name"));
//            forager.setState(rs.getString("state_abbr"));
//            return forager;
//        }

        final String sql = """
            select forager_id, first_name, last_name, state_abbr
            from forager
            where forager_id = ?;
            """;

        return jdbcClient.sql(sql)
                .param(forager_id)
                .query(Forager.class)
                .optional().orElse(null);
    }

    @Override
    public List<Forager> findByLastName(String lastNamePrefix) {
        final String sql = """
                select forager_id, first_name, last_name, state_abbr
                from forager
                where last_name like ?;
                """;
        return jdbcClient.sql(sql)
                .param(lastNamePrefix + "%")
                .query(new ForagerMapper())
                .list();
    }

    @Override
    public List<Forager> findByState(String stateAbbr) {
        final String sql = """
                select forager_id, first_name, last_name, state_abbr
                from forager
                where state_abbr = ?;
                """;
        return jdbcClient.sql(sql)
                .param(stateAbbr)
                .query(new ForagerMapper())
                .list();
    }
}
