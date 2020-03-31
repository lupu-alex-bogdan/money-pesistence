package service;

import model.Person;
import model.Transaction;
import model.TransactionReport;
import model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PersonService personService;

    public Optional<Transaction> getTransaction(int id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(transactions::add);
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (personIsNotPersisted(transaction.getPayer().getCnp())) {
            personService.addPerson(transaction.getPayer());
        }
        if (!payerAndPayeeIsTheSamePerson(transaction.getPayer().getCnp(), transaction.getPayee().getCnp()) &&
            personIsNotPersisted(transaction.getPayee().getCnp())) {
            personService.addPerson(transaction.getPayee());
        }
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(int transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public TransactionReport getReportForCnp(long cnp) {
        Person person = personService.getPerson(cnp);
        if (person == null) {
            return null;
        } else {
            List<Transaction> transactions = transactionRepository.findTransactionsByPayer(person);
            TransactionReport transactionReport = new TransactionReport();
            transactionReport.setPerson(person);
            transactionReport.setIbanToIban(transactions.stream().filter(t -> TransactionType.IBAN_TO_IBAN.equals(t.getTransactionType())).collect(Collectors.toList()));
            transactionReport.setIbanToWallet(transactions.stream().filter(t -> TransactionType.IBAN_TO_WALLET.equals(t.getTransactionType())).collect(Collectors.toList()));
            transactionReport.setWalletToIban(transactions.stream().filter(t -> TransactionType.WALLET_TO_IBAN.equals(t.getTransactionType())).collect(Collectors.toList()));
            transactionReport.setWalletToWallet(transactions.stream().filter(t -> TransactionType.WALLET_TO_WALLET.equals(t.getTransactionType())).collect(Collectors.toList()));
            return transactionReport;
        }
    }

    private boolean personIsNotPersisted(long cnp) {
        return personService.getPerson(cnp) == null;
    }

    private boolean payerAndPayeeIsTheSamePerson(long payerCnp, long payeeCnp) {
        return payerCnp == payeeCnp;
    }
}
