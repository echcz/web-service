package cn.echcz.webservice.adapter.controller;

import cn.echcz.webservice.entity.ActionLog;
import cn.echcz.webservice.usecase.ActionLogUsecase;
import cn.echcz.webservice.util.Constants;
import cn.echcz.webservice.adapter.model.ActionLogQO;
import cn.echcz.webservice.adapter.model.ActionLogVO;
import cn.echcz.webservice.adapter.model.PageVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Setter;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ActionLog", description = "操作日志")
@SecurityRequirements({
        @SecurityRequirement(name = HttpHeaders.AUTHORIZATION),
        @SecurityRequirement(name = Constants.HTTP_HEADER_DATA_PERMISSION),
})
@RestController
@RequestMapping("/action-logs")
@Validated
public class ActionLogController {
    @Setter(onMethod_ = @Autowired)
    private ActionLogUsecase actionLogUsecase;

    @Operation(summary = "查询操作日志")
    @GetMapping()
    public PageVO<ActionLogVO> queryActionLog(@ParameterObject @Validated ActionLogQO qo) {
        PageVO<ActionLog> pageVO = qo.query(actionLogUsecase.querier());
        return pageVO.map(ActionLogVO::fromEntity);
    }
}
