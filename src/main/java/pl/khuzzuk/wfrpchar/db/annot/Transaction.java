package pl.khuzzuk.wfrpchar.db.annot;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Transaction {
    boolean close() default false;
}
