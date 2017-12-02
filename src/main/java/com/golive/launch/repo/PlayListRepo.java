package com.golive.launch.repo;

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
	
	public Map<Integer, String> getUserPlayList(String userName){
		
		String query = "select playlistname from playlist where playlistowner = ?";
		Map<Integer,String> resultMap =new HashMap<Integer,String>();
		
		List<Map<String, Object>> res =  jdbctempplate.queryForList(query,userName);
		int i=1;
		for(Map<String, Object> result : res){
			resultMap.put(i, result.get("playlistname").toString());
			i++;
		}
		
		return resultMap;
		
	}
}
