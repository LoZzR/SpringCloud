package com.cloud.web;


import com.entities.detective.Detective;
import com.entities.ex.InvalidCriteriaException;
import com.entities.person.Person;
import com.entities.util.CriteriaDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.entities.base.AbstractEntity.COMPARATOR_BY_ID;


@Service
public class AllWebServices {

    private RestTemplate restTemplate;

    private static final String PERSONS_SERVICE_URL = "http://persons-service";

    private static final String DETECTIVES_SERVICE_URL = "http://detectives-service";

    public AllWebServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    List<Person> getAllPersons(){
        var persons = restTemplate.getForObject(PERSONS_SERVICE_URL.concat("/persons"), Person[].class);
        assert persons != null;
        var personsList =  Arrays.asList(persons);
        personsList.sort(COMPARATOR_BY_ID);
        return personsList;
    }

    Person getPerson(Long personId) {
        return restTemplate.getForObject(PERSONS_SERVICE_URL.concat("/persons/" + personId), Person.class);
    }

    List<Detective> getAllDetectives(){
        var detectives = restTemplate.getForObject(DETECTIVES_SERVICE_URL.concat("/detectives"), Detective[].class);
        assert detectives != null;
        var detectiveList =  Arrays.asList(detectives);
        detectiveList.forEach(d ->
            d.setPerson(restTemplate.getForObject(PERSONS_SERVICE_URL.concat("/persons/" + d.getPersonId()), Person.class))
        );
        detectiveList.sort(COMPARATOR_BY_ID);
        return detectiveList;
    }

    Detective getDetective(Long detectiveId) {
        var detective = restTemplate.getForObject(DETECTIVES_SERVICE_URL.concat("/detectives/" + detectiveId), Detective.class);
        detective.setPerson(getPerson(detective.getPersonId()));
        return detective;
    }

    List<Person> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CriteriaDto> entity = new HttpEntity<>(criteria, headers);
        ResponseEntity<Person[]> responseEntity = restTemplate.exchange(PERSONS_SERVICE_URL.concat("/persons/search"), HttpMethod.GET, entity, Person[].class);

        var persons = responseEntity.getBody();
        assert persons != null;
        var personsList =  Arrays.asList(persons);
        personsList.sort(COMPARATOR_BY_ID);
        return personsList;
    }
}
