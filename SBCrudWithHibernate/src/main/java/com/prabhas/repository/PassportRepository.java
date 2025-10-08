package com.prabhas.repository;

import com.prabhas.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    
    Optional<Passport> findByNumber(String number);
    boolean existsByNumber(String number);
}
