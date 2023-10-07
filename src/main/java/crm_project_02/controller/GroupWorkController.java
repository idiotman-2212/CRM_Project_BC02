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
import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Role;
import crm_project_02.service.GroupWorkService;
import crm_project_02.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "groupWorkController", urlPatterns = { "/groupwork", "/groupwork-add", "/groupwork-edit" })
public class GroupWorkController extends HttpServlet {
	
	private GroupWorkService groupWorkService = new GroupWorkService();
	
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        req.setCharacterEncoding("UTF-8");
        if (path.equals("/groupwork-add")) {
            req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
        } else if (path.equals("/groupwork")) {
            List<GroupWork> listGroupWorks = new ArrayList<>();
            String query = "SELECT * FROM Project";
            Connection connection = MysqlConfig.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    GroupWork groupWork = new GroupWork();
                    groupWork.setId(resultSet.getInt("id"));
                    groupWork.setName(resultSet.getString("name"));
                    groupWork.setStartDate(resultSet.getString("startDate"));
                    groupWork.setEndDate(resultSet.getString("endDate"));

                    listGroupWorks.add(groupWork);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            req.setAttribute("listGroupWorks", listGroupWorks);
            req.getRequestDispatcher("groupwork.jsp").forward(req, resp);

        }
        else if (path.equals("/groupwork-edit")) {
            req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
        String projectName = req.getParameter("project_name");
        String start_date = req.getParameter("start_date");
        String end_date = req.getParameter("end_date");

        boolean isSuccess = groupWorkService.insertGroupWork(projectName, start_date, end_date);
        
        List<GroupWork> list = new ArrayList<GroupWork>();
        try {
			list = groupWorkService.getAllGroupWork();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        req.setAttribute("listGroupWork", list);
        req.setAttribute("isSuccess", isSuccess);
        req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
    }
   
}
