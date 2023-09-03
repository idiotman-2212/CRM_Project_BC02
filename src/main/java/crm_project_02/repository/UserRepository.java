package crm_project_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Role;
import crm_project_02.entity.Users;

public class UserRepository {
//	strategy pattern
	public int insert(String fullname, String email, String pwd, String phone, int idRole) {
		int count = 0;
		String query = "INSERT INTO Users(fullname,email,pwd,phone,id_role) \n"
				+ "VALUES(?,?,?,?,?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, fullname);
			statement.setString(2, email);
			statement.setString(3, pwd);
			statement.setString(4, phone);
			statement.setInt(5, idRole);
			
			count = statement.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Lỗi thêm dữ liệu User " + e.getLocalizedMessage());
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		
		return count;
	}
	
	public List<Users> getAllUsers(){
		List<Users> listUser = new ArrayList<Users>();
		
		String query = "select u.id, u.firstName, u.lastName, u.userName, r.name\n"
				+ "from Users u \n"
				+ "join Role r ON u.id_role = r.id";
		
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Users users = new Users();
				users.setId(resultSet.getInt("id"));
				users.setFirstName(resultSet.getString("firstName"));
				users.setLastName(resultSet.getString("lastName"));
				users.setUserName(resultSet.getString("userName"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				
				users.setRole(role);
				
				listUser.add(users);
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
		
		return listUser;
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
	
	 public List<Users> findById(int id) {
		 List<Users> list = new ArrayList<Users>();
		 String query = "select u.id, u.email, u.firstName, u.lastName, u.fullname , u.userName, u.phone, r.name\n"
					+ "from Users u \n"
					+ "join Role r ON u.id_role = r.id where u.id = ?";
		 Connection connection = MysqlConfig.getConnection();
		 PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Users users = new Users();
				users.setId(resultSet.getInt("id"));
				users.setEmail(resultSet.getString("email"));
				users.setFirstName(resultSet.getString("firstName"));
				users.setLastName(resultSet.getString("lastName"));
				users.setFullName(resultSet.getString("fullName"));
				users.setUserName(resultSet.getString("userName"));
				users.setPhone(resultSet.getString("phone"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				
				users.setRole(role);
				list.add(users);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		return list;
	 }
	
	public int updateById(String fullname, String email, String pwd, String phone, int idRole, int id) {
	    int count = 0;
	    String query = "UPDATE Users SET fullName = ?, email = ?, pwd = ?, phone = ?, id_role = ? WHERE id = ?";
	    Connection connection = MysqlConfig.getConnection();
	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, email);
	        statement.setString(2, fullname);
	        statement.setString(3, email);
	        statement.setString(4, pwd);
	        statement.setString(5, phone);
	        statement.setInt(6, idRole);
	        statement.setInt(7, id);
	        
	        count = statement.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Lỗi cập nhật user: " + e.getLocalizedMessage());
	    } finally {
	        try {
	            connection.close();
	        } catch (SQLException e) {
	            System.out.println("Lỗi đóng kết nối: " + e.getLocalizedMessage());
	        }
	    }
	    
	    return count;
	}

}