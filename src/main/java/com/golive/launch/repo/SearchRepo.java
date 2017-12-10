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
public class SearchRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;

	public List<Map<Integer, List<String>>> searchAll(String searchFor, String userName) {
		List<Map<Integer, List<String>>> results = new ArrayList<Map<Integer, List<String>>>();

		String trackQuery = "select trackid, tracktitle, trackduration from track where tracktitle LIKE ?";
		String artistQuery = "select artistid, artistname, artistdesc from artist where artistname LIKE ?";
		String albumQuery = "select albumid, albumtitle, albumreleasedate from album where albumtitle LIKE ?";
		String usersQuery = "select u.username, u.name from userdata u where (u.username LIKE ? or u.name LIKE ?) and u.username != ?";

		Map<Integer, List<String>> trackResultMap = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> artistResultMap = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> albumResultMap = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> usersResultMap = new HashMap<Integer, List<String>>();

		List<Map<String, Object>> trackResults = jdbctempplate.queryForList(trackQuery, searchFor);
		List<Map<String, Object>> artistResults = jdbctempplate.queryForList(artistQuery, searchFor);
		List<Map<String, Object>> albumResults = jdbctempplate.queryForList(albumQuery, searchFor);
		List<Map<String, Object>> userResults = jdbctempplate.queryForList(usersQuery, searchFor, searchFor, userName);

		int i = 1;
		// mapping result from track fetch query into HashMap
		for (Map<String, Object> trackResult : trackResults) {
			List<String> trackRecord = new ArrayList<String>();
			trackRecord.add(trackResult.get("trackid").toString());
			trackRecord.add(trackResult.get("tracktitle").toString());
			String minutes = String.valueOf(
					TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(trackResult.get("trackduration").toString())));
			String seconds = String.valueOf(
					TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(trackResult.get("trackduration").toString())));
			trackRecord.add(minutes + " min " + seconds + " sec");
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

		i = 1;
		// mapping result from Users fetch query into HashMap
		for (Map<String, Object> userResult : userResults) {
			List<String> userRecord = new ArrayList<String>();
			userRecord.add(userResult.get("username").toString());
			userRecord.add(userResult.get("name").toString());
			usersResultMap.put(i, userRecord);
			i++;
		}

		results.add(trackResultMap);
		results.add(artistResultMap);
		results.add(albumResultMap);
		results.add(usersResultMap);
		return results;
	}

}
