package blog.controllers;

import blog.models.Post;
import blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

    @Controller
    @SessionAttributes("user")
    public class HomeController {

        @Autowired
        private PostService postService;

        @RequestMapping(value = { "/" }, method = RequestMethod.GET)
        public ModelAndView get(Model model, Pageable pageable) {

            /*Page<Post> posts = postService.getPosts(pageable);*/

            List <Post> posts = postService.findLatest5();

            model.addAttribute("posts", posts);

            return new ModelAndView("/index");
        }


}

