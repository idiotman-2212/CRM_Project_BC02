package crm_project_02.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_project_02.entity.Role;
import crm_project_02.entity.Users;
import crm_project_02.payload.response.BaseReponse;
import crm_project_02.service.RoleService;
import crm_project_02.service.UserService;

/**
 * 
 * 
 * payload :
 *   - response : Chứa các class liên quan tới format json trả ra cho client
 *   - request : Chứac các class liên quan tới format json request (tham số) client truyền lên server
 * 
 */

@WebServlet(name = "apiRoleController", urlPatterns = {"/api/role-table","/api/role-table/delete"})
public class ApiRoleController extends HttpServlet {

	private RoleService roleService = new RoleService();
//	Khởi tạo thư viện GSON để sử dụng
	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
//		Kiểm tra người dùng đang gọi đường dẫn nào
		if(path.equals("/api/role-table")) {
			List<Role> listRole = roleService.getAllRole();
			
			BaseReponse reponse = new BaseReponse();
			reponse.setStatusCode(200);
			reponse.setMessage("");
			reponse.setData(listRole);
			
//			Chuyển List hoặc mảng về JSON
			String dataJson = gson.toJson(reponse);
			
//			Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			
			out.print(dataJson);
			
			out.flush();
		}else if(path.equals("/api/role-table/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = roleService.deleteRole(id);
			
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
		}
		
		
		
	}
	
	/**
	 * Viết API /api/user/delete?id=2
	 * Với phương thức GET
	 * Sẽ nhận tham số là id của user
	 * Viết câu truy vấn xóa user với id nhận vào
	 */
	
}
