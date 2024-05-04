package batiskav.paymentmock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class TransactionForm {
    @NonNull
    private UUID id;
    private String name;
    private String cvv;
}
