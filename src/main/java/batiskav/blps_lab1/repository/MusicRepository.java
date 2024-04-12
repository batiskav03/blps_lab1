package batiskav.blps_lab1.repository;

import batiskav.blps_lab1.model.Album;
import batiskav.blps_lab1.model.Music;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MusicRepository {


    private JdbcTemplate jdbcTemplate;

    public MusicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Music getByID(int id) {
        final String QUERY = """
               select * from blps.music
                    inner join blps.album on blps.music.album_id = blps.album.album_id
               where music_id = ?""";

        return jdbcTemplate.queryForObject(QUERY, (rs, rowNum) -> new Music(rs.getInt(1),
                rs.getString(2), rs.getString(3),
                rs.getInt(4),
                new Album(rs.getInt(7), rs.getString(8)), rs.getString(6)), id);

    }

}
