package br.edu.ufrn.expensify.transaction.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufrn.expensify.auth.entity.User;
import br.edu.ufrn.expensify.transaction.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    
    public List<Expense> findAllByUser(User user);

    public Optional<Expense> findByIdAndUser(UUID id, User user);

}
