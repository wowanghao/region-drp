package io.terminus.common.data.transfer.demo.exporter.samples;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * date 2018/9/27
 *
 * @author yushuo
 */
@Data
@Builder
public class TestPojo {

    private Integer id;

    @JsonProperty("aliasName")
    private String sampleString;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Date createTime = null;

}
