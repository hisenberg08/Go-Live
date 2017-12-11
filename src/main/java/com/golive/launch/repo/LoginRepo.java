package com.golive.launch.repo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepo {

	private String validateUserNew = "select username from userdata where username = ? and password = PASSWORD(?);";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean validateUser(String userName, String pass) {

		Boolean validate = false;
		List<Map<String, Object>> user = jdbcTemplate.queryForList(validateUserNew, userName, pass);
		if (user != null && !user.isEmpty()) {
			for (Map<String, Object> userDetail : user) {
				if (userDetail.get("username").toString().equals(userName))
					validate = true;
			}

		}
		return validate;
	}

}
