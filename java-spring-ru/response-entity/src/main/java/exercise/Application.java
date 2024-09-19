package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("posts/{id}")
    ResponseEntity<Post> getPost(@PathVariable String id) {
        var entity = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(entity);
    }

    @GetMapping("posts")
    ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(posts.size())).body(posts);
    }

    @PostMapping("posts")
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("posts/{id}")
    ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        var existed = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (existed.isPresent()) {
            existed.get().setTitle(post.getTitle());
            existed.get().setBody(post.getBody());
            existed.get().setId(post.getId());
            return ResponseEntity.ok(existed.get());
        } else return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    // END
    @DeleteMapping("posts/{id}")
    void deletePost(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
