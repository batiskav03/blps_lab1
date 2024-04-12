package batiskav.blps_lab1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepo;

    private final Path musicLocation = Paths.get("src/main/resources/media");

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
            Path file = musicLocation.resolve(currentSong.getFilename());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                System.err.println("Could not read file: " + currentSong.getFilename());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
