package batiskav.blps_lab1;

import org.springframework.stereotype.Service;

@Service
public class MusicService {

    private MusicRepository musicRepo;

    public Music getMusicByID(int id) {

        return musicRepo.getByID();
    }
}
