package pl.khuzzuk.wfrpchar.messaging.subscribers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface SubscribingFor {
    String type();
}
