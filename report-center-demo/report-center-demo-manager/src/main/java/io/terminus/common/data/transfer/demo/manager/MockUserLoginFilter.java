package io.terminus.common.data.transfer.demo.manager;

import io.terminus.parana.common.web.context.RequestContext;
import io.terminus.parana.common.web.context.UserMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xieqinghe .
 * @date 2018/11/28 下午12:48
 * @email xieqinghe@terminus.io
 */
@Slf4j
@Component
public class MockUserLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // skip
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            UserMeta userMeta = new UserMeta();
            userMeta.setUserId(8888L);
            userMeta.setUsername("Happy666");
            userMeta.setNickname("尼古拉斯·赵四");

            RequestContext.setUserMeta(userMeta);

            filterChain.doFilter(request, response);
        } finally {
            RequestContext.clearCookie();
            RequestContext.clearHeader();
            RequestContext.clearUser();
        }
    }


    @Override
    public void destroy() {
        // skip
    }

}
