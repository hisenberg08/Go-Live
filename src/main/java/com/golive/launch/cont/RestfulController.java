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

import com.golive.launch.repo.RatingRepo;

@RestController
@ComponentScan(basePackages = "com.golive.launch.view")
public class RestfulController {

	@Autowired
	RatingRepo rating;

	@RequestMapping(value = "/rateTrack", method = RequestMethod.POST)
	public String playlist(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		SimpleController simpleController = new SimpleController();
		String userName = request.getParameter("username");

		return userName;
	}

}
