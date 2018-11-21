package blog.services;
import blog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User create(User user);
    User edit(User user);
    void deleteById(Long id);
    Page<User> getUsers(Pageable pageable);

    boolean authenticate(String username, String password);
}