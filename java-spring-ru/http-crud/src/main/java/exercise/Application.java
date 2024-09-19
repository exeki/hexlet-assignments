package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("posts")
    List<Post> getPosts(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        int start = (page - 1) * limit;
        int end = start + limit;
        return posts.subList(start, end);
    }

    // BEGIN
    @GetMapping("posts/{id}")
    Optional<Post> getPost(@PathVariable String id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @PostMapping("posts")
    Post createPost(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("posts/{id}")
    Optional<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        var existed = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (existed.isPresent()) {
            existed.get().setTitle(post.getTitle());
            existed.get().setBody(post.getBody());
            existed.get().setId(post.getId());
        }
        return existed;
    }

    @DeleteMapping("posts/{id}")
    void deletePost(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}
