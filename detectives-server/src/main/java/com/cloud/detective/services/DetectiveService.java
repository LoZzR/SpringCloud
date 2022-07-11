package com.cloud.detective.services;

import com.entities.detective.Detective;

import java.util.List;
import java.util.Optional;

public interface DetectiveService {

    List<Detective> findAll();

    Optional<Detective> findById(Long id);

    Optional<Detective> findByBadgeNumber(String badgeNumber);

    Detective save(Detective detective);

    void delete(Detective detective);
}
