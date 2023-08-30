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

@WebServlet(name = "taskController", urlPatterns = { "/task", "/task-add" })
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();

		if (path.equals("/task-add")) {
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
		} else if (path.equals("/task")) {
			List<Task> listTasks = new ArrayList<>();
			String query = "SELECT j.id, j.name AS job_name, p.name AS project_name, u.fullName AS user_name, j.startDate, j.endDate, s.name AS status \r\n"
					+ "FROM Job j \r\n" + "JOIN Project p ON j.id_project = p.id \r\n"
					+ "JOIN Project_Users pu ON j.id_project = pu.id_project \r\n"
					+ "JOIN Users u ON pu.id_user = u.id \r\n" + "JOIN Job_Status_Users jsu ON j.id = jsu.id_job \r\n"
					+ "JOIN Status s ON jsu.id_status = s.id;";
			Connection connection = MysqlConfig.getConnection();

			try {
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					Task task = new Task();
					Users users = new Users();
					GroupWork groupWork = new GroupWork();
					Status status = new Status();

					task.setId(resultSet.getInt("id"));
					task.setName(resultSet.getString("project_name"));

					groupWork.setName(resultSet.getString("project_name"));
					task.setGroupWork(groupWork);

					users.setFirstName(resultSet.getString("firstName"));
					users.setLastName(resultSet.getString("lastName"));
					task.setUsers(users);

					task.setStartDate(resultSet.getString("startDate"));
					task.setEndDate(resultSet.getString("endDate"));

					status.setName(resultSet.getString("status"));
					task.setStatus(status);

					listTasks.add(task);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			req.setAttribute("listTasks", listTasks);
			req.getRequestDispatcher("task.jsp").forward(req, resp);

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// lấy tham số từ thẻ form truyền qua khi người dùng click vào nút button submit
		String project_name = req.getParameter("project_name");
		String job_name = req.getParameter("job_name");
		String performer_name = req.getParameter("performer_name");
		String start_date = req.getParameter("start_date");
		String end_date = req.getParameter("end_date");
		int id_project = Integer.parseInt(req.getParameter("project"));

		String query = "INSERT INTO Job (project_name, job_name, performer_name, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

		Connection connection = MysqlConfig.getConnection();
		boolean isSuccess = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsedStartDate = sdf.parse(start_date);
			java.util.Date parsedEndDate = sdf.parse(end_date);

			java.sql.Date sqlStartDate = new java.sql.Date(parsedStartDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(parsedEndDate.getTime());

			PreparedStatement statement = connection.prepareStatement(query);
			GroupWork groupWork = new GroupWork();
			
			statement.setString(1, project_name);
			statement.setString(2, job_name);
			statement.setString(3, performer_name);
			statement.setDate(4, sqlStartDate);
			statement.setDate(5, sqlEndDate);

			int count1 = statement.executeUpdate();
			if (count1 > 0) {
				isSuccess = true;
				System.out.println("Thêm thành công");
			} else {
				System.out.println("Thêm thất bại");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Lỗi thêm dữ liệu Task" + e.getLocalizedMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		req.setAttribute("isSuccess", isSuccess);
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);

	}

}
