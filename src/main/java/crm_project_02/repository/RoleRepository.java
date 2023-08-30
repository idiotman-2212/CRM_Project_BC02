package crm_project_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Role;

/**
 * 
 *	RoleRepository : Chứa toàn bộ câu truy vấn liên quan tới bảng ROLE
 */
public class RoleRepository {
	
	public int insert(String name, String desc) {
		
		String query = "INSERT INTO Role(name,description) VALUES(?,?)";
		Connection connection = MysqlConfig.getConnection();
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, desc);
			
			count = statement.executeUpdate();
		}catch (Exception e) {
			System.out.println("Lỗi thêm role " + e.getLocalizedMessage());
		}finally {
			try {
				connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return count;
	}
	
//	Đối với câu select tên hàm sẽ bắt đầu bằng chữ : find
//	nếu có điều kiện where : by
	public List<Role> findAll(){
		List<Role> listRole = new ArrayList<Role>();
		String query = "SELECT * FROM Role";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			//Duyệt qua từng dòng dữ liệu
			while(resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				
				listRole.add(role);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return listRole;
	}
	
	public int deleteRoleById(int id) {
		int count = 0;
		String query = "DELETE FROM Role r WHERE r.id = ?";
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
	public int updateRoleById(int id, String name, String description) {
	    int count = 0;
	    String query = "UPDATE roles SET name = ?, description = ? WHERE id = ? \"";
	    Connection connection = MysqlConfig.getConnection();
	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, name);
	        statement.setString(2, description);
	        statement.setInt(3, id);
	        
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
