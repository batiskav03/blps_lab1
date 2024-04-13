package batiskav.blps_lab1.controller;

import batiskav.blps_lab1.service.MusicService;
import batiskav.blps_lab1.model.Music;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MusicController {

    private MusicService musicService;


    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }


    @QueryMapping
    public Music getMusicByID(@Argument int id) {
        return musicService.getMusicByID(id);
    }
}
