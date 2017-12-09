package com.golive.launch.cont;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//test
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.golive.launch.repo.PlayListRepo;
import com.golive.launch.repo.RatingRepo;

@RestController
@ComponentScan(basePackages = "com.golive.launch.view")
public class RestfulController {

	@Autowired
	RatingRepo ratingrepo;

	@Autowired
	PlayListRepo playListRepo;

	@Autowired
	SimpleController simpleController;

	@RequestMapping(value = "/rateTrack", method = RequestMethod.POST)
	public int rateTrack(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String userName = request.getParameter("username");
		String trackId = request.getParameter("trackId");
		String rating = request.getParameter("rating");
		if (rating.equals("blank"))
			return 2;
		int res = ratingrepo.insertRating(userName, trackId, rating);
		return res;
	}

	@RequestMapping("/deleteTrackFromplaylist")
	public void deleteTrackFromplaylist(HttpServletRequest request) {

		int playlistId = Integer.parseInt(request.getParameter("playlistId"));
		String username = request.getParameter("user");
		String trackid = (request.getParameter("trackid"));

		playListRepo.deleteTrackFromPlaylist(playlistId, trackid);
	}

	@RequestMapping(value = "/insertTrack", method = RequestMethod.POST)
	public int insertInPlaylist(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String playListId = request.getParameter("playListId");
		String trackId = request.getParameter("trackId");
		if (playListId.equals("blank"))
			return 2;
		int res = playListRepo.addTrackInPlaylist(playListId, trackId);
		return res;
	}

}
