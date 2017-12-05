package com.golive.launch.cont;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.golive.launch.entity.UserDetails;
import com.golive.launch.repo.LoginRepo;
import com.golive.launch.repo.PlayListRepo;
import com.golive.launch.repo.SaveUserInfo;
import com.golive.launch.repo.SearchRepo;

@Controller
@ComponentScan(basePackages = "com.golive.launch.view")
public class SimpleController {

	@Autowired
	public SaveUserInfo userInfo;

	@Autowired
	public LoginRepo userLogin;

	@Autowired
	PlayListRepo playList;

	@Autowired
	SearchRepo search;

	@RequestMapping("/login")
	public String test() {
		return "login";
	}

	public List<String> activeUsers = new ArrayList<String>();
	// public Map<String,String> activeUsers = new HashMap<>();

	@RequestMapping("/verifyUser")
	public String verify(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		boolean authentication = userLogin.validateUser(userName, password);
		if (authentication) {

			model.put("user", userName);
			activeUsers.add(userName);
			return "welcome";
		} else
			return "login";
	}

	@RequestMapping("/saveUserInfo")
	public String saveUserInfo(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String city = request.getParameter("city");
		String email = request.getParameter("email");

		UserDetails user = new UserDetails();
		user.setCity(city);
		user.setEmail(email);
		user.setName(name);
		user.setUsername(userName);
		user.setPassword(password);

		int updateStatus = userInfo.saverUserInfo(user);
		if (updateStatus == 1)
			return "login";
		else
			return "errorPage";
	}

	@RequestMapping("/signup")
	public String signUp(HttpServletRequest request, HttpServletResponse response) {
		return "signup";
	}

	@RequestMapping("/playlist")
	public String playlist(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userName = request.getParameter("hidden");
		if (activeUsers.contains(userName)) {
			model.put("playListData", playList.getUserPlayList(userName));
			model.put("user", userName);
			return "playlist";
		} else {
			return "errorPage";
		}
	}

	@RequestMapping("/search")
	public String search(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userName = request.getParameter("hidden");
		String searchFor = request.getParameter("searchInput");
		if (activeUsers.contains(userName)) {
			model.put("searchData", search.searchAll("%" + searchFor + "%"));
			model.put("user", userName);
			return "search";
		} else {
			return "errorPage";
		}
	}

	@RequestMapping("/home")
	public String returnHome(HttpServletRequest request, Map<String, Object> model) {
		String userName = request.getParameter("hidden");
		if (activeUsers.contains(userName)) {
			model.put("user", userName);
			return "welcome";
		} else
			return "errorPage";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		String userName = request.getParameter("hidden");
		if (activeUsers.contains(userName)) {
			activeUsers.remove(userName);
			return "login";
		} else
			return "errorPage";
	}

}
