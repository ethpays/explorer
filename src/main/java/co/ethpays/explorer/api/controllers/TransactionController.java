package co.ethpays.explorer.api.controllers;

import co.ethpays.explorer.entity.Transaction;
import co.ethpays.explorer.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionRepository transactionRepository;

    @GetMapping(value = "/{txid}")
    public Map<String, String> getTransaction(@PathVariable String txid) {
        Transaction transaction = transactionRepository.findByTransactionId(txid);
        if (transaction == null) {
            return Map.of("error", "Transaction not found", "ok", "false", "txid", txid);
        } else {
            Map<String, String> data = new LinkedHashMap<>();
            data.put("ok", "true");
            data.put("txid", transaction.getTransactionId());
            data.put("amount", String.valueOf(transaction.getAmount()));
            data.put("currency", transaction.getCurrency());
            data.put("from", transaction.getFromWallet());
            data.put("to", transaction.getToWallet());
            data.put("status", transaction.getStatus());
            data.put("timestamp", String.valueOf(transaction.getCreatedAt().getTime()));
            return data;
        }
    }

    @GetMapping(value = "/latest7")
    public List<Transaction> getLatest7Transactions() {
        return transactionRepository.findByStatusOrderByCreatedAtDesc("completed", PageRequest.of(0, 7));
    }
}
