package pl.beling.konkurs.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Transaction
 */
public class TransactionDto {
    @JsonProperty("debitAccount")
    private String debitAccount;

    @JsonProperty("creditAccount")
    private String creditAccount;

    @JsonProperty("amount")
    private BigDecimal amount;

    public TransactionDto debitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
        return this;
    }

    /**
     * Get debitAccount
     *
     * @return debitAccount
     */
    @Size(min = 26, max = 26)
    public String getDebitAccount() {
        return debitAccount;
    }

    public TransactionDto creditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
        return this;
    }

    /**
     * Get creditAccount
     *
     * @return creditAccount
     */
    @Size(min = 26, max = 26)
    public String getCreditAccount() {
        return creditAccount;
    }

    public TransactionDto amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Get amount
     *
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransactionDto.class.getSimpleName() + "[", "]")
                .add("debitAccount='" + debitAccount + "'")
                .add("creditAccount='" + creditAccount + "'")
                .add("amount=" + amount)
                .toString();
    }

}

