package com.cloud.person;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import com.cloud.person.services.PersonService;
import com.entities.ex.NotFoundException;
import com.entities.util.CriteriaDto;
import com.entities.util.NumberGenerator;
import com.entities.person.Person;

import static com.entities.base.AbstractEntity.COMPARATOR_BY_ID;

@RestController
@RequestMapping("/persons")
public class PersonsController {
    private PersonService personService;

    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles requests to list all persons.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> list() {
        List<Person> persons =  personService.findAll();
        persons.sort(COMPARATOR_BY_ID);
        return persons;
    }

    /**
     * Handles requests to create a person.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Validated(Person.BasicValidation.class) @RequestBody Person person, BindingResult result, @Value("#{request.requestURL}")
            StringBuffer originalUrl, HttpServletResponse response) {
        if (result.hasErrors()) {
            var errString = createErrorString(result);
            throw new PersonsException(HttpStatus.BAD_REQUEST, "Cannot save entry because: "+ errString);
        }
        // This a workaround for a Jackson bug, the field is actually not deserialized. This is the issue I've found already created on GitHub:
        // https://github.com/FasterXML/jackson-databind/issues/935#issuecomment-520070413.
        // It is closed, but the bug is still there in version 2.9.9. when I asked about it,
        // I was told to create a new issue, which I will, as soon as this book is published.
        if(StringUtils.isEmpty(person.getPassword())){
            person.setPassword(NumberGenerator.getPassword());
        }
        try {
            var newPerson = personService.save(person);
            response.setHeader("Location", getLocationForUser(originalUrl, newPerson.getId()));
        } catch (Exception e) {
            throw  new PersonsException(HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    /**
     * Determines URL of user resource based on the full URL of the given request,
     * appending the path info with the given childIdentifier using a UriTemplate.
     */
    static String getLocationForUser(StringBuffer url, Object childIdentifier) {
        var template = new UriTemplate(url.toString() + "/{id}");
        return template.expand(childIdentifier).toASCIIString();
    }

    private String createErrorString(BindingResult result) {
        var sb =  new StringBuilder();
        result.getAllErrors().forEach(error -> {
            if(error instanceof FieldError) {
                FieldError err= (FieldError) error;
                sb.append("Field '").append(err.getField()).append("' value error: ").append(err.getDefaultMessage()).append("\n");
            }
        });
        return sb.toString();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search")
    public List<Person> processSearch(@Validated @RequestBody CriteriaDto criteria) {
        return personService.getByCriteriaDto(criteria);
    }

    /**
     * Returns the {@code Person} instance with id {@code id}
     * @param id
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person show(@PathVariable Long id) {
        var personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            return personOpt.get();
        } else {
            throw new NotFoundException(Person.class, id );
        }
    }

    /**
     * Updates the {@code Person} instance with id {@code id}
     * @param updatedPerson
     * @param id
     * @return
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Person updatedPerson, @PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            var person = personOpt.get();
            person.setUsername(updatedPerson.getUsername());
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            personService.save(person);
        } else {
            throw new NotFoundException(Person.class, id );
        }
    }

    /**
     * Delete the {@code Person} instance with id {@code id}
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        var personOpt = personService.findById(id);
        personOpt.ifPresent(value -> personService.delete(value));
    }
}