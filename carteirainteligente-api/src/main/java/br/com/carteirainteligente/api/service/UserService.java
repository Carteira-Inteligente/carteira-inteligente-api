package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);

        if(existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());

            userRepository.save(existingUser);
            return existingUser;
        }

        return null;
    }

    public User deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if(existingUser != null) {
            userRepository.deleteById(id);
            return existingUser;
        }

        return null;
    }
}