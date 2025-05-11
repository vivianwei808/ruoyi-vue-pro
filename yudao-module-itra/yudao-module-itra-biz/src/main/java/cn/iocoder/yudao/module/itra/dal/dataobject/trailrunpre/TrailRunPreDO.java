package cn.iocoder.yudao.module.itra.dal.dataobject.trailrunpre;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 赛前备赛志 DO
 *
 * @author 芋道源码
 */
@TableName("itra_trail_run_pre")
@KeySequence("itra_trail_run_pre_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrailRunPreDO extends BaseDO {

    /**
     * 记录ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 赛事名称
     */
    private String name;
    /**
     * 跑步日期
     */
    private LocalDate runDate;
    /**
     * 天气
     * <p>
     * 枚举 { itra_weather 对应的类}
     */
    private String weather;
    /**
     * 跑前状态
     * <p>
     * 枚举 { itra_run_pre_state 对应的类}
     */
    private String preRunStatus;
    /**
     * 跑步类型：'比赛','个人跑山'
     * <p>
     * 枚举 { itra_run_type 对应的类}
     */
    private String runType;
    /**
     * 总距离(km)
     */
    private BigDecimal totalDistance;
    /**
     * 赛事类型：国际、国内、长距离、中短距离
     * <p>
     * 枚举 { itra_race_type 对应的类}
     */
    private String raceType;
    /**
     * 总爬升(m)
     */
    private BigDecimal totalElevation;
    /**
     * 个人预计完成时间
     */
    private Integer finishMinutes;
    /**
     * 关门时间
     */
    private LocalDateTime offMinutes;
    /**
     * 轨迹文件URL
     */
    private String trackFile;
    /**
     * 赛道图URL
     */
    private String racetrackFile;
    /**
     * ITRA积分
     * <p>
     * 枚举 { itra_score 对应的类}
     */
    private Integer itraScore;
    /**
     * 总体路线特点
     */
    private String routeFeatures;
    /**
     * 备注
     */
    private String remark;

}