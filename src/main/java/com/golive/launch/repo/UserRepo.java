package com.golive.launch.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;

	private String getFollowedUsersQuery = "select f.followeduser from follows f where f.followinguser = ?";
	private String followUserQuery = "insert into follows (followinguser, followeduser, followdtv) values (?, ?, now()) on DUPLICATE KEY UPDATE followdtv=now()";
	private String unFollowUserQuery = "delete from follows where followinguser = ? and followeduser = ?";

	public HashSet<String> getFollowedUsers(String username) {
		List<Map<String, Object>> followedUsers = jdbctempplate.queryForList(getFollowedUsersQuery, username);
		HashSet<String> followedUsersSet = new HashSet<String>();
		for (Map<String, Object> result : followedUsers) {
			followedUsersSet.add(result.get("followeduser").toString());
		}
		return followedUsersSet;
	}

	public int followUser(String userName, String toFollowUser) {
		int result = 0;
		result = jdbctempplate.update(followUserQuery, userName, toFollowUser);
		if (result == 1)
			return result;
		else
			return 0;
	}

	public int unfollowUser(String userName, String toUnFollowUser) {
		int result = 0;
		result = jdbctempplate.update(unFollowUserQuery, userName, toUnFollowUser);
		if (result == 1)
			return result;
		else
			return 0;
	}

	public Map<Integer, List<String>> getUserHistory(String username) {
		
		String lastFivePlayedTracks = "select t.tracktitle,p.playeddtv,t.trackid from plays p left join track t "
				+ "on t.trackid =p.trackid where p.username = ? order by p.playeddtv desc limit 10";
		
		List<Map<String, Object>> lastPlayedTracksresult = jdbctempplate.queryForList(lastFivePlayedTracks,username);
		Map<Integer, List<String>> historyMap = new HashMap<>();
		
		int i=1;
		for (Map<String, Object> result : lastPlayedTracksresult) {
			List<String> histList = new ArrayList<>();
			histList.add(result.get("tracktitle").toString());
			histList.add(result.get("playeddtv").toString());
			histList.add(result.get("trackid").toString());
			historyMap.put(i,histList);
			i++;
		}
		return historyMap;
	}

	public int userPlayTrack(String source, String user, String trackId, String playlistId) {
		
		String songPlayedQuery = "insert into plays (username,trackid,source,playeddtv) values (?,?,?,now())";
		int status = jdbctempplate.update(songPlayedQuery,user,trackId,source);
		
		if(source.equals("playlist")){
			String incPlaylistPlayedCount = "UPDATE playlisttrack SET playedcount = playedcount + 1 WHERE playlistid = ? and trackid = ?";
			if(jdbctempplate.update(incPlaylistPlayedCount,playlistId,trackId)>0)
				return 1;
			else
				return 0;
		}
		return status;
	}
}
