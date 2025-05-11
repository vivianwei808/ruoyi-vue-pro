package cn.iocoder.yudao.module.itra.service.trailrunpre;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo.*;
import cn.iocoder.yudao.module.itra.dal.dataobject.trailrunpre.TrailRunPreDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 赛前备赛志 Service 接口
 *
 * @author 芋道源码
 */
public interface TrailRunPreService {

    /**
     * 创建赛前备赛志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTrailRunPre(@Valid TrailRunPreSaveReqVO createReqVO);

    /**
     * 更新赛前备赛志
     *
     * @param updateReqVO 更新信息
     */
    void updateTrailRunPre(@Valid TrailRunPreSaveReqVO updateReqVO);

    /**
     * 删除赛前备赛志
     *
     * @param id 编号
     */
    void deleteTrailRunPre(Long id);

    /**
     * 获得赛前备赛志
     *
     * @param id 编号
     * @return 赛前备赛志
     */
    TrailRunPreDO getTrailRunPre(Long id);

    /**
     * 获得赛前备赛志分页
     *
     * @param pageReqVO 分页查询
     * @return 赛前备赛志分页
     */
    PageResult<TrailRunPreDO> getTrailRunPrePage(TrailRunPrePageReqVO pageReqVO);

}