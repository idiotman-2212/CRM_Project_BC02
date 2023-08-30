package crm_project_02.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {

	public static Connection getConnection() {
		//khai báo driver sử dụng chp jdbc tương ứng cho csdl cần kết nối
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//khai báo driver sẽ mở kết nối tới csdl nào
			return DriverManager.getConnection("jdbc:mysql://localhost:3307/layoutcrm","root", "dienchau45" );
		} catch (Exception e) {
			// lỗi xảy ra sẽ chạy vào đây
			System.out.println("Lỗi kết nối database " + e.getLocalizedMessage());
			return null;
		}
		
		
	}
}
