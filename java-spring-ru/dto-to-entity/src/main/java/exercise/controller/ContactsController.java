package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN

    Contact toEntity(ContactCreateDTO dto) {
        Contact contact = new Contact();
        contact.setPhone(dto.getPhone());
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        return contact;
    }

    ContactDTO toDto(Contact entity) {
        ContactDTO dto = new ContactDTO();
        dto.setPhone(entity.getPhone());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@RequestBody ContactCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(contactRepository.save(toEntity(dto))));
    }

    // END
}
