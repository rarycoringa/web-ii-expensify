package br.edu.ufrn.expensify.transaction.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufrn.expensify.auth.entity.User;
import br.edu.ufrn.expensify.transaction.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    
    public List<Transaction> findAllByUser(User user);

    public Optional<Transaction> findByIdAndUser(UUID id, User user);

}