package pl.khuzzuk.wfrpchar.gui;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Documented
@Qualifier
public @interface MainWindowBean {
}
