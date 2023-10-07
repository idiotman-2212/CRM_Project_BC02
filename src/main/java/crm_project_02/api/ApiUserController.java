package crm_project_02.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;

import com.google.gson.Gson;

import crm_project_02.entity.Role;
import crm_project_02.entity.Users;
import crm_project_02.payload.response.BaseReponse;
import crm_project_02.service.RoleService;
import crm_project_02.service.TaskService;
import crm_project_02.service.UserService;

/**
 * 
 * 
 * payload : - response : Chứa các class liên quan tới format json trả ra cho
 * client - request : Chứac các class liên quan tới format json request (tham
 * số) client truyền lên server
 * 
 */

@WebServlet(name = "apiUserController", urlPatterns = { "/api/user", "/api/user/delete", "/api/user/update" })
public class ApiUserController extends HttpServlet {

	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	private TaskService taskService = new TaskService();
	Users users = new Users();
//	Khởi tạo thư viện GSON để sử dụng
	private Gson gson = new Gson();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
//		Kiểm tra người dùng đang gọi đường dẫn nào
		if (path.equals("/api/user")) {
			List<Users> listUser = userService.getAllUsers();

			BaseReponse reponse = new BaseReponse();
			reponse.setStatusCode(200);
			reponse.setMessage("");
			reponse.setData(listUser);

//			Chuyển List hoặc mảng về JSON
			String dataJson = gson.toJson(reponse);

//			Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			out.print(dataJson);

			out.flush();
		} else if (path.equals("/api/user/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = userService.deleteUser(id);

			BaseReponse reponse = new BaseReponse();
			reponse.setStatusCode(200);
			reponse.setMessage(isSuccess ? "Xóa thành công" : "Xóa thất bại");
			reponse.setData(isSuccess);

			String json = gson.toJson(reponse);

//			Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			out.print(json);
		} else if (path.equals("/api/user/update")) {
			int id = Integer.parseInt(req.getParameter("id"));
			String fullname = req.getParameter("fullname");
			String pwd = req.getParameter("pwd");
			String phone = req.getParameter("phone");
			String email = req.getParameter("email");
			int idRole = Integer.parseInt(req.getParameter("role"));
			boolean isSuccess = userService.updateUser(fullname, email, email, phone, idRole, id);

			List<Role> listRole = new ArrayList<Role>();
			listRole = roleService.getAllRole();
			req.setAttribute("isSuccess", isSuccess);
			req.setAttribute("listRole", listRole);
			req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
		}

	}

	/**
	 * Viết API /api/user/delete?id=2 Với phương thức GET Sẽ nhận tham số là id của
	 * user Viết câu truy vấn xóa user với id nhận vào
	 */

}