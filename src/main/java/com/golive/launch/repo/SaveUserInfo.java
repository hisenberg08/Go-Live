package com.golive.launch.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.golive.launch.entity.UserDetails;

@Repository
public class SaveUserInfo {

	private String insert_userDetails = "insert into userdata (username,password,name,city,email,signupdate) values (?,?,?,?,?,now())";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int saverUserInfo(UserDetails user){
	
		int updateUserDetail = jdbcTemplate.update(insert_userDetails,user.getUsername(),user.getPassword(),user.getName(),user.getCity(),user.getEmail());
		if(updateUserDetail ==1)
			return updateUserDetail;
		else
			return 0;
	}
	
	
	
}
