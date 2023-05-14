package pl.beling.konkurs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Account DTO - output
 */
public class AccountDto {
    @JsonProperty("account")
    private String account;

    @JsonProperty("debitCount")
    private AtomicInteger debitCount;

    @JsonProperty("creditCount")
    private AtomicInteger creditCount;

    @JsonProperty("balance")
    private BigDecimal balance;

    public AccountDto(String accountNumber) {
        setAccount(accountNumber);
        setDebitCount(0);
        setCreditCount(0);
        setBalance(new BigDecimal(0));
    }

    /**
     * Get account
     *
     * @return account
     */
    @Size(min = 26, max = 26)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Number of debit transactions
     *
     * @return debitCount
     */
    public Integer getDebitCount() {
        return debitCount.get();
    }

    public void setDebitCount(Integer debitCount) {
        this.debitCount = new AtomicInteger(debitCount);
    }

    /**
     * Number of credit transactions
     *
     * @return creditCount
     */
    public Integer getCreditCount() {
        return creditCount.get();
    }

    public void setCreditCount(Integer creditCount) {
        this.creditCount = new AtomicInteger(creditCount);
    }

    /**
     * Get balance
     *
     * @return balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountDto.class.getSimpleName() + "[", "]")
                .add("account='" + account + "'")
                .add("debitCount=" + debitCount)
                .add("creditCount=" + creditCount)
                .add("balance=" + balance)
                .toString();
    }

    public synchronized void subtractBalance(BigDecimal amount) {
        debitCount.incrementAndGet();
        balance = balance.subtract(amount);
    }

    public synchronized void addBalance(BigDecimal amount) {
        creditCount.incrementAndGet();
        balance = balance.add(amount);
    }
}

