package crm_project_02.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Role;
import crm_project_02.entity.Users;
import crm_project_02.service.RoleService;
import crm_project_02.service.TaskService;
import crm_project_02.service.UserService;

@WebServlet(name = "userController", urlPatterns = { "/user-table", "/user-add" })
public class UserController extends HttpServlet {

	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	private TaskService taskService = new TaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/user-table":
			List<Users> listUser = userService.getAllUsers();
//			int id = Integer.parseInt(req.getParameter("id"));
//			listUser = userService.findById(id);
//			req.setAttribute("listRole", roleService.getAllRole());
			req.setAttribute("listUser", listUser);
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
			break;

		case "/user-add":
			List<Role> list = new ArrayList<Role>();
			try {
				list = getAllRole();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi get all role " + e.getLocalizedMessage());
			}
			req.setAttribute("listRole", list);
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);

			break;

		default:

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//	    Lấy tham số từ thẻ form truyền qua khi người dùng click button submit
		String fullname = req.getParameter("fullname");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		int idRole = Integer.parseInt(req.getParameter("role"));
		
		
		boolean isSuccess = userService.insertUser(fullname, email, password, phone, idRole);
		//boolean isSuccess1 = userService.findById(id);

		List<Role> list = new ArrayList<Role>();
		list = userService.getAllRole();

		req.setAttribute("listRole", list);
		req.setAttribute("isSuccess", isSuccess);
		//req.setAttribute("isSuccess1", isSuccess1);
		
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}

	private List<Role> getAllRole() throws SQLException {
		// Chuẩn bị câu query
		String query = "SELECT * FROM Role";

		// Mở kết nối CSDL
		Connection connection = MysqlConfig.getConnection();

		// Truyền câu query vào Connection
		PreparedStatement preparedStatement = connection.prepareStatement(query);

		// Thực thi câu truy vấn và được một danh sách dữ liệu
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Role> listRole = new ArrayList<Role>();

		// Duyệt qua từng dòng dữ liệu
		while (resultSet.next()) {
			Role role = new Role();
			role.setId(resultSet.getInt("id"));
			role.setName(resultSet.getString("name"));
			role.setDescription(resultSet.getString("description"));

			listRole.add(role);
		}

		return listRole;
	}

}