package sesrvices;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class PaymentBankAccount implements BankAccount{

    private BigDecimal balance = BigDecimal.ZERO;

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public void withdraw(BigDecimal amount) throws NoSuchElementException {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
        } else {
            throw new NoSuchElementException("Не хватает средств на счету");
        }
    }
}
