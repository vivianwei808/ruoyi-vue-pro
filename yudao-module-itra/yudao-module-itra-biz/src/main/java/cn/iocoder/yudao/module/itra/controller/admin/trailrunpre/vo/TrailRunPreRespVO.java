package cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 赛前备赛志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrailRunPreRespVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25147")
    @ExcelProperty("记录ID")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5507")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "赛事名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("赛事名称")
    private String name;

    @Schema(description = "跑步日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("跑步日期")
    private LocalDate runDate;

    @Schema(description = "天气")
    @ExcelProperty(value = "天气", converter = DictConvert.class)
    @DictFormat("itra_weather") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String weather;

    @Schema(description = "跑前状态", example = "2")
    @ExcelProperty(value = "跑前状态", converter = DictConvert.class)
    @DictFormat("itra_run_pre_state") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String preRunStatus;

    @Schema(description = "跑步类型：'比赛','个人跑山'", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "跑步类型：'比赛','个人跑山'", converter = DictConvert.class)
    @DictFormat("itra_run_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String runType;

    @Schema(description = "总距离(km)")
    @ExcelProperty("总距离(km)")
    private BigDecimal totalDistance;

    @Schema(description = "赛事类型：国际、国内、长距离、中短距离", example = "2")
    @ExcelProperty(value = "赛事类型：国际、国内、长距离、中短距离", converter = DictConvert.class)
    @DictFormat("itra_race_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String raceType;

    @Schema(description = "总爬升(m)")
    @ExcelProperty("总爬升(m)")
    private BigDecimal totalElevation;

    @Schema(description = "个人预计完成时间")
    @ExcelProperty("个人预计完成时间")
    private Integer finishMinutes;

    @Schema(description = "关门时间")
    @ExcelProperty("关门时间")
    private LocalDateTime offMinutes;

    @Schema(description = "轨迹文件URL")
    @ExcelProperty("轨迹文件URL")
    private String trackFile;

    @Schema(description = "赛道图URL")
    @ExcelProperty("赛道图URL")
    private String racetrackFile;

    @Schema(description = "ITRA积分")
    @ExcelProperty(value = "ITRA积分", converter = DictConvert.class)
    @DictFormat("itra_score") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer itraScore;

    @Schema(description = "总体路线特点")
    @ExcelProperty("总体路线特点")
    private String routeFeatures;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}