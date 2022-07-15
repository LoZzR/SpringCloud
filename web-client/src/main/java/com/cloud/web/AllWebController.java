package com.cloud.web;

import com.entities.detective.Detective;
import com.entities.ex.InvalidCriteriaException;
import com.entities.person.Person;
import com.entities.util.CriteriaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
public class AllWebController {

    private static Logger logger = LoggerFactory.getLogger(AllWebController.class);

    private AllWebServices allWebServices;


    public AllWebController(AllWebServices allWebServices) {
        this.allWebServices = allWebServices;
    }

    @GetMapping(value = "/persons")
    public List<Person> listPersons() {
        logger.info("Populating model with person list...");
        List<Person> persons =  allWebServices.getAllPersons();
        return persons;
    }

    @GetMapping(value = "/persons/{id}")
    public Person getPerson(@PathVariable Long id){
        return allWebServices.getPerson(id);
    }

    @GetMapping(value = "/detectives")
    public List<Detective> listDetectives(Model model) {
        logger.info("Populating model with detective list...");
        List<Detective> detectives =  allWebServices.getAllDetectives();
        return detectives;
    }

    @GetMapping(value = "/detectives/{id}")
    public Detective getDetective(@PathVariable Long id){
        return allWebServices.getDetective(id);
    }

}
