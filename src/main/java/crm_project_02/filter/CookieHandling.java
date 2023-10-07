package crm_project_02.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHandling {
    final String COOKIE_NAME = "email";
    public void addCookie(HttpServletResponse resp, String email){
        Cookie cookie = new Cookie(COOKIE_NAME,email);
        cookie.setMaxAge(60*60*8);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
    public void deleteCookie(HttpServletResponse resp, Cookie cookie){
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
    public Cookie getCookie(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie ck: cookies){
                if(COOKIE_NAME.equals(ck.getName())){
                    return ck;
                }
            }
        }
        return null;
    }
}