package crm_project_02.service;

import java.sql.SQLException;
import java.util.List;

import crm_project_02.entity.GroupWork;
import crm_project_02.repository.GroupWorkRepository;


public class GroupWorkService {
	private GroupWorkRepository groupWorkRepository = new GroupWorkRepository();

	public boolean insertGroupWork(String name , String startDate, String endDate) {
		int count = groupWorkRepository.insert(name, startDate, endDate);
		
		return count > 0;
	}
	public List<GroupWork> getAllGroupWork() throws SQLException {
		return groupWorkRepository.getAllGroupWork();
	}
	
	public boolean deleteGroupWork(int id) {
		int count = groupWorkRepository.deleteGroupWorkById(id);
		return count  > 0;
	}
	public boolean updateGroupWork(String name, String startDate, String endDate, int id) {
		int count  = groupWorkRepository.updateGroupWorkById(name, startDate, endDate, id);
		return count > 0;
	}
}
