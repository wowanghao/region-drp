package io.terminus.common.data.transfer.manager.server.persistent.model;

import io.terminus.common.data.transfer.api.model.DataTransferTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * date 2018/9/28
 *
 * @author yushuo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTransferTaskModel implements Serializable {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 0-缺省, 1-导出, 2-导入
     */
    private Integer type;

    /**
     * 数据名称-对应程序中的importer/exporter
     */
    private String name;

    /**
     * 任务的展示名称
     */
    private String displayName;

    /**
     * 任务描述
     */
    private String desc;

    /**
     * 创建任务的用户的用户id
     */
    private Long userId;

    /**
     * 创建任务的用户的用户名(用户登录名)
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 执行参数
     */
    private String criteriaJson;

    /**
     * 成功数量
     */
    private Long successCount;

    /**
     * 失败数量
     */
    private Long errorCount;

    /**
     * 导入/导出 的主文件地址
     */
    private String filePath;

    /**
     * 错误记录的文件地址
     */
    private String errorRecordsFilePath;

    /**
     * 文件扩展名
     */
    private String fileExt;

    /**
     * @see DataTransferTaskStatus
     */
    private Integer status;

    /**
     * 出错时的错误信息
     */
    private String message;

    /**
     * 额外信息
     */
    private String extraJson;

    /**
     * 开始时间
     */
    private Date startedAt;

    /**
     * 完成时间
     */
    private Date finishedAt;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


}
