package EWalletBetaApp.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity

@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String  houseNumber;
    private String street;
    private String lga;
    private String state;
//    @ManyToOne
//    @JoinColumn(name = "walletId", nullable = false)
//    private Wallet wallet;


}
