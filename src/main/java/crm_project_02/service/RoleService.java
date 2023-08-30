package crm_project_02.service;

import java.util.List;

import crm_project_02.entity.Role;
import crm_project_02.repository.RoleRepository;

/**
 * 
 * @author binhcc
 *	Service : chứa những class chuyên đi xử lý logic cho controller
 *  cách đặt tên : Giống với controller : Ví dụ : RoleController => RoleService
 *  
 *  Cách đặt tên hàm : Đặt tên hàm ứng với lại chức năng sẽ làm trên giao diện/bên controller
 */
public class RoleService {

	private RoleRepository roleRepository = new RoleRepository();
	
	public boolean addRole(String name, String desc) {
		int count = roleRepository.insert(name, desc);
		
		return count > 0;
		
	}
	public boolean insertRole( String name, String description) {
		int count = roleRepository.insert(name, description);
		return count > 0;
	}
	public List<Role> getAllRole(){
		return roleRepository.findAll();
	}
	public boolean deleteRole(int id) {
		int count  = roleRepository.deleteRoleById(id);
		return count > 0;
	}
	public boolean updateRole(int id, String name, String description) {
		int count =  roleRepository.updateRoleById(id, name, description);
		return count > 0;
	}
}
