package sesrvices;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public interface BankAccount {
    BigDecimal getBalance();
    void deposit(BigDecimal amount);
    void withdraw(BigDecimal amount) throws NoSuchElementException;
}
