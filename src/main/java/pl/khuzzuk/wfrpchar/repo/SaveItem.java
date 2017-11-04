package pl.khuzzuk.wfrpchar.repo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveItem<T> {
    private Class<?> type;
    private T entity;
}
