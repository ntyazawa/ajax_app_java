package in.tech_camp.ajax_app_java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;

import in.tech_camp.ajax_app_java.entity.PostEntity;
import in.tech_camp.ajax_app_java.form.PostForm;
import org.apache.ibatis.annotations.Options;
import in.tech_camp.ajax_app_java.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

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



@PostMapping("/posts")
public ResponseEntity<PostEntity> savePost(@ModelAttribute("postForm") PostForm form){
    // 1. フォームから送られた内容で空のエンティティを作成
    PostEntity post = new PostEntity();
    post.setContent(form.getContent());

    // 2. データベースへ保存（@Optionsにより、この時点でpostにIDがセットされる）
    postRepository.insert(post);

    // 3. セットされたIDを使って、DBから完全なデータ（日時入り）を取得
    PostEntity resultPost = postRepository.findById(post.getId());

    // 4. ステータスOK(200)と、取得したデータをJSONとして返却
    return ResponseEntity.ok(resultPost);
}
}