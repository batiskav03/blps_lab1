package batiskav.blps_lab1.repository;

import batiskav.blps_lab1.model.Album;
import batiskav.blps_lab1.model.Author;
import batiskav.blps_lab1.model.Music;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MusicDao {


    private final JdbcTemplate jdbcTemplate;

    public MusicDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Music getByID(int id) {
        final String QUERY = """
               select * from blps.music
                    inner join blps.album on blps.music.album_id = blps.album.album_id
                    inner join blps.author on blps.author.author_id = blps.album.author_id
               where music_id = ?""";

        return jdbcTemplate.queryForObject(QUERY, (rs, rowNum) -> new Music(rs.getInt(1),
                rs.getString(2), rs.getString(3),
                rs.getInt(4),
                new Album(rs.getInt(7), rs.getString(8),
                        new Author(rs.getInt(10), rs.getString(11))),
                rs.getString(6)), id);

    }

    @Transactional
    public boolean updateAuditionNumberById(int id, int number) {
        final String QUERY = """
                    update blps.music set aud_num = ?
                        where music_id = ?
                """;
        int rows = jdbcTemplate.update(QUERY, number, id);

        return rows != 0;
    }
}
