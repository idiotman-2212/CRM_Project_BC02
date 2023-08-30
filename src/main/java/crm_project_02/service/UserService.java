package crm_project_02.service;

import java.util.List;

import crm_project_02.entity.Role;
import crm_project_02.entity.Users;
import crm_project_02.repository.RoleRepository;
import crm_project_02.repository.UserRepository;

public class UserService {

	private RoleRepository roleRepository = new RoleRepository();
	private UserRepository userRepository = new UserRepository();
	
	public List<Role> getAllRole(){
		return roleRepository.findAll();
	}
	
	public boolean insertUser(String fullname, String email, String pwd, String phone, int idRole) {
		int count = userRepository.insert(fullname, email, pwd, phone, idRole);
		
		return count > 0;
	}
	
	public List<Users> getAllUsers(){
		return userRepository.getAllUsers();
	}
	public boolean deleteUser(int id) {
		int count = userRepository.deleteById(id);
		
		return count > 0;
	}
	
	public List<Users> findById(int id){
		return userRepository.findById(id);
	}
	
	public boolean updateUser(int id, String firstName, String lastName, String userName, int idRole) {
	    int count = userRepository.updateById(id, firstName, lastName, userName, idRole);
	    
	    return count > 0;
	}

}
