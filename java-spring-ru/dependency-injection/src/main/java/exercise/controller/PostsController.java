package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("posts")
public class PostsController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    PostsController(PostRepository postRepository, CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody Post body) {
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        post.setTitle(body.getTitle());
        post.setBody(body.getBody());
        return ResponseEntity.ok(postRepository.save(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        postRepository.delete(post);
        commentRepository.deleteByPostId(post.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
// END
