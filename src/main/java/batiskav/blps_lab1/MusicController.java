package batiskav.blps_lab1;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MusicController {

    private MusicService musicService;


    @QueryMapping
    public Music getMusicByID(int id) {
        return musicService.getMusicByID();
    }
}
