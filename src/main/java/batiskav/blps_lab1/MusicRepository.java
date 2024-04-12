package batiskav.blps_lab1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MusicRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
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
