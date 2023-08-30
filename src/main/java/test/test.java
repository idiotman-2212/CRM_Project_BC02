package test;

import java.util.List;

import crm_project_02.entity.Users;
import crm_project_02.repository.UserRepository;
import crm_project_02.service.UserService;

public class test {
	public static void main(String [] arg) {
		//Users users = new Users();
		UserRepository userRepository = new UserRepository();
		UserService userService = new UserService();
		List<Users> u = userRepository.findById(15);
		System.out.println(u);
	}
}
