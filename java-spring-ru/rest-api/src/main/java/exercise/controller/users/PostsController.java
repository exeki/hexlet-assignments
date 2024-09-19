package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

@RestController
@RequestMapping("/api")
class PostsController {

    // BEGIN
    List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    ResponseEntity<List<Post>> findPostsByUser(@PathVariable int id) {
        var posts = this.posts.stream().filter(p -> p.getUserId() == id).toList();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/users/{userId}/posts")
    ResponseEntity<Post> createPost(@PathVariable int userId, @RequestBody Post post) {
        post.setUserId(userId);
        this.posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}
// END
