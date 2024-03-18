package co.ethpays.explorer.repositories;

import co.ethpays.explorer.entity.SimpleTransactionDto;
import co.ethpays.explorer.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findByTransactionId(String transactionId);
    List<Transaction> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);
    List<Transaction> findByIsEthpaysTransactionIsTrueOrderByCreatedAtDesc(Pageable pageable);
}