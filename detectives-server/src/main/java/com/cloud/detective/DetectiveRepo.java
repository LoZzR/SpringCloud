package com.cloud.detective;

import com.entities.detective.Detective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DetectiveRepo extends JpaRepository<Detective, Long> {

    @Query("select d from Detective d where d.badgeNumber=:bn")
    Optional<Detective> findByBadgeNumber(@Param("bn") String badgeNumber);
}
