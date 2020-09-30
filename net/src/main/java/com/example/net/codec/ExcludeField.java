package com.example.net.codec;

import java.lang.annotation.*;

@Target({
        ElementType.FIELD,
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeField {

}
