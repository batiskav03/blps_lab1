package batiskav.blps_lab1.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Music {
    @NonNull
    private int id;
    @NonNull
    private String name;
    private String year;
    @NonNull
    private int auditionNumber;
    private Album album;
    @NonNull
    private String url;

}
