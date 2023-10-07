package crm_project_02.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.entity.Users;
import crm_project_02.payload.response.BaseReponse;
import crm_project_02.service.AuthService;


public class AuthenHandling {
    final String COOKIE_NAME = "email";
    public BaseReponse getUserInfo(HttpServletRequest req) {
    	BaseReponse Response = new BaseReponse();
        CookieHandling cookieHandling = new CookieHandling();
        Cookie cookie = cookieHandling.getCookie(req);

        if(cookie != null){
            AuthService authService = new AuthService();
            Users user = authService.getUser(cookie.getValue());
            if(user != null){
                Response.setStatusCode(200);
                Response.setMessage("Lấy thông tin user thành công");
                Response.setData(user);

                return Response;
            } else {
                Response.setMessage("Không tìm thấy user");
            }
        } else {
            Response.setMessage("Không tìm thấy cookie");
        }
        Response.setStatusCode(404);
        Response.setData(null);

        return Response;
    }
    public BaseReponse logOut(HttpServletRequest req, HttpServletResponse resp){
    	BaseReponse Response = new BaseReponse();
        CookieHandling cookieHandling = new CookieHandling();
        Cookie cookie = cookieHandling.getCookie(req);

        if(cookie != null){
            Response.setStatusCode(200);
            cookieHandling.deleteCookie(resp,cookie);
            Response.setMessage("Đăng xuất thành công");
            Response.setData(true);
        } else{
            Response.setStatusCode(400);
            Response.setMessage("Đăng xuất thất bại");
            Response.setData(false);
        }
        return Response;
    }
    public BaseReponse verifyLoginAccount(HttpServletResponse resp, String email, String password){
    	BaseReponse Response = new BaseReponse();
        LoginService loginService = new LoginService();
        if(loginService.checkLogin(email,password)){
            CookieHandling cookieHandling = new CookieHandling();
            cookieHandling.addCookie(resp,email);
            Response.setStatusCode(200);
            Response.setMessage("Đăng nhập thành công");
            Response.setData(true);
        } else{
            Response.setStatusCode(200);
            Response.setMessage("Đăng nhập thất bại");
            Response.setData(false);
        }
        return Response;
    }
    public boolean isLoggedIn(Cookie[] cookies){
        boolean isLoggedin = false;
        for (Cookie ck: cookies) {
            if(COOKIE_NAME.equals(ck.getName()) && !("".equals(ck.getValue()))){
                isLoggedin = true;
                break;
            }
        }
        return isLoggedin;
    }
    public int getRoleOfUser(HttpServletRequest req){
        CookieHandling cookieHandling = new CookieHandling();
        Cookie cookie = cookieHandling.getCookie(req);
        String email = cookie.getValue();
        AuthService authService = new AuthService();
        return authService.getRoleByEmail(email);
    }
}