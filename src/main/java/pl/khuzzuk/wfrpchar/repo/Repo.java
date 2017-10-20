package pl.khuzzuk.wfrpchar.repo;

import lombok.Data;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;

import java.util.List;

@Data
public class Repo {
    private List<Item> types;
    private List<Commodity> items;
}
