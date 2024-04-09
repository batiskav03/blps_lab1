package batiskav.blps_lab1;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Music {
    @Id
    private int id;
    private String name;
    private String year;
    private int audition_number;
    private Album album;

}
