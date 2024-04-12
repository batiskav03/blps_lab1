package batiskav.blps_lab1.controller;

import batiskav.blps_lab1.model.Music;
import batiskav.blps_lab1.model.Playlist;
import batiskav.blps_lab1.service.PlaylistService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlaylistController {

    private PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @QueryMapping
    public Playlist getPlaylist(@Argument int id) {
        return playlistService.getPlaylist(id);
    }

    @SchemaMapping
    public List<Music> trackList(Playlist pl) {
        return playlistService.getPlaylistTrackList(pl);
    }
}
