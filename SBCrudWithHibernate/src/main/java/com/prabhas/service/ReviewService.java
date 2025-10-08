package com.prabhas.service;

import com.prabhas.model.Review;
import com.prabhas.model.Employee;
import com.prabhas.repository.ReviewRepository;
import com.prabhas.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    
    public Review createReview(Review review) {
        // Validate employee exists
        if (review.getEmployee() != null && review.getEmployee().getId() != null) {
            Employee employee = employeeRepository.findById(review.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
            review.setEmployee(employee);
        }
        
        return reviewRepository.save(review);
    }
    
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        
        review.setStars(reviewDetails.getStars());
        review.setDescription(reviewDetails.getDescription());
        
        return reviewRepository.save(review);
    }
    
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        reviewRepository.delete(review);
    }
    
    public List<Review> getReviewsByEmployee(Long employeeId) {
        return reviewRepository.findByEmployeeId(employeeId);
    }
    
    public Double getAverageStarsForEmployee(Long employeeId) {
        return reviewRepository.getAverageStarsByEmployeeId(employeeId);
    }
}
