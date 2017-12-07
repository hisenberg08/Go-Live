package com.golive.launch.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepo {

	@Autowired
	private JdbcTemplate jdbctempplate;

}
