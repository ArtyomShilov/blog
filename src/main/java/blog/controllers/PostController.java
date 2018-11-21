package blog.controllers;

import blog.models.Post;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.NotificationServiceImpl;
import blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;


@Controller
@SessionAttributes("user")

public class PostController {



    @Autowired
    private PostService postService;

    @Autowired
    private NotificationServiceImpl notifyService;



    @RequestMapping(value = { "/posts" }, method = RequestMethod.GET)
    public ModelAndView get(Model model, Pageable pageable) {
        if (!model.containsAttribute("user")) return new ModelAndView("redirect:/users/login");

        User user = (User) model.asMap().get("user");

        Page<Post> posts = postService.findByAuthor(user, pageable);

        model.addAttribute("posts", posts);

        return new ModelAndView("/posts/index");
    }

    @RequestMapping("posts/{id}")
    public String get(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("lastPosts", postService.getPosts(0,  5).getContent());

        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        model.addAttribute("post", postService.findById(id));
        return "/posts/post";
    }



    @RequestMapping(value = "/posts/create", method = RequestMethod.GET)
    public String newPost(Model model) {
        if (!model.containsAttribute("user")) {
            System.out.println("no users");
            return "redirect:/users/login";
        }

        Post post = new Post();
        post.setId(0L);
        User user = (User) model.asMap().get("user");
        post.setAuthor(user);
        post.setDate(new Date());
        model.addAttribute("post", post);

        return "/posts/edit";
    }


    @RequestMapping(value = { "posts/edit/{id}", "posts/edit/"})
    public String edit(@PathVariable Long id, Model model){
        if (!model.containsAttribute("user")) return "redirect:/users/login";

        Post post = postService.findById(id);
        model.addAttribute("post", post);

        return "posts/edit";
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public String save(Post post, Model model) {
        if (!model.containsAttribute("user")) return "redirect:/users/login";

        if (post.getTitle() == null || post.getTitle().trim().length() < 1) {
            notifyService.addErrorMessage("Title should be more than 1 chars.");
            return "posts/edit";
        }
        if (post.getTitle().length() > 100) {
            notifyService.addErrorMessage("Title should be less than 100 chars.");
            return "posts/edit";
        }
        if (post.getBody() == null || post.getBody().trim().length() < 1) {
            notifyService.addErrorMessage("Body should be more than 1 chars.");
            return "posts/edit";
        }

        System.out.println(post);

        if (post.getId() != null && post.getId() != 0) {
            post = postService.edit(post);
        } else {
            post.setAuthor((User) model.asMap().get("user"));
            post.setDate(new Date());
            post = postService.create(post);
        }

        notifyService.addInfoMessage("Post created successfully");
        return "redirect:/posts/edit/" + post.getId();
    }



    @RequestMapping("posts/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        if (!model.containsAttribute("user")) return "redirect:/users/login";

        postService.deleteById(id);
        return "redirect:/posts";
    }
}
