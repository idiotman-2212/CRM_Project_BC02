package crm_project_02.entity;
//Entity : Là nơi khai báo ra các class đặt tên và thuộc tính giống với lại tên bảng trong database
//Nếu cột là khóa ngoại thì không khai báo biến mà sẽ chuyển thành đối tượng của bảng được tham chiếu tới
public class Users {
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String userName;
	private Role role;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
