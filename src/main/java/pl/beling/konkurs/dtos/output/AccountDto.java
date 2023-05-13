package pl.beling.konkurs.dtos.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * Account
 */
public class AccountDto {
    @JsonProperty("account")
    private String account;

    @JsonProperty("debitCount")
    private Integer debitCount;

    @JsonProperty("creditCount")
    private Integer creditCount;

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
        return debitCount;
    }

    public void setDebitCount(Integer debitCount) {
        this.debitCount = debitCount;
    }

    /**
     * Number of credit transactions
     *
     * @return creditCount
     */
    public Integer getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Integer creditCount) {
        this.creditCount = creditCount;
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

    public void subtractBalance(BigDecimal amount) {
        setDebitCount(getDebitCount() + 1);
        setBalance(getBalance().subtract(amount));
    }

    public void addBalance(BigDecimal amount) {
        setCreditCount(getCreditCount() + 1);
        setBalance(getBalance().add(amount));
    }
}

