package batiskav.blps_lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TransactionFormRepresentation {
    private UUID uuid;
}
