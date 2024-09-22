package exercise.controller;

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

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("comments")
public class CommentsController {
    CommentRepository commentRepository;

    private Comment getCommentById(long id){
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id " +  id + " not found"));
    }

    public CommentsController (CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> findAll() {
        return ResponseEntity.ok(commentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findById(@PathVariable long id) {
        return ResponseEntity.ok(getCommentById(id));
    }

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentRepository.save(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable long id, @RequestBody Comment body) {
        var comment = getCommentById(id);
        comment.setBody(body.getBody());
        return ResponseEntity.ok(commentRepository.save(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> delete(@PathVariable long id) {
        commentRepository.delete(getCommentById(id));
        return ResponseEntity.ok().build();
    }

}
// END
