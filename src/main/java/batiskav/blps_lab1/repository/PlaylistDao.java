package batiskav.blps_lab1.repository;

import batiskav.blps_lab1.model.Music;
import batiskav.blps_lab1.model.Playlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaylistDao {

    private final JdbcTemplate jdbcTemplate;

    public PlaylistDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Playlist> findAll() {
        final String QUERY = "select id, name from blps.playlist";
        return jdbcTemplate.query(QUERY,
                (rs, rowNum) -> new Playlist(rs.getInt(1),
                        rs.getString(2))
        );
    }

    public Playlist findPlaylistById(int id) {
        final String QUERY = """
                select playlist.pl_id, playlist.name from blps.playlist
                    where playlist.pl_id = ?
                """;

        return jdbcTemplate.queryForObject(QUERY,
                (rs, rowNum) -> new Playlist(rs.getInt(1), rs.getString(2)), id);
    }

    public List<Music> trackListByPlaylistId(Playlist pl) {
        final String QUERY = """
                    select music.music_id, music.name, music.aud_num, music.url\s
                                from blps.playlist
                            join blps.playlist_to_music on blps.playlist_to_music.pl_id = blps.playlist.pl_id
                            left join blps.music on blps.music.music_id = blps.playlist_to_music.music_id
                            where blps.playlist.pl_id = ?
                """;

        return jdbcTemplate.query(QUERY,
                (rs, rowNum) ->  new Music(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)), pl.getId());
    }



}
