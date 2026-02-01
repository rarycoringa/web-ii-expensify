package br.edu.ufrn.expensify.account.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufrn.expensify.account.entity.Account;
import br.edu.ufrn.expensify.auth.entity.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findAllByUser(User user);

    Optional<Account> findByIdAndUser(UUID id, User user);

    void deleteByIdAndUser(UUID id, User user);

}
