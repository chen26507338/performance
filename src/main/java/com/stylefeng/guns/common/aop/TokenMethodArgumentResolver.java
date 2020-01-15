package com.stylefeng.guns.common.aop;

import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.cache.CacheKey;
import com.stylefeng.guns.core.base.Token;
import com.stylefeng.guns.core.base.TokenConfig;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.AESUtil;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Date;

public class TokenMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private Boolean isEncryption;
    private String key;
    private String iv;

    public TokenMethodArgumentResolver() {
        TokenConfig tokenConfig = SpringContextHolder.getBean(TokenConfig.class);
        this.isEncryption = tokenConfig.getEncryption();
        this.key = tokenConfig.getKey();
        this.iv = tokenConfig.getIv();
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Token.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String tokenParam = nativeWebRequest.getParameter("token");
        if (StringUtils.isBlank(tokenParam)) {
            throw new GunsException(1, "token不能为空");
        } else {
            String tempToken = null;
            if (isEncryption) {
                tempToken = tokenParam;
                //todo IOS加密有问题 等后续调试后开放
//                if (CacheKit.get(Cache.TOKEN_USED, tokenParam) != null) {
//                    throw new GunsException(HttpStatus.UNAUTHORIZED.value(), "凭证失效");
//                }

                try {
                    String temp = AESUtil.decrypt(tokenParam, key, iv);
                    if (temp.length() < 32) {
                        throw new GunsException(HttpStatus.UNAUTHORIZED.value(), "凭证无效");
                    }
                    tokenParam = temp.substring(0, 32);
                } catch (Exception e) {
                    throw new GunsException(HttpStatus.UNAUTHORIZED.value(), "凭证无效");
                }

            }

            Token token = CacheKit.get(Cache.USER_TOKEN, tokenParam);
            if (token == null) {
                throw new GunsException(HttpStatus.UNAUTHORIZED.value(), "验证失败");
            }

            if (token.getExpired().before(new Date())) {
                throw new GunsException(HttpStatus.UNAUTHORIZED.value(), "凭证过期");
            }

            IUserService userService = SpringContextHolder.getBean(IUserService.class);
            if (userService.selectIgnorePointById(token.getUserId()).getStatus().equals(IUserService.STATUS_DISABLE)) {
                throw new GunsException(HttpStatus.UNAUTHORIZED.value(), "该用户已被拉黑");
            }

            token.setExpired(DateUtils.addDays(new Date(), 1));
            CacheKit.put(Cache.USER_TOKEN, token.getToken(), token);
            CacheKit.put(Cache.USER_TOKEN, CacheKey.USER_TOKEN + token.getUserId(), token);
            if (StringUtils.isNotBlank(tempToken))
                CacheKit.put(Cache.TOKEN_USED, tempToken, true);
            return token;
        }
    }
}
