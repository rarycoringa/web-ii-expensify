package br.edu.ufrn.expensify.transaction.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufrn.expensify.auth.entity.User;
import br.edu.ufrn.expensify.transaction.entity.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    
    public List<Income> findAllByUser(User user);

    public Optional<Income> findByIdAndUser(UUID id, User user);

}
