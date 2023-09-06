package crm_project_02.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import crm_project_02.payload.response.BaseReponse;
import com.google.gson.Gson;

import crm_project_02.entity.Task;
import crm_project_02.service.TaskService;

@WebServlet(name = "apiTaskController", urlPatterns = {"/api/task", "/api/task/delete"})
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
        }
    }
}
