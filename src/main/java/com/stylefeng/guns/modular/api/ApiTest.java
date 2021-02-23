package com.stylefeng.guns.modular.api;
import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.base.TokenConfig;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.AESUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Api(value = "客户端接口v1版")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class ApiTest extends BaseController {

    @Autowired
    private TokenConfig tokenConfig;

    @ApiOperation(value = "加密")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "token", name = "token", dataType = "string", paramType = "query")
    })
    @PostMapping("token/encrypt")
    public String tokenEncrypt(String token) {
        return AESUtil.encrypt(token + System.currentTimeMillis(), tokenConfig.getKey(), tokenConfig.getIv());
    }

    @ApiOperation(value = "解密")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "内容", name = "content", dataType = "string", paramType = "query")
    })
    @PostMapping("token/decode")
    public Object tokenDecode(String content) {
        String aesDecode = AESUtil.decrypt(content, tokenConfig.getKey(), tokenConfig.getIv());
        String json = new String(Base64.base64ToByteArray(aesDecode));
        return JSON.parseObject(json, Map.class);
    }

}
