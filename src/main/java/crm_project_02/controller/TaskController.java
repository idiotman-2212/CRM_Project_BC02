package crm_project_02.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.MysqlConnection;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Role;
import crm_project_02.entity.Status;
import crm_project_02.entity.Task;
import crm_project_02.entity.Users;
import crm_project_02.service.GroupWorkService;
import crm_project_02.service.RoleService;
import crm_project_02.service.TaskService;
import crm_project_02.service.UserService;

@WebServlet(name = "taskController", urlPatterns = { "/task", "/task-add" })
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();
	private TaskService taskService = new TaskService();
	private GroupWorkService groupWorkService = new GroupWorkService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/task-add")) {
			
			List<Users> listUser = new ArrayList<Users>();
			try {
				listUser = getAllUser();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi get all users " + e.getLocalizedMessage());
			}
			
			List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
			try {
				listGroupWork = getAllGroupWork();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi get all groupwork " + e.getLocalizedMessage());
			}
	
			req.setAttribute("listUser", listUser);
			req.setAttribute("listGroupWork", listGroupWork);
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			
		} else if (path.equals("/task")) {
			List<Task> listTask = new ArrayList<Task>();
			String query = "SELECT * FROM Job";
			Connection connection = MysqlConfig.getConnection();
			try {
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					Task task = new Task();
					task.setId(resultSet.getInt("id"));
					task.setName(resultSet.getString("name"));
					
					GroupWork groupWork = new GroupWork();
					groupWork.setName(resultSet.getString("name"));
					task.setGroupWork(groupWork);
					
					Users users = new Users();
					users.setFullName(resultSet.getString("fullName"));
					task.setUsers(users);
					
					task.setStartDate(resultSet.getString("startDate"));
					task.setEndDate(resultSet.getString("endDate"));
					
					Status status = new Status();
					status.setName(resultSet.getString("name"));
					task.setStatus(status);
					
					listTask.add(task);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			req.setAttribute("listTask", listTask);
			req.getRequestDispatcher("task.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// lấy tham số từ thẻ form truyền qua khi người dùng click vào nút button submit
		int id_project = Integer.parseInt(req.getParameter("id_project"));
		String name = req.getParameter("name");
		int id_user = Integer.parseInt(req.getParameter("id_user"));
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		

		String query = "INSERT INTO Job (id_project, name, id_user, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
		Connection connection = MysqlConfig.getConnection();
		boolean isSuccess = false;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id_project);
			statement.setString(2, name);
			statement.setInt(3, id_user);
			statement.setString(4, startDate);
			statement.setString(5, endDate);
			
			int count = statement.executeUpdate();
			if(count > 0) {
				isSuccess = true;
				System.out.println("Thêm thành công");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi thêm dữ liệu Task" + e.getLocalizedMessage());
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		List<Users> listUser = new ArrayList<Users>();
		try {
			listUser = getAllUser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi get all users " + e.getLocalizedMessage());
		}
		
		List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
		try {
			listGroupWork = getAllGroupWork();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi get all groupwork " + e.getLocalizedMessage());
		}

		req.setAttribute("listUser", listUser);
		req.setAttribute("listGroupWork", listGroupWork);
		req.setAttribute("isSuccess", isSuccess);
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);

	}

	private List<Users> getAllUser() throws SQLException{
		String query = "SELECT u.id, u.fullName FROM Users u";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		List<Users> listUsers = new ArrayList<Users>();
		
		while(resultSet.next()) {
			Users users = new Users();
			users.setId(resultSet.getInt("id"));
			users.setFullName(resultSet.getString("fullName"));
			listUsers.add(users);
		}
		return listUsers;
	}
	
	private List<GroupWork> getAllGroupWork() throws SQLException{
		String query = "SELECT p.id, p.name FROM Project p";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
		
		while(resultSet.next()) {
			GroupWork groupWork = new GroupWork();
			groupWork.setId(resultSet.getInt("id"));
			groupWork.setName(resultSet.getString("name"));
			listGroupWork.add(groupWork);
		}
		return listGroupWork;
	}
}
