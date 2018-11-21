package blog.services;

import blog.models.User;
import blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Primary
public class UserServiceJpaImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> findAll() {
        return this.userRepo.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.userRepo.findOne(id);
    }

    @Override
    public User create(User user) {
        return this.userRepo.save(user);
    }

    @Override
    public User edit(User user) {
        return this.userRepo.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepo.delete(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public boolean authenticate(String username, String password) {
        // Provide a sample password check: username == password
        return Objects.equals(username, password);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }
}