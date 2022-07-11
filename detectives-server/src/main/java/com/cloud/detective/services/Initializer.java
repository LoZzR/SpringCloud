package com.cloud.detective.services;

import com.entities.detective.Detective;
import com.entities.util.EmploymentStatus;
import com.entities.util.NumberGenerator;
import com.entities.util.Rank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class Initializer {
    private Logger logger = LoggerFactory.getLogger(Initializer.class);

    private DetectiveService detectiveService;


    public Initializer(DetectiveService detectiveService) {
        this.detectiveService = detectiveService;
    }

    @PostConstruct
    public void init() {
        logger.info(" -->> Starting database initialization...");
        if (detectiveService.findAll().isEmpty()) {
            createDetectives();
        }
        logger.info(" -->> Database initialization finished.");
    }

    private void createDetectives() {
        Detective detective = createDetective(1L, Rank.INSPECTOR, false, EmploymentStatus.ACTIVE);
        detectiveService.save(detective);

        detective = createDetective(2L, Rank.SENIOR, true, EmploymentStatus.ACTIVE);
        detectiveService.save(detective);

        detective = createDetective(3L, Rank.TRAINEE, false, EmploymentStatus.VACATION);
        detectiveService.save(detective);

        detective = createDetective(4L, Rank.INSPECTOR, true, EmploymentStatus.SUSPENDED);
        detectiveService.save(detective);

    }

    private Detective createDetective(Long personId, Rank rank, Boolean armed, EmploymentStatus status){
        Detective detective = new Detective();
        detective.setPersonId(personId);
        detective.setBadgeNumber(NumberGenerator.getBadgeNumber());
        detective.setRank(rank);
        detective.setArmed(armed);
        detective.setStatus(status);
        return detective;
    }
}