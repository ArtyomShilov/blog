package blog.services;

import blog.models.Post;
import blog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    List<Post> findLatest5();
    Post findById(Long id);
    Post create(Post post);
    Post edit(Post post);
    void deleteById(Long id);

    Page<Post> getPosts(Integer page, Integer size);
    Page<Post> getPosts(Pageable pageable);
    Page<Post> findByAuthor(User user, Pageable pageable);
}
