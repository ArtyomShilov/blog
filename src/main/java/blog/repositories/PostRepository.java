package blog.repositories;

import blog.models.Post;
import blog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.date DESC")

    List<Post> findLatest5Posts(Pageable pageable);

    Page<Post> findAllByOrderByDate(Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAuthorOrderByDate(User user, Pageable pageable);
}