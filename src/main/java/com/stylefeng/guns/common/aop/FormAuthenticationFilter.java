package com.stylefeng.guns.common.aop;

import com.stylefeng.guns.core.shiro.ShiroUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    private DefaultWebSessionManager sessionManager;
    private boolean multiAccountLogin;


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        if (!multiAccountLogin) {
            Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
            if (sessions.size() > 0) {
                for (Session session : sessions) {
                    PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                    if (pc != null) {
                        ShiroUser user = (ShiroUser) pc.getPrimaryPrincipal();
                        if (this.getUsername(request).equals(user.getAccount())
                                && !SecurityUtils.getSubject().getSession().getId().equals(session.getId())) {
                            sessionManager.getSessionDAO().delete(session);
                        }
                    }

                }
            }
        }
        return super.onLoginSuccess(token, subject, request, response);
    }
}
