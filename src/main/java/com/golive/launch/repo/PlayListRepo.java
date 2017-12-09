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
	
	public List<Map<Integer, List<String>>> getUserPlayList(String userName){
		
		String getUserPlaylist = "select playlistname,playlistid from playlist where playlistowner = ?";
		String getfollowedUserPlaylist = "select playlistname,playlistid,playlistowner from playlist where playlistowner in"
				+ "(select followeduser from follows where followinguser = ? ) and playlisttype = 'public'";
		
		List<Map<Integer, List<String>>> resultMap = new ArrayList<>();
		Map<Integer, List<String>> userPlaylistMap =new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> followedUserPlaylistMap =new HashMap<Integer, List<String>>();
		
		
		List<Map<String, Object>> userPlaylist =  jdbctempplate.queryForList(getUserPlaylist,userName);
		List<Map<String, Object>> followedUserPlayliys =  jdbctempplate.queryForList(getfollowedUserPlaylist,userName);
		
		int i=1;
		for(Map<String, Object> result : userPlaylist){
			List<String> userPlaylistRecord = new ArrayList<String>();
			userPlaylistRecord.add(result.get("playlistname").toString());
			userPlaylistRecord.add(result.get("playlistid").toString());
			userPlaylistMap.put(i,userPlaylistRecord);
			i++;
		}

		int j=1;
		for(Map<String, Object> result : followedUserPlayliys){
			List<String> followedUserPlaylistRecord = new ArrayList<String>();
			followedUserPlaylistRecord.add(result.get("playlistname").toString());
			followedUserPlaylistRecord.add(result.get("playlistid").toString());
			followedUserPlaylistRecord.add(result.get("playlistowner").toString());
			followedUserPlaylistMap.put(j, followedUserPlaylistRecord);
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

	public Map<Integer, List<String>> getTracksforPlaylist(int playlistId, String playlistOwner) {
		
		String getTrackForPlayList = "select t.trackid,t.tracktitle,t.trackduration from playlisttrack p left join track t "
				+ "on p.trackid = t.trackid where playlistid = ?";
		
		List<Map<String, Object>> playlistTrack =  jdbctempplate.queryForList(getTrackForPlayList,playlistId);
		Map<Integer, List<String>> playlsitTrackMap =new HashMap<Integer, List<String>>();
		int i=1;
		for(Map<String, Object> result : playlistTrack){
			List<String> userPlaylistRecord = new ArrayList<String>();
			userPlaylistRecord.add(result.get("tracktitle").toString());
			userPlaylistRecord.add(result.get("trackduration").toString());
			userPlaylistRecord.add(result.get("trackid").toString());
			playlsitTrackMap.put(i,userPlaylistRecord);
			i++;
		}
		return playlsitTrackMap;
		
	}

	public void deleteTrackFromPlaylist(int playlistId, String trackid) {
		
		String query = "delete from playlisttrack where playlistid = ? and trackid =?";
		jdbctempplate.update(query,playlistId,trackid);
	}
}
