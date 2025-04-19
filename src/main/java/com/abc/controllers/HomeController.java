package com.abc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abc.entities.Post;
import com.abc.entities.User;
import com.abc.services.FollowService;
import com.abc.services.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
    private PostService postService;
    private FollowService followService;
    
    @Autowired
	public HomeController(PostService postService, FollowService followService) {
		this.postService = postService;
		this.followService = followService;
	}
	@RequestMapping(value = "/")
    public String homeController(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if(user == null) {
			return "redirect:/login";
		}

		
		
        List<User> suggestFollow = followService.getSuggestFollow(user.getId());
        List<User> usersf = followService.getUserFollower(user.getId());
        List<User> userfed = followService.getUserFollowed(user.getId());
        List<Post> posts = postService.getAllPost(user.getId());

        model.addAttribute("suggestfollow", suggestFollow);
        model.addAttribute("usersf",usersf);
        model.addAttribute("userfed",userfed);
        model.addAttribute("posts", posts);
        
        return "home";
    }
	@RequestMapping("/search")
	public String searchUser(
	        @RequestParam(name = "minFollowing", defaultValue = "0") int minFollowing,
	        @RequestParam(name = "minFollower", defaultValue = "0") int minFollower,
	        Model model) {

	    List<User> result = followService.searchUsersByFollowCounts(minFollowing, minFollower);
	    model.addAttribute("searchResults", result);
	    model.addAttribute("minFollowing", minFollowing);
	    model.addAttribute("minFollower", minFollower);

	    return "search";
	}

}
