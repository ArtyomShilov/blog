package blog.services;

import blog.models.Post;
import blog.models.User;
import blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class PostServiceJpaImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Override
    public List<Post> findAll() {
        return this.postRepo.findAll();
    }

    @Override
    public List<Post> findLatest5() {
        return this.postRepo.findLatest5Posts(new PageRequest(0, 5));
    }

    @Override
    public Post findById(Long id) {
        return this.postRepo.findOne(id);
    }

    @Override
    public Post create(Post post) {
        return this.postRepo.save(post);
    }

    @Override
    public Post edit(Post post) {
        return this.postRepo.save(post);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepo.delete(id);
    }

    @Override
    public Page<Post> findByAuthor(User user, Pageable pageable) {
        return postRepo.findByAuthorOrderByDate(user, pageable);
    }

    public Page<Post> getPosts(Integer page, Integer size) {
        PageRequest request = new PageRequest(page, size, Sort.Direction.DESC, "date");
        return postRepo.findAll(request);
    }

    @Override
    public Page<Post> getPosts(Pageable pageable) {
        return postRepo.findAllByOrderByDate(pageable);
    }





}