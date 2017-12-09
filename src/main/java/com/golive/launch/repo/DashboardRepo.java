package com.golive.launch.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepo {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	public Map<String, List<String>> getNewsFeed() {
		
		Map<String,List<String>> newsFeed = new HashMap<>();
		List<String> topAtristList = new ArrayList<>();
		List<String> latestTracks = new ArrayList<>();
		List<String> latestAlbums = new ArrayList<>();
		List<String> highestRatedTrack = new ArrayList<>();
		
		String getTopArtist = "select a.artistname from userlikeartist u left join artist a"
				+ " on u.artistid =a.artistid group by a.artistid order by count(*) desc limit 3";
		
		String latestTrack = "select tracktitle from track order by trackreleasedate desc limit 3";
		
		String latestAlbum = "select albumtitle from album order by albumreleasedate desc limit 3";
		
		String higestRatedTracks = "select t.tracktitle from userratetrack ut left join track t on t.trackid = ut.trackid "
				+ "group by ut.trackid order by (sum(ut.trackrating)/count(*)) desc limit 3";
		
		
		//top artist
		List<Map<String, Object>> topArtist = jdbctemplate.queryForList(getTopArtist);
		for (Map<String, Object> result : topArtist) {
			topAtristList.add(result.get("artistname").toString());
		}
		newsFeed.put("topArtist", topAtristList);
		
		//latest tracks
		
		List<Map<String, Object>> recentTracks = jdbctemplate.queryForList(latestTrack);
		for (Map<String, Object> result : recentTracks) {
			latestTracks.add(result.get("tracktitle").toString());
		}
		newsFeed.put("latestTrack", latestTracks);
		
		//latest album
		List<Map<String, Object>> recentAlbums = jdbctemplate.queryForList(latestAlbum);
		for (Map<String, Object> result : recentAlbums) {
			latestAlbums.add(result.get("albumtitle").toString());
		}
		newsFeed.put("latestAlbum", latestAlbums);
		
		//highest rated track
		List<Map<String, Object>> highestRatedTracks = jdbctemplate.queryForList(higestRatedTracks);
		for (Map<String, Object> result : highestRatedTracks) {
			highestRatedTrack.add(result.get("tracktitle").toString());
		}
		newsFeed.put("tracktitle", highestRatedTrack);
		
		return newsFeed;
		
	}

}
