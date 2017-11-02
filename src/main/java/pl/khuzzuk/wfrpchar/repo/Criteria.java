package pl.khuzzuk.wfrpchar.repo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Criteria {
    private Class<?> type;
    private String name;
}
