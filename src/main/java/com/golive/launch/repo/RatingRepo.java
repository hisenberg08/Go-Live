package com.golive.launch.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;
	private String ratingInsertQuery = "Insert into userratetrack (username, trackid, trackrating, ratingdtv) values (?,?,?,now())";

	public int insertRating(String userName, String trackId, String rating) {
		int rate = jdbctempplate.update(ratingInsertQuery, userName, trackId, rating);
		if (rate == 1)
			return rate;
		else
			return 0;
	}

}
