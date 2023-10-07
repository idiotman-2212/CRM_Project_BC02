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
import crm_project_02.payload.response.BaseReponse;
import com.google.gson.Gson;

import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Status;
import crm_project_02.entity.Task;
import crm_project_02.entity.Users;
import crm_project_02.service.TaskService;

@WebServlet(name = "apiTaskController", urlPatterns = {"/api/task", "/api/task/delete", "/api/task/edit"})
public class ApiTaskController extends HttpServlet {

    private TaskService taskService = new TaskService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.equals("/api/task")) {
            List<Task> listTask = taskService.getAllTasks();

            BaseReponse reponse = new BaseReponse();
			reponse.setStatusCode(200);
			reponse.setMessage("");
			reponse.setData(listTask);

            String dataJson = gson.toJson(reponse);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            out.print(dataJson);

            out.flush();
        } else if (path.equals("/api/task/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean isSuccess = taskService.deleteTaskById(id);

            BaseReponse reponse = new BaseReponse();
			reponse.setStatusCode(200);
			reponse.setMessage(isSuccess ? "Xóa thành công" : "Xóa thất bại");
			reponse.setData(isSuccess);

            String json = gson.toJson(reponse);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            out.print(json);
        }else if (path.equals("/api/task/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Task task = taskService.findById(id);

            BaseReponse response = new BaseReponse();

            if (task != null) {
                response.setStatusCode(200);
                response.setMessage("Lấy thông tin công việc thành công");
                response.setData(task);
            } else {
                response.setStatusCode(404);
                response.setMessage("Không tìm thấy công việc với ID " + id);
                response.setData(null);
            }

            String json = gson.toJson(response);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            out.print(json);
        }

    }
    private BaseReponse getAllTask(){
    	BaseReponse Response = new BaseReponse();
        TaskService taskService = new TaskService();
        List<Task> taskList;
            taskList = taskService.getAllTasks();
            
      
        if(taskList.size()>0){
            Response.setStatusCode(200);
            Response.setMessage("Lấy danh sách công việc thành công");
            Response.setData(taskList);
        } else {
            Response.setStatusCode(404);
            Response.setMessage("Lấy danh sách công việc thất bại");
            Response.setData(null);
        }

        return Response;
    }
}
