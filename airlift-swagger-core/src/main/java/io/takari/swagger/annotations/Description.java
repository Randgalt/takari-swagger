package io.takari.swagger.annotations;

import java.lang.annotation.*;

/**
 * Document a class, method or parameter
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Description {
  /**
   * @return the description of the element
   */
  String value();
}
