package com.stylefeng.guns.modular.api;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(GunsException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object exceptionHandler(GunsException e) {
        logger.error("业务异常:{}", e.getMessage());
        return new ErrorTip(e.getCode(), e.getMessage());
    }

}
