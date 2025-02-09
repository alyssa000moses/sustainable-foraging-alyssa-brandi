package learn.foraging.domain;

import learn.foraging.data.ForageRepositoryDouble;
import learn.foraging.data.ForagerRepositoryDouble;
import learn.foraging.data.ItemRepositoryDouble;
import learn.foraging.models.Forage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static learn.foraging.TestData.*;
import static learn.foraging.TestHelper.makeResult;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ForageServiceTest {

    ForageService service = new ForageService(
            new ForageRepositoryDouble(),
            new ForagerRepositoryDouble(),
            new ItemRepositoryDouble());

    @Test
    void shouldAdd() {
        Forage arg = new Forage(0, JAN_01_2023, FORAGER_ONE, EDIBLE, ONE_KILO);
        Result<Forage> expected = makeResult(null, new Forage(5, JAN_01_2023, FORAGER_ONE, EDIBLE, ONE_KILO));
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullForage() {
        Result<Forage> expected = makeResult("Nothing to save.", null);
        Result<Forage> actual = service.add(null);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullDate() {
        Forage arg = new Forage(0, null, FORAGER_ONE, EDIBLE, ONE_KILO);
        Result<Forage> expected = makeResult("Forage date is required.", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullForager() {
        Forage arg = new Forage(0, JAN_01_2023, null, EDIBLE, ONE_KILO);
        Result<Forage> expected = makeResult("Forager is required.", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullItem() {
        Forage arg = new Forage(0, JAN_01_2023, FORAGER_ONE, null, ONE_KILO);
        Result<Forage> expected = makeResult("Item is required.", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullKilograms() {
        Forage arg = new Forage(0, JAN_01_2023, FORAGER_ONE, EDIBLE, null);
        Result<Forage> expected = makeResult("Kilograms are required.", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddFutureDate() {
        Forage arg = new Forage(0, LocalDate.now().plusDays(35), FORAGER_ONE, EDIBLE, ONE_KILO);
        Result<Forage> expected = makeResult("Forage date cannot be in the future.", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddKilosLessThanEqualToZero() {
        Forage arg = new Forage(0, JAN_01_2023, FORAGER_ONE, EDIBLE, BigDecimal.ZERO);
        Result<Forage> expected = makeResult("Kilograms must be a positive number less than 250.0", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddKilosTooLarge() {
        Forage arg = new Forage(0, JAN_01_2023, FORAGER_ONE, EDIBLE, new BigDecimal("250.1"));
        Result<Forage> expected = makeResult("Kilograms must be a positive number less than 250.0", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }


    @Test
    void shouldNotAddWhenItemNotFound() {
        Forage arg = new Forage(0, JAN_01_2023, FORAGER_ONE, POISONOUS, ONE_KILO);
        Result<Forage> expected = makeResult("Item does not exist.", null);
        Result<Forage> actual = service.add(arg);
        assertEquals(expected, actual);
    }

    @Test
void reportOfItemAndValueForGivenDay_shouldReturnCorrectValues() {
    LocalDate date = JAN_01_2023;
    Map<String, BigDecimal> report = service.reportOfItemAndValueForGivenDay(date);

    assertEquals(new BigDecimal("10.00"), report.get("EDIBLE"));
    // Add more assertions if needed based on the test data
}

@Test
void reportOfItemAndValueForGivenDay_shouldHandleEmptyList() {
    LocalDate date = LocalDate.of(2025, 1, 1); // Assuming no forages on this date
    Map<String, BigDecimal> report = service.reportOfItemAndValueForGivenDay(date);

    assertEquals(0, report.size());
}

@Test
void reportOfItemAndValueForGivenDay_shouldHandleNullValues() {
    LocalDate date = JAN_01_2023; // Assuming some forages with null values on this date
    Map<String, BigDecimal> report = service.reportOfItemAndValueForGivenDay(date);

    assertEquals(new BigDecimal("10.00"), report.get("EDIBLE"));
    // Add more assertions if needed based on the test data
}

    @Test
    void reportOfItemAndKgForGivenDay_shouldReturnCorrectValues() {
        LocalDate date = JAN_01_2023;
        Map<String, BigDecimal> report = service.reportOfItemAndKgForGivenDay(date);

        assertEquals(new BigDecimal("2.00"), report.get("EDIBLE"));
        // Add more assertions if needed based on the test data
    }

    @Test
    void reportOfItemAndKgForGivenDay_shouldHandleEmptyList() {
        LocalDate date = LocalDate.of(2025, 1, 1); // Assuming no forages on this date
        Map<String, BigDecimal> report = service.reportOfItemAndKgForGivenDay(date);

        assertEquals(0, report.size());
    }

}