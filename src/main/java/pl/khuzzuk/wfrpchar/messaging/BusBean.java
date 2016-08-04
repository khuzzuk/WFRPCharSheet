package pl.khuzzuk.wfrpchar.messaging;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Documented
@Qualifier
public @interface BusBean {
}
