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
import crm_project_02.entity.Task;
import crm_project_02.entity.Users;

public class TaskRepository {
//	strategy pattern
	public int insert(String project_name, String job_name, String performer_name, String start_date, String end_date) {
		int count = 0;
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
		List<Task> list = new ArrayList<Task>();
		
		String query = "select u.id, u.firstName, u.lastName, u.userName, r.name\n"
				+ "from Users u \n"
				+ "join Role r ON u.id_role = r.id";
		
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.set
				task.setFirstName(resultSet.getString("firstName"));
				task.setLastName(resultSet.getString("lastName"));
				task.setUserName(resultSet.getString("userName"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				
				users.setRole(role);
				
				list.add(users);
			}
			
		}catch (Exception e) {
			System.out.println("Lỗi lấy danh sách user " + e.getLocalizedMessage());
		}finally {
			try {
				connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return list;
	}
	public int deleteById(int id) {
		int count = 0;
		String query = "DELETE FROM Users u WHERE u.id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			count = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	public int updateById(int id, String firstName, String lastName, String userName, int idRole) {
	    int count = 0;
	    String query = "UPDATE Users SET firstName = ?, lastName = ?, userName = ?, id_role = ? WHERE id = ?";
	    Connection connection = MysqlConfig.getConnection();
	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, firstName);
	        statement.setString(2, lastName);
	        statement.setString(3, userName);
	        statement.setInt(4, idRole);
	        statement.setInt(5, id);
	        
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

}
