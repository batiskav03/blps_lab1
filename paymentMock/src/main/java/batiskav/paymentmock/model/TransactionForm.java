package batiskav.paymentmock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TransactionForm {
    private UUID id;
}
