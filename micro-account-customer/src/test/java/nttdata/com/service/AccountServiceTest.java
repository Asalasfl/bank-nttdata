package  nttdata.com.service;

import nttdata.com.model.Account;
import nttdata.com.repository.AccountRepository;
import nttdata.com.repository.TransactionRepository;
import nttdata.com.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAccountById() {
        // Arrange
        Account account = new Account();
        account.setId("accountId");
        account.setType("saving");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findById(any(String.class))).thenReturn(Mono.just(account));

        // Act & Assert
        accountService.getAccountById("accountId")
                .as(StepVerifier::create)
                .expectNextMatches(accountDTO -> {
                    // Add assertions for expected values in the AccountDTO object
                    return accountDTO.getIdAccount().equals(account.getId())
                            && accountDTO.getType().equals(account.getType())
                            && accountDTO.getBalance().equals(account.getBalance());
                })
                .verifyComplete();
    }

}
