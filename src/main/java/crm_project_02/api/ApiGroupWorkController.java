package crm_project_02.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Users;
import crm_project_02.payload.response.BaseReponse;
import crm_project_02.service.GroupWorkService;
import crm_project_02.service.UserService;

/**
 * 
 * 
 * payload :
 *   - response : Chứa các class liên quan tới format json trả ra cho client
 *   - request : Chứac các class liên quan tới format json request (tham số) client truyền lên server
 * 
 */

@WebServlet(name = "apiGroupWorkController", urlPatterns = {"/api/groupwork","/api/groupwork/delete"})
public class ApiGroupWorkController extends HttpServlet {

	private GroupWorkService groupWorkService = new GroupWorkService();
//	Khởi tạo thư viện GSON để sử dụng
	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
//		Kiểm tra người dùng đang gọi đường dẫn nào
		if(path.equals("/api/groupwork")) {
			List<GroupWork> listGroupWork = null;
			try {
				listGroupWork = groupWorkService.getAllGroupWork();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BaseReponse reponse = new BaseReponse();
			reponse.setStatusCode(200);
			reponse.setMessage("");
			reponse.setData(listGroupWork);
			
//			Chuyển List hoặc mảng về JSON
			String dataJson = gson.toJson(reponse);
			
//			Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			
			out.print(dataJson);
			
			out.flush();
		}else if(path.equals("/api/groupwork/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = groupWorkService.deleteGroupWork(id);
			
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
