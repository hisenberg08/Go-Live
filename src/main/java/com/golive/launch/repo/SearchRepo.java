package com.golive.launch.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SearchRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;

	public List<Map<Integer, List<String>>> searchAll(String searchFor) {
		List<Map<Integer, List<String>>> results = new ArrayList<Map<Integer, List<String>>>();

		String trackQuery = "select trackid, tracktitle, trackduration, trackreleasedate from track where tracktitle LIKE ?";
		String artistQuery = "select artistid, artistname, artistdesc from artist where artistname LIKE ?";
		String albumQuery = "select albumid, albumtitle, albumreleasedate from album where albumtitle LIKE ?";

		Map<Integer, List<String>> trackResultMap = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> artistResultMap = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> albumResultMap = new HashMap<Integer, List<String>>();

		List<Map<String, Object>> trackResults = jdbctempplate.queryForList(trackQuery, searchFor);
		List<Map<String, Object>> artistResults = jdbctempplate.queryForList(artistQuery, searchFor);
		List<Map<String, Object>> albumResults = jdbctempplate.queryForList(albumQuery, searchFor);

		int i = 1;
		// mapping result from track fetch query into HashMap
		for (Map<String, Object> trackResult : trackResults) {
			List<String> trackRecord = new ArrayList<String>();
			trackRecord.add(trackResult.get("trackid").toString());
			trackRecord.add(trackResult.get("tracktitle").toString());
			trackRecord.add(trackResult.get("trackduration").toString());
			trackRecord.add(trackResult.get("trackreleasedate").toString());
			trackResultMap.put(i, trackRecord);
			i++;
		}

		i = 1;
		// mapping result from artist fetch query into HashMap
		for (Map<String, Object> artistResult : artistResults) {
			List<String> artistRecord = new ArrayList<String>();
			artistRecord.add(artistResult.get("artistid").toString());
			artistRecord.add(artistResult.get("artistname").toString());
			artistRecord.add(artistResult.get("artistdesc").toString());
			artistResultMap.put(i, artistRecord);
			i++;
		}

		i = 1;
		// mapping result from artist fetch query into HashMap
		for (Map<String, Object> albumResult : albumResults) {
			List<String> albumRecord = new ArrayList<String>();
			albumRecord.add(albumResult.get("albumid").toString());
			albumRecord.add(albumResult.get("albumtitle").toString());
			albumRecord.add(albumResult.get("albumreleasedate").toString());
			albumResultMap.put(i, albumRecord);
			i++;
		}

		results.add(trackResultMap);
		results.add(artistResultMap);
		results.add(albumResultMap);
		return results;
	}

}
