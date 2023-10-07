package crm_project_02.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = httpReq.getCookies();
        if(cookies != null && cookies.length > 0){
            AuthenHandling authenHandling = new AuthenHandling();
            if(authenHandling.isLoggedIn(cookies)){
                filterChain.doFilter(httpReq,httpResp);
            }else{
                httpResp.sendRedirect(httpReq.getContextPath() + "/login");
            }
        }
        else{
            httpResp.sendRedirect(httpReq.getContextPath() + "/login");
        }
    }
}
