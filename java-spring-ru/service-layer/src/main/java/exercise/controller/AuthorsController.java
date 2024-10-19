package exercise.controller;

import exercise.dto.AuthorDTO;
import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping
    public List<AuthorDTO> getAllAuthors(){
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable int id){
        return authorService.getById(id);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody @Valid AuthorCreateDTO authorCreateDTO){
        return  ResponseEntity.status(HttpStatus.CREATED).body(authorService.createAuthor(authorCreateDTO));
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(@PathVariable int id, @RequestBody @Valid AuthorUpdateDTO authorUpdateDTO){
        return authorService.updateAuthor(id, authorUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id){
        authorService.deleteAuthor(id);
    }

    // END
}
