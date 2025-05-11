package cn.iocoder.yudao.module.itra.controller.admin.trailrunpre;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.itra.controller.admin.trailrunpre.vo.*;
import cn.iocoder.yudao.module.itra.dal.dataobject.trailrunpre.TrailRunPreDO;
import cn.iocoder.yudao.module.itra.service.trailrunpre.TrailRunPreService;

@Tag(name = "管理后台 - 赛前备赛志")
@RestController
@RequestMapping("/itra/trail-run-pre")
@Validated
public class TrailRunPreController {

    @Resource
    private TrailRunPreService trailRunPreService;

    @PostMapping("/create")
    @Operation(summary = "创建赛前备赛志")
    @PreAuthorize("@ss.hasPermission('itra:trail-run-pre:create')")
    public CommonResult<Long> createTrailRunPre(@Valid @RequestBody TrailRunPreSaveReqVO createReqVO) {
        return success(trailRunPreService.createTrailRunPre(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新赛前备赛志")
    @PreAuthorize("@ss.hasPermission('itra:trail-run-pre:update')")
    public CommonResult<Boolean> updateTrailRunPre(@Valid @RequestBody TrailRunPreSaveReqVO updateReqVO) {
        trailRunPreService.updateTrailRunPre(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除赛前备赛志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('itra:trail-run-pre:delete')")
    public CommonResult<Boolean> deleteTrailRunPre(@RequestParam("id") Long id) {
        trailRunPreService.deleteTrailRunPre(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得赛前备赛志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('itra:trail-run-pre:query')")
    public CommonResult<TrailRunPreRespVO> getTrailRunPre(@RequestParam("id") Long id) {
        TrailRunPreDO trailRunPre = trailRunPreService.getTrailRunPre(id);
        return success(BeanUtils.toBean(trailRunPre, TrailRunPreRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得赛前备赛志分页")
    @PreAuthorize("@ss.hasPermission('itra:trail-run-pre:query')")
    public CommonResult<PageResult<TrailRunPreRespVO>> getTrailRunPrePage(@Valid TrailRunPrePageReqVO pageReqVO) {
        PageResult<TrailRunPreDO> pageResult = trailRunPreService.getTrailRunPrePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrailRunPreRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出赛前备赛志 Excel")
    @PreAuthorize("@ss.hasPermission('itra:trail-run-pre:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTrailRunPreExcel(@Valid TrailRunPrePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrailRunPreDO> list = trailRunPreService.getTrailRunPrePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "赛前备赛志.xls", "数据", TrailRunPreRespVO.class,
                        BeanUtils.toBean(list, TrailRunPreRespVO.class));
    }

}