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
import crm_project_02.service.RoleService;

@WebServlet(name = "roleController", urlPatterns = { "/role-add", "/role-table" })
public class RoleController extends HttpServlet {

	private RoleService roleService = new RoleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Lấy ra đường dẫn mà người dùng đang gọi
		String path = req.getServletPath();

		// Kiểm tra đường dẫn người dùng đang gọi là đường dẫn nào để hiển thị giao diện
		// tương ứng
		if (path.equals("/role-add")) {
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		} else if (path.equals("/role-table")) {
			List<Role> listRoles = new ArrayList<Role>();
			String query = "select * from Role";
			Connection connection = MysqlConfig.getConnection();
			try {
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					Role role = new Role();
					role.setId(resultSet.getInt("id"));
					role.setName(resultSet.getString("name"));
					role.setDescription(resultSet.getString("description"));

					listRoles.add(role);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			req.setAttribute("listRoles", listRoles);
			req.getRequestDispatcher("role-table.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String roleName = req.getParameter("role-name");
		String desc = req.getParameter("desc");
		boolean isSuccess = roleService.addRole(roleName, desc);
		
		req.setAttribute("isSuccess", isSuccess);
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		
		
		
		
		
		
		
//		// TODO Auto-generated method stub
//		// Nhận tham số nếu có
//		String roleName = req.getParameter("role-name");
//		String desc = req.getParameter("desc");
//		// Chuẩn bị câu query
//		String query = "INSERT INTO Role(name,description) VALUES(?,?)";
//
//		// Mở kết nối tới CSDL
//		Connection connection = MysqlConfig.getConection();
//		boolean isSuccess = false;
//
//		try {
//			// Truyền câu query vào kết nối vừa được mở
//			PreparedStatement statement = connection.prepareStatement(query);
//			statement.setString(1, roleName);
//			statement.setString(2, desc);
//
//			// Thực thi câu query
//			int count = statement.executeUpdate();
//			if (count > 0) {
//				isSuccess = true;
//				System.out.println("Thêm thành công");
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}
//
//		req.setAttribute("isSuccess", isSuccess);
//		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}

}
