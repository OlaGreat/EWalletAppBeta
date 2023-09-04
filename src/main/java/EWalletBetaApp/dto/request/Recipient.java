package EWalletBetaApp.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class Recipient {
    private String name;
    @NonNull
    private String email;
}
