package br.edu.ufrn.expensify.transaction.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufrn.expensify.auth.entity.User;
import br.edu.ufrn.expensify.transaction.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    
    public List<Transfer> findAllByUser(User user);

    public Optional<Transfer> findByIdAndUser(UUID id, User user);

}
