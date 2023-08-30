package crm_project_02.entity;

import java.util.Date;

public class ProjectUsers {
    private Users users;
    private GroupWork groupWork;
    private String createDate;
    
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public GroupWork getGroupWork() {
		return groupWork;
	}
	public void setGroupWork(GroupWork groupWork) {
		this.groupWork = groupWork;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

    
}
