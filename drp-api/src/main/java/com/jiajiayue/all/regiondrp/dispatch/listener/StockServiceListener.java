package com.jiajiayue.all.regiondrp.dispatch.listener;


import com.alibaba.fastjson.JSON;
import com.jiajiayue.all.regiondrp.biz.model.MktStockInit;
import com.jiajiayue.all.regiondrp.biz.service.MktStockWriteService;
import com.jiajiayue.all.regiondrp.common.util.HttpParametersUtils;
import com.jiajiayue.all.regiondrp.dispatch.manager.MQManager;
import io.jjy.platform.common.event.DispatchClientExecutionEvent;
import io.jjy.platform.common.event.DrpEvent;
import io.terminus.common.rocketmq.annotation.ConsumeMode;
import io.terminus.common.rocketmq.annotation.MQConsumer;
import io.terminus.common.rocketmq.annotation.MQSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: wh
 * @create: 2019-05-21 13:58
 **/
@Slf4j
@Component
@MQConsumer(consumerGroup = "drp_schedule")
public class StockServiceListener extends AbstractListener {

    @Value("${constant.stock.stockInitUrl}")
    private String stockInitUrl;

    @Autowired
    private MQManager mqManager;

    @Autowired
    private MktStockWriteService mktStockWriteService;

    @MQSubscribe(topic = "drp_job_event_topic_store", consumeMode = ConsumeMode.ORDERLY)
    @Override
    public void subscribe(DrpEvent drpEvent) {
        Map mapParameter = JSON.parseObject(drpEvent.getDataJson(), Map.class);
        this.cardMotionTo(stockInitUrl, "", mapParameter, drpEvent.getBizType());
    }

    private Map<String, Object> cardMotionTo(String url, String token, Map mapParameter, String source){
        DispatchClientExecutionEvent event;
        Map<String, Object> mapResponseBody = null;
        List<MktStockInit> listSelectData = (List<MktStockInit>) mapParameter.get("data");
        HttpParametersUtils httpParametersUtils = HttpParametersUtils.builder().build();//请求工具类
        HttpParametersUtils.HttpParameters httpParameters = httpParametersUtils.new HttpParameters();//参数
        httpParameters.setRequestUrl(url);
        httpParameters.setAccess_Token(token);
        httpParameters.setMap(mapParameter);

        event = DispatchClientExecutionEvent.builder()
                .executionParams(JSON.toJSONString(httpParameters))
                .executionUrl(url)
                .executionState(200)
                .message("execute success")
                .source(source).build();
        try {
            mapResponseBody = httpParametersUtils.sendHttpRequest(httpParameters);
            Boolean success = (Boolean) mapResponseBody.get("success");
            List<MktStockInit> errorList = (List) mapResponseBody.get("data");
            if (!success) {
                Set<String> ids = errorList.stream().map(MktStockInit::getId).collect(Collectors.toSet());
                List<MktStockInit> result = listSelectData.stream().filter(mktStockInit -> ids.contains(mktStockInit.getId())).collect(Collectors.toList());
                log.info(result.toString());
            } else {
                //mktStockWriteService.batchUpdate(dataSource, listSelectData, StockComtag.SUCCESS);//更新----------------------------
                this.success(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
            event.setExecutionState(500);
            event.setMessage(e.getMessage().replace("\"", "'"));
        }
        return mapResponseBody;
    }

}
