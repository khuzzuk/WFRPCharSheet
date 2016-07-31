package pl.khuzzuk.wfrpchar.db.annot;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface Initializer {
}
