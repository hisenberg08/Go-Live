package com.golive.launch.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArtistRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;

	private String getTracksforArtistQuery = "select t.trackid,t.tracktitle,t.trackduration from trackartist ta left join track t on ta.trackid = t.trackid where ta.artistid = ?";

	public Map<Integer, List<String>> getTracksforArtist(String artistId) {

		List<Map<String, Object>> artistTracks = jdbctempplate.queryForList(getTracksforArtistQuery, artistId);
		Map<Integer, List<String>> artistTrackMap = new HashMap<Integer, List<String>>();
		int i = 1;
		for (Map<String, Object> result : artistTracks) {
			List<String> artistTrackRecord = new ArrayList<String>();
			artistTrackRecord.add(result.get("tracktitle").toString());
			artistTrackRecord.add(result.get("trackduration").toString());
			artistTrackRecord.add(result.get("trackid").toString());
			artistTrackMap.put(i, artistTrackRecord);
			i++;
		}
		return artistTrackMap;
	}
}
