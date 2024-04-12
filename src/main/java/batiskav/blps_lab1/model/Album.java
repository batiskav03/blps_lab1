package batiskav.blps_lab1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Album {
    @NonNull
    private int id;
    @NonNull
    private String name;
    private Author author;
}
