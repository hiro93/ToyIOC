package net.sk32.toy.boot.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TyBootApplication {
    String value() default "";
}
