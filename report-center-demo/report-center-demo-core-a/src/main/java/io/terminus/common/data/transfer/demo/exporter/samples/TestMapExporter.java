package io.terminus.common.data.transfer.demo.exporter.samples;

import io.terminus.common.data.transfer.spi.annotations.DataExporter;
import io.terminus.common.data.transfer.spi.ifaces.IDataExporter;
import io.terminus.common.data.transfer.spi.models.DataExportContext;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;

import java.util.*;

/**
 * date 2018/9/27
 *
 * @author yushuo
 */
@DataExporter(name = "map")
public class TestMapExporter implements IDataExporter {

    @Override
    public Paging<Map> exportData(PagingCriteria criteria, DataExportContext context) {
        if(criteria.getPageNo()*criteria.getPageSize() > 1e4) {
            // 这里返回 empty 之后，后面就不会再调用exportData了，在demo中假设有10000条数据，来模拟数据导出结束
            // 注意在项目里面不要带进去这部分代码
            return Paging.empty();
        }
        List<Map<String, Object>> ret = new ArrayList<>();
        for(int i=0; i<criteria.getPageSize(); ++i) {
            int id = i + criteria.getPageSize() * (criteria.getPageNo() - 1);
            int page = criteria.getPageNo();
            Map<String, Object> testObj = new HashMap<String, Object>() {{
                put("id", id);
                put("page", page);
                put("uuid", UUID.randomUUID().toString());
                put("createTime", new Date());
            }};

            ret.add(testObj);
        }

        //noinspection unchecked
        return new Paging(-1L, ret);
    }

    @Override
    public Map<String, String> headerAlias(){
        return new HashMap<String, String>() {{
            put("id", "ID");
            put("page", "页码");
        }};
    }
}
