package pl.khuzzuk.wfrpchar.db.annot;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface QueryTransaction {
}
