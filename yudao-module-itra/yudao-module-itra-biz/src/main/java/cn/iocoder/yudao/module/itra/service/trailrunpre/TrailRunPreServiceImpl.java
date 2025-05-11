package cn.iocoder.yudao.module.itra.service.trailrunpre;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo.TrailRunPrePageReqVO;
import cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo.TrailRunPreSaveReqVO;
import cn.iocoder.yudao.module.itra.dal.dataobject.trailrunpre.TrailRunPreDO;
import cn.iocoder.yudao.module.itra.dal.mysql.trailrunpre.TrailRunPreMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.itra.enums.ErrorCodeConstants.TRAIL_RUN_PRE_NOT_EXISTS;

/**
 * 赛前备赛志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrailRunPreServiceImpl implements TrailRunPreService {

    @Resource
    private TrailRunPreMapper trailRunPreMapper;

    @Override
    public Long createTrailRunPre(TrailRunPreSaveReqVO createReqVO) {
        // 插入
        TrailRunPreDO trailRunPre = BeanUtils.toBean(createReqVO, TrailRunPreDO.class);
        trailRunPreMapper.insert(trailRunPre);
        // 返回
        return trailRunPre.getId();
    }

    @Override
    public void updateTrailRunPre(TrailRunPreSaveReqVO updateReqVO) {
        // 校验存在
        validateTrailRunPreExists(updateReqVO.getId());
        // 更新
        TrailRunPreDO updateObj = BeanUtils.toBean(updateReqVO, TrailRunPreDO.class);
        trailRunPreMapper.updateById(updateObj);
    }

    @Override
    public void deleteTrailRunPre(Long id) {
        // 校验存在
        validateTrailRunPreExists(id);
        // 删除
        trailRunPreMapper.deleteById(id);
    }

    private void validateTrailRunPreExists(Long id) {
        if (trailRunPreMapper.selectById(id) == null) {
            throw exception(TRAIL_RUN_PRE_NOT_EXISTS);
        }
    }

    @Override
    public TrailRunPreDO getTrailRunPre(Long id) {
        return trailRunPreMapper.selectById(id);
    }

    @Override
    public PageResult<TrailRunPreDO> getTrailRunPrePage(TrailRunPrePageReqVO pageReqVO) {
        return trailRunPreMapper.selectPage(pageReqVO);
    }

}