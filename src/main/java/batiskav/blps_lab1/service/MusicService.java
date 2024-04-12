package batiskav.blps_lab1.service;

import batiskav.blps_lab1.repository.MusicRepository;
import batiskav.blps_lab1.model.Music;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MusicService {

    private MusicRepository musicRepo;

    private final Path musicLocation = Paths.get("src/main/resources/media");

    public MusicService(MusicRepository musicRepo) {
        this.musicRepo = musicRepo;
    }

    public Music getMusicByID(int id)  {

        return musicRepo.getByID(id);
    }

//    public File getMusicMedia(int id) {
//        musicLocation.resolve(id + ".mp3");
//        try {
//            byte[] music = Files.readAllBytes(musicLocation);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Files.
//        return
//    }

    public Resource loadSongById(int id) {
        Music currentSong = getMusicByID(id);
        try {
            Path file = musicLocation.resolve(currentSong.getUrl());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                System.err.println("Could not read file: " + currentSong.getUrl());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
