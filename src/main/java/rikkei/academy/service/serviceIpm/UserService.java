package rikkei.academy.service.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Users;
import rikkei.academy.repository.IUserRepository;
import rikkei.academy.service.IUserService;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;


    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Users findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public boolean existsByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
