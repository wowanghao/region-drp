/*
package com.jiajiayue.all.regiondrp.schedule.job;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.common.collect.Maps;
import com.jiajiayue.all.regiondrp.biz.service.MktStockReadService;
import com.jiajiayue.all.regiondrp.common.util.CountUtil;
import com.jiajiayue.all.regiondrp.constant.BusinessType;
import com.jiajiayue.all.regiondrp.schedule.constant.JobConstants;
import com.jiajiayue.all.regiondrp.schedule.model.JobProcessParam;
import io.jjy.platform.common.event.DrpEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @author wh
 * @date 2019/05/20 10:42
 *//*

@Slf4j
@Component
//@ElasticJobConfig(name = "region-drp-stock-init")
public class RegionDrpStockInitJob extends AbstractSimpleScheduleJob implements SimpleJob {

    private final int pageSize = 200;

    @Autowired
    private MktStockReadService mktStockReadService;



    public RegionDrpStockInitJob() {
    }

    @Override
    protected void execute(JobProcessParam jobProcessParam) throws Exception {
        this.mktStockManageParallel(jobProcessParam);
    }

    public void mktStockManageParallel(JobProcessParam jobProcessParam) throws Exception {
        //String jobName = jobProcessParam.getShardingContext().getJobName();//获得当前job名称
        String dataSource = jobProcessParam.getShardingContext().getShardingParameter();//当前数据源
        List<Integer> pageList = CountUtil.getPageListByTotalCount(mktStockReadService.count(dataSource), pageSize);
        pageList.parallelStream().forEach(currentPage -> {
            List listSelectData = this.fetchPage(currentPage, dataSource);//查询--------------------------------------------
            this.sendMQ(listSelectData, dataSource);//发送mq-----------------------------------------------
            log.info(listSelectData.size() + listSelectData.toString());
        });
    }

    */
/**
     * 获取数据
     *//*

    protected List fetchPage(Integer currentPage, String dataSource) {
        Map<String, Object> criteria = Maps.newHashMap();
        criteria.put("pageSize", pageSize);
        criteria.put("currentPage", currentPage);
        List result = mktStockReadService.listByPage(dataSource, criteria);
        return result;
    }

    */
/**
     * 发送mq
     *//*

    protected void sendMQ(List listSelectData, String dataSource) {
        Map mapMq = new HashMap();
        mapMq.put("data", listSelectData);
        //mapMq.put("dataSource", dataSource);
        //DRP相关的实体类
        DrpEvent drpEvent = new DrpEvent();
        drpEvent.setBizType(BusinessType.STORE.getVal());//获得业务类型名称
        drpEvent.setDataJson(JSON.toJSONString(mapMq));//数据转为json
        drpEvent.setJobInstanceId(JobConstants.JOB_INSTANCE_ID);//获得主机实例id
        this.mqProducer.send(BusinessType.STORE.getTopic(), drpEvent);//发送mq-------------------------------------
    }



}
*/
