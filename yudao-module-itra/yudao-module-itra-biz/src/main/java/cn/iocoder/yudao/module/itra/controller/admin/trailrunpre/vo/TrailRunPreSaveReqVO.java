package cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 赛前备赛志新增/修改 Request VO")
@Data
public class TrailRunPreSaveReqVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25147")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5507")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "赛事名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "赛事名称不能为空")
    private String name;

    @Schema(description = "跑步日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "跑步日期不能为空")
    private LocalDate runDate;

    @Schema(description = "天气")
    private String weather;

    @Schema(description = "跑前状态", example = "2")
    private String preRunStatus;

    @Schema(description = "跑步类型：'比赛','个人跑山'", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "跑步类型：'比赛','个人跑山'不能为空")
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

}