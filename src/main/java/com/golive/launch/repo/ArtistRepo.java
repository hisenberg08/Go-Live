package com.golive.launch.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArtistRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;

	private String getTracksforArtistQuery = "select t.trackid,t.tracktitle,t.trackduration from trackartist ta left join track t on ta.trackid = t.trackid where ta.artistid = ?";
	private String getLikedArtistsQuery = "select ua.artistid from userlikeartist ua where ua.username=?";
	private String addLikeArtistQuery = "Insert into userlikeartist (username, artistid, likedtv) values (?,?,now()) on DUPLICATE KEY UPDATE likedtv=now()";
	private String removeLikeArtistQuery = "delete from userlikeartist where username=? and artistid=?";

	public Map<Integer, List<String>> getTracksforArtist(String artistId) {

		List<Map<String, Object>> artistTracks = jdbctempplate.queryForList(getTracksforArtistQuery, artistId);
		Map<Integer, List<String>> artistTrackMap = new HashMap<Integer, List<String>>();
		int i = 1;
		for (Map<String, Object> result : artistTracks) {
			List<String> artistTrackRecord = new ArrayList<String>();
			artistTrackRecord.add(result.get("tracktitle").toString());
			// artistTrackRecord.add(result.get("trackduration").toString());
			String minutes = String
					.valueOf(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(result.get("trackduration").toString())));
			String seconds = String
					.valueOf(TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(result.get("trackduration").toString())));
			artistTrackRecord.add(minutes + " min " + seconds + " sec");
			artistTrackRecord.add(result.get("trackid").toString());
			artistTrackMap.put(i, artistTrackRecord);
			i++;
		}
		return artistTrackMap;
	}

	public HashSet<String> getLikedArtists(String username) {
		List<Map<String, Object>> likedArtists = jdbctempplate.queryForList(getLikedArtistsQuery, username);
		HashSet<String> likedArtistsSet = new HashSet<String>();
		for (Map<String, Object> result : likedArtists) {
			likedArtistsSet.add(result.get("artistid").toString());
		}
		return likedArtistsSet;
	}

	public int addLikeArtist(String userName, String artistId) {
		int rate = 0;
		rate = jdbctempplate.update(addLikeArtistQuery, userName, artistId);
		if (rate == 1)
			return rate;
		else
			return 0;
	}

	public int removeLikeArtist(String userName, String artistId) {
		int rate = 0;
		rate = jdbctempplate.update(removeLikeArtistQuery, userName, artistId);
		if (rate == 1)
			return rate;
		else
			return 0;
	}
}
