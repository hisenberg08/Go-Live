package com.golive.launch.cont;

//testing
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
import com.golive.launch.repo.ArtistRepo;
import com.golive.launch.repo.DashboardRepo;
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
	ArtistRepo artistRepo;

	@Autowired
	SearchRepo search;
	
	@Autowired
	DashboardRepo dashRepo;

	@RequestMapping("/login")
	public String test() {
		return "login";
	}

	public static List<String> activeUsers = new ArrayList<String>();
	// public Map<String,String> activeUsers = new HashMap<>();

	@RequestMapping("/verifyUser")
	public String verify(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		boolean authentication = userLogin.validateUser(userName, password);
		if (authentication) {

			model.put("user", userName);
			activeUsers.add(userName);
			model.put("news",dashRepo.getNewsFeed());
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
			model.put("playListData", playList.getUserPlayList(userName));
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
			model.put("news",dashRepo.getNewsFeed());
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

	@RequestMapping("/createnew")
	public String createNewPlaylist(HttpServletRequest request, Map<String, Object> model) {

		String userName = request.getParameter("hidden");
		String playlistCategory = request.getParameter("playlistCategory");
		String playlistName = request.getParameter("playlistName");

		if (activeUsers.contains(userName)) {
			int status = playList.createNewPlayList(userName, playlistCategory, playlistName);
			model.put("user", userName);
			model.put("news",dashRepo.getNewsFeed());
			if (status == 1)
				return "welcome";
			else
				return "errorPage";
		} else
			return "errorPage";
	}

	@RequestMapping("/getTracks")
	public String getTracks(HttpServletRequest request, Map<String, Object> model) {

		String userName = request.getParameter("hidden");
		String tracksFor = request.getParameter("type");

		if (activeUsers.contains(userName)) {
			if (tracksFor.equals("playlist")) {
				int playlistId = Integer.parseInt(request.getParameter("playlistid"));
				String playlistOwner = request.getParameter("playlistowner");
				String playlistName = request.getParameter("playlistName");

				model.put("tracks", playList.getTracksforPlaylist(playlistId, playlistOwner));
				model.put("user", userName);
				model.put("playlistId", playlistId);
				model.put("playlistName", playlistName);

				if (userName.equals(playlistOwner))
					model.put("owner", "you");
				else
					model.put("owner", playlistOwner);

				return "displayPlaylistTracks";
			} else if (tracksFor.equals("artist")) {
				String artistId = request.getParameter("artistId");
				String artistName = request.getParameter("artistName");

				model.put("tracks", artistRepo.getTracksforArtist(artistId));
				model.put("user", userName);
				model.put("artistId", artistId);
				model.put("artistName", artistName);
				model.put("playListData", playList.getUserPlayList(userName));

				return "displayArtistTracks";
			}

			return "errorPage";
		} else
			return "errorPage";
	}
}
