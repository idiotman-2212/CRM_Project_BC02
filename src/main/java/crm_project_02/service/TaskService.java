package crm_project_02.service;

import java.sql.SQLException;
import java.util.List;

import crm_project_02.entity.GroupWork;
import crm_project_02.entity.Status;
import crm_project_02.entity.Task;
import crm_project_02.entity.Users;
import crm_project_02.repository.GroupWorkRepository;
import crm_project_02.repository.TaskRepository;
import crm_project_02.repository.UserRepository;

public class TaskService {
	
	private UserRepository userRepository = new UserRepository();
	private GroupWorkRepository  groupWorkRepository = new GroupWorkRepository();
	private TaskRepository taskRepository = new TaskRepository();
	
	public List<Task> getAllTasks(){
		return taskRepository.getAllTask();
	}
	
	public List<Status> getAllStatus() throws SQLException{
		return taskRepository.getAllStatus();
	}
	
	public List<Task> getAllTaskForProfile(){
		return taskRepository.getAllTaskForProfile();
	}
	
	public List<Users> getAllUsers() throws SQLException{
		return taskRepository.getAllUsers();
	}
	
	public List<GroupWork> getAllGroupWorks() throws SQLException{
		return taskRepository.getAllGroupWork();
	}
	
	public boolean insertTask(int id_project, String name, int id_user, String start_date, String end_date) {
		int count = taskRepository.insert(id_project, name, id_user, start_date, end_date);
		return count > 0;
	}
	
	public boolean insertTaskForProfile(int id_project, String name, String startDate, String endDate, int id_status) {
		int count = taskRepository.insertTaskForProfile(id_project, name, startDate, endDate, id_status);
		return count > 0;
	}
	
	public boolean deleteTaskById(int id) {
		int count =  taskRepository.deleteTaskById(id);
		return count > 0;
	}
	public Task findById(int id){
		return taskRepository.findById(id);
	}
	
	public boolean updateTask(int id_project, String name, int id_user, String startDate, String endDate, int id_status, int id) {
		int count =  taskRepository.updateTaskById(id_project, name, id_user, startDate, endDate, id_status, id);
		return count > 0;
	}
	
}
