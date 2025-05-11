package cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 赛前备赛志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrailRunPrePageReqVO extends PageParam {

    @Schema(description = "用户ID", example = "5507")
    private Long userId;

    @Schema(description = "赛事名称", example = "赵六")
    private String name;

    @Schema(description = "跑步日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] runDate;

    @Schema(description = "天气")
    private String weather;

    @Schema(description = "跑前状态", example = "2")
    private String preRunStatus;

    @Schema(description = "跑步类型：'比赛','个人跑山'", example = "1")
    private String runType;

    @Schema(description = "总距离(km)")
    private BigDecimal totalDistance;

    @Schema(description = "赛事类型：国际、国内、长距离、中短距离", example = "2")
    private String raceType;

    @Schema(description = "总爬升(m)")
    private BigDecimal totalElevation;

    @Schema(description = "个人预计完成时间")
    private Integer finishMinutes;

    @Schema(description = "关门时间")
    private LocalDateTime offMinutes;

    @Schema(description = "轨迹文件URL")
    private String trackFile;

    @Schema(description = "赛道图URL")
    private String racetrackFile;

    @Schema(description = "ITRA积分")
    private Integer itraScore;

    @Schema(description = "总体路线特点")
    private String routeFeatures;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}