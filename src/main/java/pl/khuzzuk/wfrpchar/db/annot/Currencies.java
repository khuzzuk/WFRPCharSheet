package pl.khuzzuk.wfrpchar.db.annot;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Documented
@Inherited
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface Currencies {
}
