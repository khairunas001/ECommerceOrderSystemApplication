package e_commerce_order_system.e_commerce_order_system.repository;

import e_commerce_order_system.e_commerce_order_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Transaction, String> {
}
