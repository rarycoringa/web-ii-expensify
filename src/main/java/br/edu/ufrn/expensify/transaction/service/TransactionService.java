package br.edu.ufrn.expensify.transaction.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.edu.ufrn.expensify.account.service.AccountService;
import br.edu.ufrn.expensify.auth.entity.User;
import br.edu.ufrn.expensify.auth.service.AuthService;
import br.edu.ufrn.expensify.transaction.entity.Expense;
import br.edu.ufrn.expensify.transaction.entity.Income;
import br.edu.ufrn.expensify.transaction.entity.Transaction;
import br.edu.ufrn.expensify.transaction.entity.Transfer;
import br.edu.ufrn.expensify.transaction.exception.TransactionNotFoundException;
import br.edu.ufrn.expensify.transaction.repository.ExpenseRepository;
import br.edu.ufrn.expensify.transaction.repository.IncomeRepository;
import br.edu.ufrn.expensify.transaction.repository.TransactionRepository;
import br.edu.ufrn.expensify.transaction.repository.TransferRepository;

@Service
public class TransactionService {
    
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final TransferRepository transferRepository;

    private final AccountService accountService;
    private final AuthService authService;


    public TransactionService(
        TransactionRepository transactionRepository,
        IncomeRepository incomeRepository,
        ExpenseRepository expenseRepository,
        TransferRepository transferRepository,
        AccountService accountService,
        AuthService authService
    ) {
        this.transactionRepository = transactionRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.transferRepository = transferRepository;
        this.accountService = accountService;
        this.authService = authService;
    }

    public List<Transaction> getAllTransactions() {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching all transactions for user: {}", user.getUsername());
    
        return transactionRepository.findAllByUser(user);
    }

    public Transaction getTransactionById(UUID id) {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching transaction with id: {} for user: {}", id, user.getUsername());
    
        return transactionRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + id + " for user: " + user.getUsername()));
    }

    public Transaction saveTransaction(Transaction transaction) {
        User user = authService.getAuthenticatedUser();
        
        transaction.setUser(user);

        logger.info("Saving transaction: {}", transaction);

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Transaction transaction) {
        logger.info("Updating transaction: {}", transaction);
    
        return transactionRepository.save(transaction);
    }

    public List<Income> getAllIncomes() {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching all incomes for user: {}", user.getUsername());
    
        return incomeRepository.findAllByUser(user);
    }

    public Income getIncomeById(UUID id) {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching income with id: {} for user: {}", id, user.getUsername());
    
        return incomeRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new TransactionNotFoundException("Income not found with id: " + id + " for user: " + user.getUsername()));
    }

    public Income saveIncome(Income income) {
        User user = authService.getAuthenticatedUser();

        income.setUser(user);

        logger.info("Saving income: {}", income);
        
        return incomeRepository.save(income);
    }

    public Income updateIncome(Income income) {
        logger.info("Updating income: {}", income);
        
        return incomeRepository.save(income);
    }

    public List<Expense> getAllExpenses() {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching all expenses for user: {}", user.getUsername());
    
        return expenseRepository.findAllByUser(user);
    }

    public Expense getExpenseById(UUID id) {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching expense with id: {} for user: {}", id, user.getUsername());
    
        return expenseRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new TransactionNotFoundException("Expense not found with id: " + id + " for user: " + user.getUsername()));
    }

    public Expense saveExpense(Expense expense) {
        User user = authService.getAuthenticatedUser();

        expense.setUser(user);

        logger.info("Saving expense: {}", expense);
        
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Expense expense) {
        logger.info("Updating expense: {}", expense);
        
        return expenseRepository.save(expense);
    }

    public List<Transfer> getAllTransfers() {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching all transfers for user: {}", user.getUsername());
    
        return transferRepository.findAllByUser(user);
    }

    public Transfer getTransferById(UUID id) {
        User user = authService.getAuthenticatedUser();

        logger.info("Fetching transfer with id: {} for user: {}", id, user.getUsername());
    
        return transferRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new TransactionNotFoundException("Transfer not found with id: " + id + " for user: " + user.getUsername()));
    }

    public Transfer saveTransfer(Transfer transfer) {
        User user = authService.getAuthenticatedUser();

        transfer.setUser(user);

        logger.info("Saving transfer: {}", transfer);
        
        return transferRepository.save(transfer);
    }

    public Transfer updateTransfer(Transfer transfer) {
        logger.info("Updating transfer: {}", transfer);
        
        return transferRepository.save(transfer);
    }

    public void createIncome(Income income) {
        saveIncome(income);
        accountService.increaseBalance(income.getAccount().getId(), income.getAmount());

        logger.info("Created income: {}", income);
    }

    // public void updateIncome(Income income) {};

    public void deleteIncome(Income income) {
        accountService.decreaseBalance(income.getAccount().getId(), income.getAmount());
        incomeRepository.delete(income);

        logger.info("Deleted income: {}", income);
    }

    public void createExpense(Expense expense) {
        saveExpense(expense);
        accountService.decreaseBalance(expense.getAccount().getId(), expense.getAmount());

        logger.info("Created expense: {}", expense);
    }

    // public void updateExpense(Expense expense) {};

    public void deleteExpense(Expense expense) {
        accountService.increaseBalance(expense.getAccount().getId(), expense.getAmount());
        expenseRepository.delete(expense);

        logger.info("Deleted expense: {}", expense);
    }

    public void createTransfer(Transfer transfer) {
        saveTransfer(transfer);
        accountService.decreaseBalance(transfer.getSourceAccount().getId(), transfer.getAmount());
        accountService.increaseBalance(transfer.getDestinationAccount().getId(), transfer.getAmount());

        logger.info("Created transfer: {}", transfer);
    }

    // public void updateTransfer(Transfer transfer) {};

    public void deleteTransfer(Transfer transfer) {
        accountService.increaseBalance(transfer.getSourceAccount().getId(), transfer.getAmount());
        accountService.decreaseBalance(transfer.getDestinationAccount().getId(), transfer.getAmount());
        transferRepository.delete(transfer);

        logger.info("Deleted transfer: {}", transfer);
    }

}
