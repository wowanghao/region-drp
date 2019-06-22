package io.terminus.common.data.transfer.spi.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface DataExporter {

    String name();

}
