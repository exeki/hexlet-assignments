package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.BadRequestException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;


    // BEGIN
    protected Author getAuthorOrThrow(long id) {
        return authorRepository.findById(id).orElseThrow(() -> new BadRequestException("Author not found"));
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll().stream().map(a -> authorMapper.map(a)).toList();
    }

    public AuthorDTO getById(long id) {
        return authorMapper.map(getAuthorOrThrow(id));
    }

    public AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.map(authorCreateDTO);
        return authorMapper.map(authorRepository.save(author));
    }

    public AuthorDTO updateAuthor(long id, AuthorUpdateDTO authorUpdateDTO) {
        Author author = getAuthorOrThrow(id);
        authorMapper.update(authorUpdateDTO, author);
        return authorMapper.map(authorRepository.save(author));
    }

    public void deleteAuthor(long id) {
        authorRepository.delete(getAuthorOrThrow(id));
    }
    // END
}
