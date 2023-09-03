package crm_project_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Role;
import crm_project_02.entity.Status;
import crm_project_02.entity.Task;
import crm_project_02.entity.Users;

public class TaskRepository {
//	strategy pattern
	public int insert(int project_name, String name, int performer, String start_date, String end_date) {
		int count = 0;
		String query = "INSERT INTO Job (id_project, name, id_user, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
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
			
			statement.setInt(1, project_name);
			statement.setString(2, name);
			statement.setInt(3, performer);
			statement.setDate(4, sqlStartDate);
			statement.setDate(5, sqlEndDate);

			count = statement.executeUpdate();
			if (count > 0) {
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
		
		return count;
	}
	
	public List<Task> getAllTask(){
		List<Task> listTasks = new ArrayList<>();
		String query = "SELECT\r\n"
				+ "    j.name,\r\n"
				+ "    p.name AS project_name,\r\n"
				+ "    u.fullName  AS performer,\r\n"
				+ "    j.startDate AS start_date,\r\n"
				+ "    j.endDate AS end_date\r\n"
				+ "FROM Job j\r\n"
				+ "JOIN Project p ON j.id_project = p.id\r\n"
				+ "JOIN Users u ON j.id_user = u.id;"
				+ "";
		
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				Users users = new Users();
				GroupWork groupWork = new GroupWork();
				
				task.setName(resultSet.getString("name"));

				groupWork.setName(resultSet.getString("project_name"));
				task.setGroupWork(groupWork);

				users.setFullName(resultSet.getString("performer"));
				task.setUsers(users);

				task.setStartDate(resultSet.getString("start_date"));
				task.setEndDate(resultSet.getString("end_date"));


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
		
		return listTasks;
	}
	public int deleteTaskById(String id) {
		int count = 0;
		String query = "DELETE FROM Job j WHERE j.id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, id);
			
			count = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	public int updateTaskById(String id, int id_project, String name, int id_user, String start_date, String end_date, int id_status) {
	    int count = 0;
	    String query = "UPDATE Job SET name = ? , startDate = ?, endDate = ?, id_user = ? , id_project  = ? , id_status  = ? WHERE id = ?";
	    Connection connection = MysqlConfig.getConnection();
	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, name);
	        statement.setString(2, start_date);
	        statement.setString(3, end_date);
	        statement.setInt(4, id_user);
	        statement.setInt(5, id_project);
	        statement.setInt(6, id_status);
	        statement.setString(7, id);
	        
	        count = statement.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Error updating user: " + e.getLocalizedMessage());
	    } finally {
	        try {
	            connection.close();
	        } catch (SQLException e) {
	            System.out.println("Error closing connection: " + e.getLocalizedMessage());
	        }
	    }
	    
	    return count;
	}
	
	public List<Task> findById(String id) {
		List<Task> list = new ArrayList<Task>();
		String query = "SELECT \r\n"
				+ "	j.id,\r\n"
				+ "    j.name,\r\n"
				+ "    p.name AS project_name,\r\n"
				+ "    u.fullName  AS performer,\r\n"
				+ "    j.startDate AS start_date,\r\n"
				+ "    j.endDate AS end_date\r\n"
				+ "FROM Job j\r\n"
				+ "JOIN Project p ON j.id_project = p.id\r\n"
				+ "JOIN Users u ON j.id_user = u.id\r\n"
				+ "where j.id = ?";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				
				Users users = new Users();
				users.setFullName(resultSet.getString("performer"));
				task.setUsers(users);
				
				task.setStartDate(resultSet.getString("startDate"));
				task.setEndDate(resultSet.getString("endDate"));
				
				GroupWork groupWork = new GroupWork();
				groupWork.setName(resultSet.getString("project_name"));
				task.setGroupWork(groupWork);
				
				list.add(task);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
}
