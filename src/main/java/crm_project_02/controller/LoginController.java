package crm_project_02.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Users;
import crm_project_02.service.RoleService;
import crm_project_02.service.UserService;
import crm_project_02.util.SessionUtil;

/*
 * package Controller: là nơi chứa toàn bộ file liên quan tới khai báo
 * đường dẫn và xử lí đường dẫn
 * 
 * Tính năng login:
 * Bước 1: Lấy dữ liệu người dùng nhập vào ô email và password
 * Bước 2:Viết một câu query kiếm tra email và password người dùng nhập vào có tồn tạikhông
 * Bước 3: dùng JDBC kết nối tới csdl và truyền câu query ở bước 2 cho csdl thực thi
 * Bước 4: kiểm tra dữ liệu, nếu có dữ liệu thì là đăng nhập thành công và ngược lại là đăng nhập thất bại
 */
@WebServlet(name = "loginController", urlPatterns = { "/login" , "/logout"})

public class LoginController extends HttpServlet {
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	String action = req.getParameter("action");
    	if(action != null && action.equals("login")) {
    		int maxAge = 8 * 60 * 60;
            Cookie cookie = new Cookie("hoten", URLEncoder.encode("Nguyễn Văn A", "UTF-8"));
            cookie.setMaxAge(maxAge);

            // Yêu cầu client cookie
            resp.addCookie(cookie);
    		req.getRequestDispatcher("login.jsp").forward(req, resp);
    	} else if(action != null && action.equals("logout")) {
    		SessionUtil.getInstance().removeValue(req, "Users");
    		resp.sendRedirect(req.getContextPath() + "/index");
    	}
    	else {
    		req.getRequestDispatcher("index.jsp").forward(req, resp);
    	}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action != null && action.equals("login")) {
        	Users users = new Users();
        	users = userService.findEmailPasswordRole(users.getEmail(), users.getPassword(), users.getRole().getId());
        	if(users != null) {
        		SessionUtil.getInstance().putValue(req, "Users", users);
        		
        		if(users.getRole().getName().equals("Member")) {
        			resp.sendRedirect(req.getContextPath()+ "/404");
        		}
        		else if(users.getRole().getName().equals("Admin")) {
        			resp.sendRedirect(req.getContextPath()+ "/index");
        		}
        		else if(users.getRole().getName().equals("Leader")) {
        			resp.sendRedirect(req.getContextPath()+ "/index");
        		}
        	}else {
        		resp.sendRedirect(req.getContextPath()+ "/login?action=login");
        	}
        	
        	
        	
        	// Bước 1
        	req.setCharacterEncoding("UTF-8");
            String email = req.getParameter("email");
            String password = req.getParameter("password"); 
            
            // Bước 2
            String query = "select *\n"
                    + "from Users u\n"
                    + "where u.email = ? and u.pwd = ?";

            Connection conn = MysqlConfig.getConnection();
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                List<Users> listUser = new ArrayList<Users>();

                while (resultSet.next()) {
                    users.setId(resultSet.getInt("id"));
                    users.setEmail(resultSet.getString("email"));
                    listUser.add(users);
                }
                if (listUser.size() > 0) {
                    // Đăng nhập thành công
                    // Lưu thông tin đăng nhập vào session
                    HttpSession session = req.getSession();
                    session.setAttribute("loggedInUser", email);
                    resp.sendRedirect("index");
                } else {
                    // Đăng nhập thất bại
                    req.setAttribute("mess", "Wrong email or password");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
            } catch (Exception e) {
                System.out.println("Lỗi thực thi truy vấn " + e.getLocalizedMessage());
            }
        }
    	
    }
}

