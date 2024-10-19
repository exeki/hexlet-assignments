package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Book;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorService authorService;

    protected Book getByIdOrThrow(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    public List<BookDTO> getAll(){
        return bookRepository.findAll().stream().map(b -> bookMapper.map(b)).toList();
    }

    public BookDTO getById(long id){
        return bookMapper.map(getByIdOrThrow(id));
    }

    public BookDTO create(BookCreateDTO dto){
        Book book = bookMapper.map(dto);
        book.setAuthor(authorService.getAuthorOrThrow(dto.getAuthorId()));
        return bookMapper.map(bookRepository.save(book));
    }

    public BookDTO update(long id, BookUpdateDTO dto){
        Book book = getByIdOrThrow(id);
        bookMapper.update(dto, book);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public void delete(long id){
        bookRepository.delete(getByIdOrThrow(id));
    }

    // END
}
