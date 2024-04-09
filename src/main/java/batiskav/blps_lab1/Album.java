package batiskav.blps_lab1;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Album {
    @Id
    private int id;
    private String name;
    private String description;
}
