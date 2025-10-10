package com.prabhas.Emp_Order_Service.repository;

import com.prabhas.Emp_Order_Service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByEmployeeId(Long employeeId);
    
    List<Order> findByOrderStatus(String orderStatus);
    
    @Query("SELECT o FROM Order o WHERE o.totalAmount >= ?1")
    List<Order> findByTotalAmountGreaterThanEqual(Double amount);

	List<Order> findAll();
}