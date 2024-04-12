package batiskav.blps_lab1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Music {

    private int id;
    private String name;
    private String year;
    private int audition_number;
    private Album album;
    private String filename;

}
