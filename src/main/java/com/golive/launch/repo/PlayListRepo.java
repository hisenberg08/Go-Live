package com.golive.launch.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlayListRepo {

	@Autowired
	private JdbcTemplate jdbctempplate; 
	
	public List<Map<Integer, String>> getUserPlayList(String userName){
		
		String getUserPlaylist = "select playlistname from playlist where playlistowner = ?";
		String getfollowedUserPlaylist = "select playlistname from playlist where playlistowner in"
				+ "(select followeduser from follows where followinguser = ? ) and playlisttype = 'public'";
		
		List<Map<Integer, String>> resultMap = new ArrayList<>();
		Map<Integer,String> userPlaylistMap =new HashMap<Integer,String>();
		Map<Integer,String> followedUserPlaylistMap =new HashMap<Integer,String>();
		
		
		List<Map<String, Object>> userPlaylist =  jdbctempplate.queryForList(getUserPlaylist,userName);
		List<Map<String, Object>> followedUserPlayliys =  jdbctempplate.queryForList(getfollowedUserPlaylist,userName);
		
		int i=1;
		for(Map<String, Object> result : userPlaylist){
			userPlaylistMap.put(i, result.get("playlistname").toString());
			i++;
		}

		int j=1;
		for(Map<String, Object> result : followedUserPlayliys){
			followedUserPlaylistMap.put(j, result.get("playlistname").toString());
			j++;
		}
		
		
		resultMap.add(userPlaylistMap);
		resultMap.add(followedUserPlaylistMap);
		
		return resultMap;
		
	}

	public int createNewPlayList(String userName,String playlistCategory, String playlistName) {
		
		String query ="insert into playlist (playlistname,playlistowner,playlisttype,playlistcreatedtv) values (?,?,?,now())"; 
		try{
			int status = jdbctempplate.update(query,playlistName,userName,playlistCategory);
			return status;	
		}catch(Exception e){
			return 0;
		}
		
		
	}
}
