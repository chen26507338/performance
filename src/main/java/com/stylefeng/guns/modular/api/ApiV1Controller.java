package com.stylefeng.guns.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.annotion.NoRepeatSubmit;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.Token;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.payment.model.DlxpryGz;
import com.stylefeng.guns.modular.payment.model.JsAward;
import com.stylefeng.guns.modular.payment.model.PqryGz;
import com.stylefeng.guns.modular.payment.model.ZbryGz;
import com.stylefeng.guns.modular.system.service.IUserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.Year;

/**
 * 接口V1版
 */
@RestController
@RequestMapping("/api/v1")
@Api(value = "客户端接口v1版", description = "客户端接口v1版")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class ApiV1Controller {
    private Logger logger = LoggerFactory.getLogger(ApiV1Controller.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IAssessNormPointService assessNormPointService;

    @ExceptionHandler(GunsException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object exceptionHandler(GunsException e) {
        logger.error("业务异常:{}", e.getMessage());
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    @ApiOperation(value = "用户登录", response = Token.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "语言", name = "lang", dataType = "string", paramType = "query")
    })
    @PostMapping("login")
    @NoRepeatSubmit
    public Object login(@RequestParam @ApiParam(value = "用户名", required = true) String account,
                        @RequestParam @ApiParam(value = "密码", required = true) String password) {
        User user = new User();
        user.setAccount(account);
        user.setPwdCheck(password);
        Token token = userService.apiLogin(user);
        return new SuccessTip(token);
    }

    @ApiOperation(value = "用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户凭证", name = "token", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("user/details")
    public Object userDetail(@ApiIgnore Token token) {
        User user = userService.selectIgnorePointById(token.getUserId());
        return new SuccessTip(user);
    }

    @ApiOperation(value = "用户积分")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户凭证", name = "token", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("user/assess/point")
    public Object userAssessPoint(@ApiIgnore Token token) {
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(Long.valueOf(token.getUserId()));
        return new SuccessTip(assessNormPointService.selectOne(new EntityWrapper<>(params)));
    }

    @ApiOperation(value = "在编人员工资")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户凭证", name = "token", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("user/payment/zbry")
    public Object userPaymentZbry(Integer year,Integer month,@ApiIgnore Token token) {
        ZbryGz params = new ZbryGz();
        params.setYear(year);
        params.setMonth(month);
        params.setUserId(Long.valueOf(token.getUserId()));
        return new SuccessTip(params.selectList(new EntityWrapper<>(params)));
    }

    @ApiOperation(value = "派遣人员工资")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户凭证", name = "token", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("user/payment/pqry")
    public Object userPaymentPqry(Integer year,Integer month,@ApiIgnore Token token) {
        PqryGz params = new PqryGz();
        params.setYear(year);
        params.setMonth(month);
        params.setUserId(Long.valueOf(token.getUserId()));
        return new SuccessTip(params.selectList(new EntityWrapper<>(params)));
    }

    @ApiOperation(value = "代理校聘人员工资")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户凭证", name = "token", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("user/payment/dlxp")
    public Object userPaymentDlxp(Integer year,Integer month,@ApiIgnore Token token) {
        DlxpryGz params = new DlxpryGz();
        params.setYear(year);
        params.setMonth(month);
        params.setUserId(Long.valueOf(token.getUserId()));
        return new SuccessTip(params.selectList(new EntityWrapper<>(params)));
    }

    @ApiOperation(value = "薪酬工资")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户凭证", name = "token", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("user/payment/xc")
    public Object userPaymentXc(String year, String type, @ApiIgnore Token token) {
        JsAward params = new JsAward();
        params.setYear(year);
        params.setType(type);
        params.setUserId(Long.valueOf(token.getUserId()));
        return new SuccessTip(params.selectList(new EntityWrapper<>(params)));
    }

}
