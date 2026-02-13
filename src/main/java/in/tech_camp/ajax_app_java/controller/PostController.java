package in.tech_camp.ajax_app_java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.ajax_app_java.entity.PostEntity;
import in.tech_camp.ajax_app_java.form.PostForm;
import in.tech_camp.ajax_app_java.repository.PostRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PostController {

  private final PostRepository postRepository;

  @GetMapping("/")
  public String showList(Model model) {
    var postList = postRepository.findAll();
    model.addAttribute("postList", postList);
    model.addAttribute("postForm", new PostForm());
    return "posts/index";
  }

  // @GetMapping("/postForm")
  // public String showPostForm(@ModelAttribute("postForm") PostForm form){
  //     return "posts/postForm";
  // }

  @PostMapping("/posts")
  public String savePost(@ModelAttribute("postForm") PostForm form){
    PostEntity post = new PostEntity();
    post.setContent(form.getContent());
    postRepository.insert(post);
    return "redirect:/";
  }

}