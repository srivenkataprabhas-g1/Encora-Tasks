package com.prabhas.repository;

import com.prabhas.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByEmployeeId(Long employeeId);
    List<Review> findByStars(int stars);
    List<Review> findByStarsGreaterThanEqual(int stars);
    
    @Query("SELECT AVG(r.stars) FROM Review r WHERE r.employee.id = :employeeId")
    Double getAverageStarsByEmployeeId(@Param("employeeId") Long employeeId);
    @Query("SELECT r FROM Review r WHERE r.employee.id = :employeeId ORDER BY r.id DESC")
    List<Review> findLatestReviews(@Param("employeeId") Long employeeId);
    @Query("SELECT r FROM Review r WHERE r.employee.id = :employeeId ORDER BY r.stars DESC")
    List<Review> findTopReviews(@Param("employeeId") Long employeeId);
    @Query("SELECT r FROM Review r WHERE r.employee.id = :employeeId ORDER BY r.reviewDate DESC")
    List<Review> findLatestReviewsByEmployee(@Param("employeeId") Long employeeId);
}
