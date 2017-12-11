package com.golive.launch.repo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.golive.launch.entity.UserDetails;

@Repository
public class SaveUserInfo {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int saverUserInfo(UserDetails user) {

		SimpleJdbcCall s = new SimpleJdbcCall(jdbcTemplate);
		s.setProcedureName("userSignUp");
		Map<String, Object> res = s.execute(user.getUsername(), user.getPassword(), user.getName(), user.getCity(),
				user.getEmail());

		if (Integer.parseInt(res.get("#update-count-1").toString()) == 1)
			return 1;
		else
			return 0;
	}

}
