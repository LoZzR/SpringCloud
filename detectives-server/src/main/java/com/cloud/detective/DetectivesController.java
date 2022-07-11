package com.cloud.detective;

import com.cloud.detective.services.DetectiveService;
import com.entities.detective.Detective;
import com.entities.ex.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.entities.base.AbstractEntity.COMPARATOR_BY_ID;

@RestController
@RequestMapping("/detectives")
public class DetectivesController {

    private DetectiveService detectiveService;

    public DetectivesController(DetectiveService detectiveService) {
        this.detectiveService = detectiveService;
    }

    /**
     * Handles requests to list all detectives.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Detective> list() {
        List<Detective> detectives =  detectiveService.findAll();
        detectives.sort(COMPARATOR_BY_ID);
        return detectives;
    }

    /**
     * Returns the {@code detective} instance with id {@code id}
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Detective show(@PathVariable Long id) {
        var DetectiveOpt = detectiveService.findById(id);
        if(DetectiveOpt.isPresent()) {
            return DetectiveOpt.get();
        } else {
            throw new NotFoundException(Detective.class, id);
        }
    }

    /**
     * Returns the {@code detective} instance with badge number {@code badgeNo}
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search/{badgeNo}")
    public Detective search(@PathVariable String badgeNo) {
        var detectiveOpt =  detectiveService.findByBadgeNumber(badgeNo);
        if(detectiveOpt.isPresent() ) {
            return detectiveOpt.get();
        } else {
            throw new DetectivesException("Detective with badge number " + badgeNo +" was not found!");
        }
    }

    /**
     * Updates the {@code detctive} instance with id {@code id}
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Detective updatedDetective, @PathVariable Long id) {
        var detectiveOpt = detectiveService.findById(id);
        if(detectiveOpt.isPresent()) {
            var detective = detectiveOpt.get();
            detective.setBadgeNumber(updatedDetective.getBadgeNumber());
            detective.setRank(updatedDetective.getRank());
            detective.setArmed(updatedDetective.getArmed());
            detective.setStatus(updatedDetective.getStatus());
            detectiveService.save(detective);
        } else {
            throw new NotFoundException(Detective.class, id);
        }
    }

    /**
     * Delete the {@code Detective} instance with id {@code id}
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        var detectiveOpt = detectiveService.findById(id);
        detectiveOpt.ifPresent(value -> detectiveService.delete(value));
    }
}
