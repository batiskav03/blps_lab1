package batiskav.blps_lab1.service;

import batiskav.blps_lab1.model.Music;
import batiskav.blps_lab1.model.Playlist;
import batiskav.blps_lab1.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private PlaylistRepository playlistRepo;

    public PlaylistService(PlaylistRepository playlistRepo) {
        this.playlistRepo = playlistRepo;
    }

    public Playlist getPlaylist(int id) {
        return playlistRepo.findPlaylistById(id);
    }


    public List<Music> getPlaylistTrackList(Playlist pl) {
        return playlistRepo.trackListByPlaylistId(pl);
    }
}
