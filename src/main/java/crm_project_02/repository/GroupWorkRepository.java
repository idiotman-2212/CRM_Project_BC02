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
import crm_project_02.entity.Users;

public class GroupWorkRepository {
	public int insert(String name, String startDate, String endDate) {
		int count = 0;
		String query = "INSERT INTO Project (name, startDate, endDate) VALUES (?, ?, ?)";

		Connection connection = MysqlConfig.getConnection();
		boolean isSuccess = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsedStartDate = sdf.parse(startDate);
			java.util.Date parsedEndDate = sdf.parse(endDate);

			java.sql.Date sqlStartDate = new java.sql.Date(parsedStartDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(parsedEndDate.getTime());

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setDate(2, sqlStartDate);
			statement.setDate(3, sqlEndDate);

			count = statement.executeUpdate();
			if (count > 0) {
				isSuccess = true;
				System.out.println("Thêm thành công");
			}
		} catch (Exception e) {
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
		return count;
	}
	public List<GroupWork> getAllGroupWork() throws SQLException {
		String query = "SELECT * from Project";
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		List<GroupWork> listGoupWork = new ArrayList<GroupWork>();
		while(resultSet.next()) {
			GroupWork groupWork = new GroupWork();
			groupWork.setId(resultSet.getInt("id"));
			groupWork.setName(resultSet.getString("name"));
			groupWork.setStartDate(resultSet.getString("startDate"));
			groupWork.setEndDate(resultSet.getString("endDate"));
			
			listGoupWork.add(groupWork);
			
		}
		return listGoupWork;
	}
	public int deleteGroupWorkById(int id) {
		int count = 0;
		String query = "DELETE FROM Project p WHERE p.id = ?";
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
	public int updateGroupWorkById(String name, String startDate, String endDate, int id) {
	    int count = 0;
	    String query = "UPDATE Project\r\n"
	    		+ "SET name = ?, startDate = ?, endDate = ?\r\n"
	    		+ "WHERE id = ?;\r\n"
	    		+ "";
	    Connection connection = MysqlConfig.getConnection();
	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, name);
	        statement.setString(2, startDate);
	        statement.setString(3, endDate);
	        statement.setInt(4, id);
	        
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
