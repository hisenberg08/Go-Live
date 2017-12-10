package com.golive.launch.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;

	private String getTracksforAlbumQuery = "select t.albumpos,t.trackid,t.tracktitle,t.trackduration from track t where t.albumid = ? ORDER BY t.albumpos ASC";
	private String updateAlbumViews = "UPDATE album SET albumviews = albumviews + 1 WHERE albumid = ?";
	
	public Map<Integer, List<String>> getTracksforAlbum(String albumId) {

		jdbctempplate.update(updateAlbumViews,albumId);
		
		List<Map<String, Object>> albumTracks = jdbctempplate.queryForList(getTracksforAlbumQuery, albumId);
		Map<Integer, List<String>> albumTrackMap = new HashMap<Integer, List<String>>();
		int i = 1;
		for (Map<String, Object> result : albumTracks) {
			List<String> albumTrackRecord = new ArrayList<String>();
			albumTrackRecord.add(result.get("tracktitle").toString());
			// albumTrackRecord.add(result.get("trackduration").toString());
			String minutes = String
					.valueOf(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(result.get("trackduration").toString())));
			String seconds = String
					.valueOf(TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(result.get("trackduration").toString())));
			albumTrackRecord.add(minutes + " min " + seconds + " sec");
			albumTrackRecord.add(result.get("trackid").toString());
			albumTrackRecord.add(result.get("albumpos").toString());
			albumTrackMap.put(i, albumTrackRecord);
			i++;
		}
		return albumTrackMap;
	}
}
