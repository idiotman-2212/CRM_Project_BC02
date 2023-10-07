package crm_project_02.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Status;
import crm_project_02.entity.Task;
import crm_project_02.entity.Users;
import crm_project_02.service.GroupWorkService;
import crm_project_02.service.TaskService;
import crm_project_02.service.UserService;

@WebServlet(name = "profileController", urlPatterns = { "/profile", "/profile-edit" })
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();
	private TaskService taskService = new TaskService();
	private GroupWorkService groupWorkService = new GroupWorkService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();

		if (path.equals("/profile")) {
			List<Task> listTask = taskService.getAllTaskForProfile();
			req.setAttribute("listTask", listTask);
			req.getRequestDispatcher("profile.jsp").forward(req, resp);

		} else if (path.equals("/profile-edit")) {
			List<Status> listStatus = new ArrayList<Status>();
			try {
				listStatus = taskService.getAllStatus();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			req.setAttribute("listStatus", listStatus);
			req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// lấy tham số từ thẻ form truyền qua khi người dùng click vào nút button submit
		req.setCharacterEncoding("UTF-8");
		int id_project = Integer.parseInt(req.getParameter("id_project"));
		String name = req.getParameter("name");
		int id_status = Integer.parseInt(req.getParameter("id_status"));
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		boolean isSuccess = taskService.insertTaskForProfile(id_project, name, startDate, endDate, id_status);
		
		List<Status> listStatus = new ArrayList<Status>();
		try {
			listStatus = taskService.getAllStatus();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("listStatus", listStatus);
		req.setAttribute("isSuccess", isSuccess);
		req.getRequestDispatcher("profile.jsp").forward(req, resp);

	}
}