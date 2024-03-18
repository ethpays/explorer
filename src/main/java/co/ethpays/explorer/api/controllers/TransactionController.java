package co.ethpays.explorer.api.controllers;

import co.ethpays.explorer.entity.Balance;
import co.ethpays.explorer.entity.SimpleTransactionDto;
import co.ethpays.explorer.entity.Transaction;
import co.ethpays.explorer.entity.TransactionOverviewDto;
import co.ethpays.explorer.repositories.BalanceRepository;
import co.ethpays.explorer.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;

    @GetMapping(value = "/{txid}")
    public Map<String, String> getTransaction(@PathVariable String txid) {
        Transaction transaction = transactionRepository.findByTransactionId(txid);
        if (transaction == null) {
            return Map.of("error", "Transaction not found", "ok", "false", "txid", txid);
        } else {
            Balance fromAccount = balanceRepository.findByAddress(transaction.getFromWallet());
            Balance toAccount = balanceRepository.findByAddress(transaction.getToWallet());
            Map<String, String> data = new LinkedHashMap<>();
            data.put("ok", "true");
            data.put("txid", transaction.getTransactionId());
            data.put("amount", String.valueOf(transaction.getAmount()));
            data.put("currency", transaction.getCurrency());
            data.put("from", transaction.getFromWallet());
            if(fromAccount.isEthpaysAccount()) {
                data.put("from_ethpays_username", fromAccount.getUsername());
            }
            if (toAccount.isEthpaysAccount()) {
                data.put("to_ethpays_username", toAccount.getUsername());
            }
            data.put("to", transaction.getToWallet());
            data.put("status", transaction.getStatus());
            data.put("timestamp", String.valueOf(transaction.getCreatedAt().getTime()));
            return data;
        }
    }

    @GetMapping(value = "/latest7")
    public List<TransactionOverviewDto> getLatest7Transactions() {
        List<Transaction> transactions = transactionRepository.findByStatusOrderByCreatedAtDesc("completed", PageRequest.of(0, 7));
        List <TransactionOverviewDto> transactionOverviewDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionOverviewDto newTrans = new TransactionOverviewDto();
            Balance fromAccount = balanceRepository.findByAddress(transaction.getFromWallet());
            Balance toAccount = balanceRepository.findByAddress(transaction.getToWallet());
            newTrans.setTransactionId(transaction.getTransactionId());
            newTrans.setAmount(transaction.getAmount());
            newTrans.setCurrency(transaction.getCurrency());
            newTrans.setFromWallet(transaction.getFromWallet());
            newTrans.setToWallet(transaction.getToWallet());
            newTrans.setStatus(transaction.getStatus());
            newTrans.setCreatedAt(transaction.getCreatedAt());
            newTrans.setFromIsEthpaysAccount(fromAccount.isEthpaysAccount());
            newTrans.setToIsEthpaysAccount(toAccount.isEthpaysAccount());
            if (fromAccount.isEthpaysAccount()) {
                newTrans.setFromEthpaysUsername(fromAccount.getUsername());
            }
            if (toAccount.isEthpaysAccount()) {
                newTrans.setToEthpaysUsername(toAccount.getUsername());
            }
            transactionOverviewDtos.add(newTrans);
        }
        return transactionOverviewDtos;
    }

    @GetMapping(value = "/ethpays/top100")
    public List<SimpleTransactionDto> getTop100EthpaysTransactions() {
        List<Transaction> transactions =  transactionRepository.findByIsEthpaysTransactionIsTrueOrderByCreatedAtDesc(PageRequest.of(0, 100));
        List<SimpleTransactionDto> simpleTransactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            SimpleTransactionDto newTrans = new SimpleTransactionDto();
            newTrans.setTransactionId(transaction.getTransactionId());
            newTrans.setAmount(transaction.getAmount());
            newTrans.setCurrency(transaction.getCurrency());
            simpleTransactionDtos.add(newTrans);
        }
        return simpleTransactionDtos;
    }
}
