package pl.khuzzuk.wfrpchar.entities.items;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ParserBag<T extends Commodity> {
    @Getter
    private final T baseType;
    @Getter
    private final ResourceType primaryResource;
    @Getter
    private final ResourceType secondaryResource;
}
