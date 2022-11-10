package hcux.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 注解，实现dto字段自动大写
 */
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface AutoUpper {
}
