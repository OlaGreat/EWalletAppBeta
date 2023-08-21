package EWalletBetaApp.data.models;

import EWalletBetaApp.dto.response.TransactionResponse;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "wallets")
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long walletId;

    private String firstName;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String userName;

    @OneToOne
    private Address address;

    private BigDecimal balance;

    @Column(unique = true)
    private String phoneNumber;

    private String accountNumber;
    @Column(nullable = false)
    private String pin;
    @Column(nullable = false)
    private String passWord;
    @OneToMany(mappedBy = "walletId", cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<Transaction> transactions = new ArrayList<>();

//    @ElementCollection
//    private List<TransactionResponse> transactionResponse;


}
