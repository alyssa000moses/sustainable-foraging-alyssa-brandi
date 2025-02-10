package learn.foraging.data;

import learn.foraging.DataHelper;
import learn.foraging.models.Forager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static learn.foraging.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ForagerJdbcClientRepositoryTest {
    JdbcClient jdbcClient = DataHelper.getJdbcClient();
    ForagerJdbcClientRepository repository = new ForagerJdbcClientRepository(jdbcClient);

    @BeforeEach
    void setup() {
        jdbcClient.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldFindByLastNameLa() {
        List<Forager> expected = List.of(
                FORAGER_ONE,
                FORAGER_TWO
        );
        List<Forager> actual = repository.findByLastName("La");
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByLastNameX() {
        List<Forager> expected = List.of();
        List<Forager> actual = repository.findByLastName("X");
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByStateMN() {
        List<Forager> expected = List.of();
        List<Forager> actual = repository.findByState("MN");
        assertEquals(expected, actual);
    }
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
    @Test
    void add_shouldAddForager() {
        Forager forager = new Forager();
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState("CA");

        Forager result = repository.add(forager);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("CA", result.getState());
    }
>>>>>>> Stashed changes

    @Test
    void addForager() {
        Forager forager = new Forager();
<<<<<<< Updated upstream
=======
        forager.setFirstName("Jane");
        forager.setLastName("Smith");
        forager.setState("NY");

        Forager result = repository.add(forager);

        assertNotNull(result.getId());
=======

    @Test
    void addForager() {
        Forager forager = new Forager();
>>>>>>> Stashed changes
        forager.setFirstName("Test");
        forager.setLastName("Test");
        forager.setState("MN");
        Forager actual = repository.add(forager);
        assertEquals(3, actual.getId());
<<<<<<< Updated upstream
=======
>>>>>>> a152898 (add new forager complete)
>>>>>>> Stashed changes
    }
}