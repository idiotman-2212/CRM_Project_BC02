package crm_project_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

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
	
	 public Users findById(int id) {
		
		 //List<Users> listUser = new ArrayList<Users>();
		 String query = "select u.email, u.password, u.fullname, u.phone, r.name\n"
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
				users.setEmail(resultSet.getString("email"));
				users.setFullName(resultSet.getString("fullname"));
				users.setPhone(resultSet.getString("phone"));
				users.setPassword(resultSet.getString("password"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				users.setRole(role);
				
				return users;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		return null;
	 }
	
	public int updateById(String fullName, String email, String password, String phone, int idRole, int id) {
	    int count = 0;
	    String query = "UPDATE Users SET fullName = ?, email = ?, pwd = ?, phone = ?, id_role = ? WHERE id = ?";
	    Connection connection = MysqlConfig.getConnection();
	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, fullName);
	        statement.setString(2, email);
	        statement.setString(3, password);
	        statement.setString(4, phone);
	        statement.setInt(5, idRole);
	        statement.setInt(6, id);
	        
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
	public Users getUserByEmail(String email){
        Users users = new Users();

        Connection connection = MysqlConfig.getConnection();
        String query = "SELECT * FROM Users u WHERE u.email = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
				users.setEmail(resultSet.getString("email"));
				users.setFullName(resultSet.getString("fullname"));
				users.setPhone(resultSet.getString("phone"));
				users.setPassword(resultSet.getString("password"));
				users.setId(resultSet.getInt("id"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				users.setRole(role);
				
				return users;
            }
        }catch (Exception e){
            System.out.println("An error occurred when get user info in database | "+e.getMessage());
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("An error occurred when close database | "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
	public int getRoleByEmail(String email){
        int roleId = 0;

        Connection connection = MysqlConfig.getConnection();
        String query = "SELECT u.id_role FROM Users u WHERE u.email = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                roleId = resultSet.getInt("email");
            }
        }catch (Exception e){
            System.out.println("An error occurred when get role by email info in database | "+e.getMessage());
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("An error occurred when close database | "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return roleId;
    }
	public int countUserByEmailNPassword(String email, String password){
        int count = 0;
        Connection connection = MysqlConfig.getConnection();
        String query = "SELECT count(*) as count FROM Users u WHERE u.email = ? and u.pwd = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                count = resultSet.getInt("count");
            }
        }catch (Exception e){
            System.out.println("An error occurred when count user in database | "+e.getMessage());
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("An error occurred when close database | "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return count;
    }
	
	public Users findEmailPasswordRole(String email, String password, int idRole) {
		
		 //List<Users> listUser = new ArrayList<Users>();
		 String query = "SELECT *  FROM Users u Where u.email = ? and u.pwd = ? and u.id_role = ? ";
		 Connection connection = MysqlConfig.getConnection();
		 PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.setInt(3, idRole);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Users users = new Users();
				users.setEmail(resultSet.getString("email"));
				
				users.setPassword(resultSet.getString("password"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				users.setRole(role);
				
				return users;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		return null;
	 }
}