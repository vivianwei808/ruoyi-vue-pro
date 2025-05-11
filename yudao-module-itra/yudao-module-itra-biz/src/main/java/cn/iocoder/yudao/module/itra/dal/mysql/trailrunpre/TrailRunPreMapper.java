package cn.iocoder.yudao.module.itra.dal.mysql.trailrunpre;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.itra.dal.dataobject.trailrunpre.TrailRunPreDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo.*;

/**
 * 赛前备赛志 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrailRunPreMapper extends BaseMapperX<TrailRunPreDO> {

    default PageResult<TrailRunPreDO> selectPage(TrailRunPrePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrailRunPreDO>()
                .eqIfPresent(TrailRunPreDO::getUserId, reqVO.getUserId())
                .likeIfPresent(TrailRunPreDO::getName, reqVO.getName())
                .betweenIfPresent(TrailRunPreDO::getRunDate, reqVO.getRunDate())
                .eqIfPresent(TrailRunPreDO::getWeather, reqVO.getWeather())
                .eqIfPresent(TrailRunPreDO::getPreRunStatus, reqVO.getPreRunStatus())
                .eqIfPresent(TrailRunPreDO::getRunType, reqVO.getRunType())
                .eqIfPresent(TrailRunPreDO::getTotalDistance, reqVO.getTotalDistance())
                .eqIfPresent(TrailRunPreDO::getRaceType, reqVO.getRaceType())
                .eqIfPresent(TrailRunPreDO::getTotalElevation, reqVO.getTotalElevation())
                .eqIfPresent(TrailRunPreDO::getFinishMinutes, reqVO.getFinishMinutes())
                .eqIfPresent(TrailRunPreDO::getOffMinutes, reqVO.getOffMinutes())
                .eqIfPresent(TrailRunPreDO::getTrackFile, reqVO.getTrackFile())
                .eqIfPresent(TrailRunPreDO::getRacetrackFile, reqVO.getRacetrackFile())
                .eqIfPresent(TrailRunPreDO::getItraScore, reqVO.getItraScore())
                .eqIfPresent(TrailRunPreDO::getRouteFeatures, reqVO.getRouteFeatures())
                .eqIfPresent(TrailRunPreDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TrailRunPreDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrailRunPreDO::getId));
    }

}