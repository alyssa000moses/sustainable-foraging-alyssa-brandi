package learn.foraging.data;

import learn.foraging.models.Category;
import learn.foraging.models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<Item> findByCategory(Category category);

    Item findById(int id);

    Item add(Item item);

    Optional<Item> findByName(String name);
}
