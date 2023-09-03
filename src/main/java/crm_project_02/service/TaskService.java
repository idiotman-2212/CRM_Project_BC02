package crm_project_02.service;

import java.sql.SQLException;
import java.util.List;

import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Task;
import crm_project_02.entity.Users;
import crm_project_02.repository.GroupWorkRepository;
import crm_project_02.repository.TaskRepository;
import crm_project_02.repository.UserRepository;

public class TaskService {
	
	private UserRepository userRepository = new UserRepository();
	private GroupWorkRepository  groupWorkRepository = new GroupWorkRepository();
	private TaskRepository taskRepository = new TaskRepository();
	
	public List<Task> getAllTask(){
		return taskRepository.getAllTask();
	}
	
	public List<Users> getAllUsers(){
		return userRepository.getAllUsers();
	}
	
	public List<GroupWork> getAllGroupWorks() throws SQLException{
		return groupWorkRepository.getAllGroupWork();
	}
	
	public boolean insertTask(int project_name, String name, int performer, String start_date, String end_date) {
		int count = taskRepository.insert(project_name, name, performer, start_date, end_date);
		return count > 0;
	}
	
	public boolean deleteTask(String id) {
		int count =  taskRepository.deleteTaskById(id);
		return count > 0;
	}
	public List<Task> findById(String id){
		return taskRepository.findById(id);
	}
	
	public boolean updateTask(int project_name, String name, int performer, String start_date, String end_date) {
		int count =  taskRepository.updateTaskById(end_date, project_name, name, project_name, start_date, end_date, performer);
		return count > 0;
	}
}
