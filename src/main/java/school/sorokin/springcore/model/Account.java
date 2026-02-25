package school.sorokin.springcore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.util.Map;
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "money_amount")
    private BigDecimal moneyAmount;

    public Account() {
    }



    public Account(Long id, User user, BigDecimal moneyAmount) {
        this.id = id;
        this.user = user;
        this.moneyAmount = moneyAmount;
    }


    public Long getId() {
        return id;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public User getUser() {
        return user;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
