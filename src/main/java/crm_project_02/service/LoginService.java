package crm_project_02.service;

import crm_project_02.repository.UserRepository;

public class LoginService {
    public boolean checkLogin(String email, String password){
        UserRepository userRepository = new UserRepository();
        int countUser = userRepository.countUserByEmailNPassword(email,password);
        return countUser > 0;
    }
}
