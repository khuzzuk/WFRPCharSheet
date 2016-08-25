package pl.khuzzuk.wfrpchar.db.annot;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Qualifier
@Documented
public @interface RangedWeapons {
}
