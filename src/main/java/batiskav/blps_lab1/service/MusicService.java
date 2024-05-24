package batiskav.blps_lab1.service;

import batiskav.blps_lab1.repository.MusicDao;
import batiskav.blps_lab1.model.Music;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    private final MusicDao musicRepo;

    private final S3Service s3client;
    private final SubscribeService subscribeService;
    private final String bucketName;

    public MusicService(MusicDao musicRepo, S3Service s3client,
                        @Value("${api.bucketName}") String bucketName, SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
        this.musicRepo = musicRepo;
        this.s3client = s3client;
        this.bucketName = bucketName;
    }

    public Music getMusicByID(int id)  {
        Music song = musicRepo.getByID(id);
        String url = s3client.createTemporaryURL(bucketName, song.getUrl());
        if (subscribeService.checkSubscribeDate()) {
            song.setUrl(url);
            increaseAuditionNumber(id, song.getAuditionNumber());
        }
        return song;
    }

    private boolean increaseAuditionNumber(int id, int number) {
        return musicRepo.updateAuditionNumberById(id, number + 1);
    }




}
