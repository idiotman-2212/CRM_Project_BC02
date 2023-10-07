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
	public int insert(int id_project, String name, int id_user, String startDate, String endDate) {
	    int count = 0;
	    String query = "INSERT INTO Job (id_project, name, id_user, startDate, endDate, id_status) VALUES (?, ?, ?, ?, ?)";
	    Connection connection = MysqlConfig.getConnection();
	    boolean isSuccess = false;
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        // Kiểm tra định dạng ngày tháng năm
	        sdf.setLenient(false);
	        java.util.Date parsedStartDate = sdf.parse(startDate);
	        java.util.Date parsedEndDate = sdf.parse(endDate);

	        java.sql.Date sqlStartDate = new java.sql.Date(parsedStartDate.getTime());
	        java.sql.Date sqlEndDate = new java.sql.Date(parsedEndDate.getTime());

	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, id_project);
	        statement.setString(2, name);
	        statement.setInt(3, id_user);
	        statement.setDate(4, sqlStartDate);
	        statement.setDate(5, sqlEndDate);
	        //statement.setInt(6, id_status);

	        count = statement.executeUpdate();
	        if (count > 0) {
	            isSuccess = true;
	            System.out.println("Thêm thành công");
	        }
	    } catch (SQLException pe) {
	        pe.printStackTrace();
	        System.out.println("Lỗi định dạng ngày tháng, vui lòng nhập theo định dạng dd/MM/yyyy.");
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

	
	public int insertTaskForProfile(int id_project, String name, String startDate, String endDate, int id_status) {
		int count = 0;
		String query = "INSERT INTO Job (id_project, name, startDate, endDate, id_status) VALUES (?, ?, ?, ?, ?)";
		Connection connection = MysqlConfig.getConnection();
		boolean isSuccess = false;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id_project);
			statement.setString(2, name);
			statement.setString(3, startDate);
			statement.setString(4, endDate);
			statement.setInt(5, id_status);

			count = statement.executeUpdate();
			if (count > 0) {
				isSuccess = true;
				System.out.println("Thêm thành công");
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
	    List<Task> listTask = new ArrayList<>();
	    String query = "SELECT j.id, j.name AS job_name, p.name AS project_name, u.fullName AS user_name, j.startDate, j.endDate, s.name AS status " +
	                   "FROM Job j " +
	                   "JOIN Project p ON j.id_project = p.id " +
	                   "JOIN Users u ON j.id_user = u.id " +
	                   "JOIN Status s ON j.id_status = s.id";
	    
	    Connection connection = MysqlConfig.getConnection();

	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            Task task = new Task();
	            Users users = new Users();
	            GroupWork groupWork = new GroupWork();
	            Status status = new Status();
	            
	            task.setId(resultSet.getInt("id"));
	            task.setName(resultSet.getString("job_name"));
	            
	            groupWork.setName(resultSet.getString("project_name"));
	            task.setGroupWork(groupWork);
	            
	            users.setFullName(resultSet.getString("user_name"));
	            task.setUsers(users);

	            task.setStartDate(resultSet.getString("startDate"));
	            task.setEndDate(resultSet.getString("endDate"));
	            
	            status.setName(resultSet.getString("status"));
	            task.setStatus(status);

	            listTask.add(task);
	        }
	    } catch (SQLException e) {
	        System.out.println("Lỗi lấy danh sách task " + e.getLocalizedMessage());
	    } finally {
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (Exception e2) {
	            // Xử lý lỗi đóng kết nối
	        }
	    }
	    
	    return listTask;
	}

	public List<Task> getAllTaskForProfile(){
	    List<Task> listTask = new ArrayList<>();
	    String query = "SELECT j.id, j.name AS job_name, p.name AS project_name, j.startDate, j.endDate, s.name AS status " +
	                   "FROM Job j " +
	                   "JOIN Project p ON j.id_project = p.id " +
	                   "JOIN Status s ON j.id_status = s.id";
	    
	    Connection connection = MysqlConfig.getConnection();

	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            Task task = new Task();
	            Users users = new Users();
	            GroupWork groupWork = new GroupWork();
	            Status status = new Status();
	            
	            task.setId(resultSet.getInt("id"));
	            task.setName(resultSet.getString("job_name"));
	            
	            groupWork.setName(resultSet.getString("project_name"));
	            task.setGroupWork(groupWork);
	            
	            task.setStartDate(resultSet.getString("startDate"));
	            task.setEndDate(resultSet.getString("endDate"));
	            
	            status.setName(resultSet.getString("status"));
	            task.setStatus(status);

	            listTask.add(task);
	        }
	    } catch (SQLException e) {
	        System.out.println("Lỗi lấy danh sách task " + e.getLocalizedMessage());
	    } finally {
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (Exception e2) {
	            // Xử lý lỗi đóng kết nối
	        }
	    }
	    
	    return listTask;
	}
	
	public int deleteTaskById(int id) {
		int count = 0;
		String query = "DELETE FROM Job j WHERE j.id = ?";
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
	public int updateTaskById(String name, String start_date, String end_date, int id_project, int id_user, int id_status, int id) {
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
	        statement.setInt(7, id);
	        
//	        statement.setString(1,task.getName());
//            statement.setString(2,task.getStartDate());
//            statement.setString(3,task.getEndDate());
//            statement.setInt(4,task.getUsers().getId());
//            statement.setInt(5,task.getGroupWork().getId());
//            statement.setInt(6,task.getStatus().getId());
//            statement.setInt(7,task.getId());
//	        
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
	
	public Task findById(int id) {
		Task task = new Task();
		String query = "SELECT * FROM Job j WHERE j.id = ?";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
			
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				
				Users users = new Users();
				users.setFullName(resultSet.getString("fullname"));
				task.setUsers(users);
				
				task.setStartDate(resultSet.getString("startDate"));
				task.setEndDate(resultSet.getString("endDate"));
				
				GroupWork groupWork = new GroupWork();
				groupWork.setName(resultSet.getString("name"));
				task.setGroupWork(groupWork);
				
				Status status = new Status();
				status.setName(resultSet.getString("name"));
				task.setStatus(status);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return task;
		
	}
	
	public List<Users> getAllUsers() throws SQLException{
		String query = "SELECT u.id, u.fullName FROM Users u";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		List<Users> listUsers = new ArrayList<Users>();
		
		while(resultSet.next()) {
			Users users = new Users();
			users.setId(resultSet.getInt("id"));
			users.setFullName(resultSet.getString("fullName"));
			listUsers.add(users);
		}
		return listUsers;
	}

	public List<GroupWork> getAllGroupWork() throws SQLException{
		String query = "SELECT p.id, p.name FROM Project p";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		List<GroupWork> listGroupWork = new ArrayList<GroupWork>();
		
		while(resultSet.next()) {
			GroupWork groupWork = new GroupWork();
			groupWork.setId(resultSet.getInt("id"));
			groupWork.setName(resultSet.getString("name"));
			listGroupWork.add(groupWork);
		}
		return listGroupWork;
	}
	
	public List<Status> getAllStatus() throws SQLException{
		List<Status> listStatus = new ArrayList<Status>();
		String query = "SELECT s.id, s.name FROM Status s";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			Status status = new Status();
			status.setId(resultSet.getInt("id"));
			status.setName(resultSet.getString("name"));
			listStatus.add(status);
		}
		return listStatus;
	}
}


