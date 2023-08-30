package crm_project_02.entity;

public class Task {
	private int id;
	private String name;
	private String content;
	private String startDate;
	private String endDate;
	private Users users;
	private Status status;
	private GroupWork groupWork;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public GroupWork getGroupWork() {
		return groupWork;
	}
	public void setGroupWork(GroupWork groupWork) {
		this.groupWork = groupWork;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	
	
}
