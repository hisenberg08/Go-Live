package com.golive.launch.repo;

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
}
