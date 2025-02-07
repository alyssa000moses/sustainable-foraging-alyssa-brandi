package learn.foraging.data;

import learn.foraging.models.Category;
import learn.foraging.models.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static learn.foraging.TestData.*;

public class ItemRepositoryDouble implements ItemRepository {
//    private List<TestData> data = new ArrayList<>();

//    public FakeRepository() {
//        data.add(new TestData(1, "Alice"));
//        data.add(new TestData(2, "Bob"));
//        data.add(new TestData(3, "Charlie"));
//    }
//
//    public List<TestData> findAll() {
//        return data;
//    }


    @Override
    public List<Item> findByCategory(Category category) {
        return null;
    }

    @Override
    public Item findById(int id) {
        if (id == 1) {
            return EDIBLE;
        }
        return null;
    }

    @Override
    public Item add(Item item) {
        return EDIBLE;
    }

    @Override
    public Optional<Item> findByName(String name) {
        if (name.equals("EDIBLE")) {
            return Optional.of(EDIBLE);
        }
        return Optional.empty();
    }
}
