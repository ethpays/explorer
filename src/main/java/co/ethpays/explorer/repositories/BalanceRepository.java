package co.ethpays.explorer.repositories;

import co.ethpays.explorer.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {
    Balance findByAddress(String address);
}