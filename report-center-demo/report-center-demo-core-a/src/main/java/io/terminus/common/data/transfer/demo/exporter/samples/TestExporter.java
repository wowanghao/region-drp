package io.terminus.common.data.transfer.demo.exporter.samples;

import io.terminus.common.data.transfer.spi.annotations.DataExporter;
import io.terminus.common.data.transfer.spi.ifaces.IDataExporter;
import io.terminus.common.data.transfer.spi.models.DataExportContext;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;
import lombok.var;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * date 2018/9/27
 *
 * @author yushuo
 */
@DataExporter(name = "test")
public class TestExporter implements IDataExporter {

    @Override
    public Paging<TestPojo> exportData(PagingCriteria criteria, DataExportContext context) {
        if(criteria.getPageNo() * criteria.getPageSize() > 1e4) {
            // 这里返回 empty 之后，后面就不会再调用exportData了，在demo中假设有10000条数据，来模拟数据导出结束
            // 注意在项目里面不要带进去这部分代码
            return Paging.empty();
        }
        List<TestPojo> ret = new ArrayList<>();
        for(int i=0; i<criteria.getPageSize(); ++i) {
            var pojo = TestPojo.builder()
                    .id(i + criteria.getPageSize() * (criteria.getPageNo() - 1))
                    .sampleString(UUID.randomUUID().toString())
                    .createTime( i % 2== 0 ? new Date(): null)
                    .build();
            ret.add(pojo);
        }
        return new Paging<>(-1L, ret);
    }

}
