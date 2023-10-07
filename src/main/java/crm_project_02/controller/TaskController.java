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

@WebServlet(name = "taskController", urlPatterns = { "/task", "/task-add", "/task-edit" })
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
			List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
			
			try {
				listUser = taskService.getAllUsers();
				listGroupWork = taskService.getAllGroupWorks();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi get all groupwork " + e.getLocalizedMessage());
			}
	
			req.setAttribute("listUser", listUser);
			req.setAttribute("listGroupWork", listGroupWork);
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			
		} else if (path.equals("/task")) {
			List<Task> listTask = taskService.getAllTasks(); 
			req.setAttribute("listTask", listTask);
			req.getRequestDispatcher("task.jsp").forward(req, resp);
		}
		else if (path.equals("/task-edit")) {
//			int id = Integer.parseInt(req.getParameter("id"));
//			 taskService.findById(id);
			
			List<Users> listUser = new ArrayList<Users>();
			List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
			List<Status> listStatus = new ArrayList<Status>();
			try {
				listUser = taskService.getAllUsers();
				listGroupWork = taskService.getAllGroupWorks();
				listStatus = taskService.getAllStatus();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi get all task " + e.getLocalizedMessage());
			}
	
			req.setAttribute("listUser", listUser);
			req.setAttribute("listGroupWork", listGroupWork);
			req.setAttribute("listStatus", listStatus);
			
			req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getServletPath();
		if(path.equals("/task-add")) {
			
			// lấy tham số từ thẻ form truyền qua khi người dùng click vào nút button submit
			int id_project = Integer.parseInt(req.getParameter("id_project"));
			String name = req.getParameter("name");
			int id_user = Integer.parseInt(req.getParameter("id_user"));
			String start_date = req.getParameter("start_date");
			String end_date = req.getParameter("end_date");
			//int id_status = Integer.parseInt(req.getParameter("id_status"));
			//int id = Integer.parseInt(req.getParameter("id"));
			
			boolean isSuccess = taskService.insertTask(id_project, name, id_user, start_date, end_date);
			
			List<Users> listUser = new ArrayList<Users>();
			List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
			
				try {
					listUser = taskService.getAllUsers();
					listGroupWork = taskService.getAllGroupWorks();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			req.setAttribute("listUser", listUser);
			req.setAttribute("listGroupWork", listGroupWork);
			
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
		}
		else if(path.equals("/task-edit")) {
			
			int id_project = Integer.parseInt(req.getParameter("id_project"));
			String name = req.getParameter("name");
			int id_user = Integer.parseInt(req.getParameter("id_user"));
			String start_date = req.getParameter("start_date");
			String end_date = req.getParameter("end_date");
			int id_status = Integer.parseInt(req.getParameter("id_status"));
			int id = Integer.parseInt(req.getParameter("id"));
			
			//Task task = taskService.findById(id);
			boolean isSuccess = taskService.updateTask(id_project, name, id_user, start_date, end_date, id_status, id);
			
			List<Users> listUser = new ArrayList<Users>();
			List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
			List<Status> listStatus = new ArrayList<Status>();
			
				try {
					listUser = taskService.getAllUsers();
					listGroupWork = taskService.getAllGroupWorks();
					listStatus = taskService.getAllStatus();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			req.setAttribute("listUser", listUser);
			req.setAttribute("listGroupWork", listGroupWork);
			req.setAttribute("listStatus", listStatus);
			//req.setAttribute("task", task);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
			//resp.sendRedirect("task.jsp");
		}
		

	}
}
