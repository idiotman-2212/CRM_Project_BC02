package crm_project_02.service;

import crm_project_02.entity.Users;
import crm_project_02.repository.UserRepository;

public class AuthService {
    UserRepository userRepository = new UserRepository();
    public Users getUser(String email){
        return userRepository.getUserByEmail(email);
    }
    public int getRoleByEmail(String email){
        return userRepository.getRoleByEmail(email);
    }
}
