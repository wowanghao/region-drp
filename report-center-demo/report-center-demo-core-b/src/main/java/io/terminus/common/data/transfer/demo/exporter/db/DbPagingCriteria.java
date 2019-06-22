package io.terminus.common.data.transfer.demo.exporter.db;

import io.terminus.common.model.PagingCriteria;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * date 2018/9/27
 *
 * @author yushuo
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DbPagingCriteria extends PagingCriteria {

    private String table;

    private List<String> fields;

}
