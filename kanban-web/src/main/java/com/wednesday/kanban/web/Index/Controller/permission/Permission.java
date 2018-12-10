package com.wednesday.kanban.web.Index.Controller.permission;

import com.wednesday.kanban.domain.Perm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
    Perm value() default Perm.ALL;
}
