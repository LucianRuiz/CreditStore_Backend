package com.creditstore.CreditStore.accounts.rest;

import com.creditstore.CreditStore.accounts.entity.Account;
import com.creditstore.CreditStore.accounts.model.AccountRequest;
import com.creditstore.CreditStore.accounts.model.AccountResponse;
import com.creditstore.CreditStore.accounts.model.PayRequest;
import com.creditstore.CreditStore.accounts.model.PayResponse;
import com.creditstore.CreditStore.accounts.service.AccountService;
import com.creditstore.CreditStore.accounts.service.PayService;
import com.creditstore.CreditStore.clients.model.ClientDto;
import com.creditstore.CreditStore.shared.formulas.DatosSalida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountRest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PayService payService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest accountRequest) {
        AccountResponse newAccount = accountService.create(accountRequest);
        return ResponseEntity.ok(newAccount);
    }

    @GetMapping("/clients/{clientId}/accounts")
    public ResponseEntity<List<Account>> getAllByClientId(@PathVariable UUID clientId) {
        List<Account> accounts = accountService.getAll(clientId);
        return ResponseEntity.ok(accounts);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Integer id, @RequestBody AccountRequest accountRequest) {
        AccountResponse updatedAccount = accountService.update(id, accountRequest);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // agregar pagos
    @PostMapping("/{id}/pays")
    public ResponseEntity<PayResponse> createPay(@PathVariable Integer id, @RequestBody PayRequest payRequest) {
        PayResponse newPay = payService.create(payRequest, id);
        return ResponseEntity.ok(newPay);
    }

    @GetMapping("/{id}/pays")
    public ResponseEntity<List<PayResponse>> getAllPays(@PathVariable Integer id) {
        List<PayResponse> pays = payService.getAllByAccountId(id);
        return ResponseEntity.ok(pays);
    }

    // Nuevo endpoint para obtener la deuda del cliente
    @GetMapping("/{id}/credit-debt")
    public ResponseEntity<Double> get(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.getAccountDebt(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Integer id) {
        AccountResponse account = accountService.getById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}/interes-moratorio")
    public ResponseEntity<List<DatosSalida>> calcularInteresMoratorio(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPagoReal) {
        List<DatosSalida> resultado = accountService.calcularInteresMoratorio(id, fechaPagoReal);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}/total-interes-moratorio")
    public ResponseEntity<Double> getTotalInteresMoratorio(@PathVariable Integer id) {
        double totalInteresMoratorio = accountService.calculateTotalInteresMoratorio(id);
        return ResponseEntity.ok(totalInteresMoratorio);
    }
}