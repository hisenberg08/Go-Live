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

	public Map<Integer, List<String>> searchAll(String searchFor) {

		String query = "select trackid, tracktitle, trackduration, trackreleasedate from track where tracktitle LIKE ?";
		Map<Integer, List<String>> resultMap = new HashMap<Integer, List<String>>();

		List<Map<String, Object>> res = jdbctempplate.queryForList(query, searchFor);
		int i = 1;

		for (Map<String, Object> result : res) {
			List<String> record = new ArrayList<String>();
			record.add(result.get("trackid").toString());
			record.add(result.get("tracktitle").toString());
			record.add(result.get("trackduration").toString());
			record.add(result.get("trackreleasedate").toString());
			resultMap.put(i, record);
			i++;
		}
		return resultMap;
	}

}
