package pl.khuzzuk.wfrpchar.messaging.publishers;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Qualifier
@Documented
public @interface Publishers {
}
