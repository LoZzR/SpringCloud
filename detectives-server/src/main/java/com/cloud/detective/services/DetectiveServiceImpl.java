package com.cloud.detective.services;

import com.entities.detective.Detective;
import com.cloud.detective.DetectiveRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetectiveServiceImpl implements DetectiveService {

    private DetectiveRepo detectiveRepo;

    public DetectiveServiceImpl(DetectiveRepo detectiveRepo) {
        this.detectiveRepo = detectiveRepo;
    }

    @Override
    public List<Detective> findAll() {
        return detectiveRepo.findAll();
    }

    @Override
    public Optional<Detective> findById(Long id) {
        return detectiveRepo.findById(id);
    }

    @Override
    public Optional<Detective> findByBadgeNumber(String badgeNumber) {
        return detectiveRepo.findByBadgeNumber(badgeNumber);
    }

    @Override
    public Detective save(Detective detective) {
        return detectiveRepo.save(detective);
    }

    @Override
    public void delete(Detective detective) {
        detectiveRepo.delete(detective);
    }
}
