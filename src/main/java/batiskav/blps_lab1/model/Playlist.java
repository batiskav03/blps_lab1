package batiskav.blps_lab1.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Playlist {
    @NonNull
    private int id;
    @NonNull
    private String Name;

    private String description;
    private List<Music> musicList;
}
