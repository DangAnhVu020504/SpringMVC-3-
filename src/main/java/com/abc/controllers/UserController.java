package com.abc.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abc.entities.Post;
import com.abc.entities.User;
import com.abc.entities.Province;
import com.abc.services.PostService;
import com.abc.services.ProvinceService;
import com.abc.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    
    private PostService postService;
    private ProvinceService provinceService;
    private UserService userService;
    
    @Autowired
    public UserController(PostService postService, ProvinceService provinceService,UserService userService) {
		this.postService = postService;
		this.provinceService = provinceService;
		this.userService = userService;
	}
	@GetMapping("/profile")
	public String profileUser(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		if(user == null)
			return "redirect:/login";
		
		List<Post> posts = new ArrayList<Post>();
		
		posts = postService.getPostById(user.getId());
		
		
		model.addAttribute("user",user);
		model.addAttribute("posts",posts);
		
		return "profile";
		
	}
	
	@GetMapping("/edit")
	public String showEditProfile(Model model, HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user == null)
	        return "redirect:/login";

	    // Giả sử có ProvinceService lấy danh sách tỉnh/thành
	    List<Province> provinces = provinceService.getAllProvinces();

	    model.addAttribute("user", user);
	    model.addAttribute("provinces", provinces);
	    return "editProfile"; // Tên file JSP
	}
	@PostMapping("/edit")
	public String handleEditProfile(@RequestParam String email,
	                                @RequestParam String birthday,
	                                @RequestParam int provinceId,
	                                Model model, HttpSession session) {

	    User user = (User) session.getAttribute("user");
	    if (user == null)
	        return "redirect:/login";

	    Map<String, String> errors = new HashMap<>();

	    // Validate Email
	    if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
	        errors.put("email", "Email không hợp lệ");
	    }

	    // Validate Tuổi >= 15
	    try {
	        LocalDate birthDate = LocalDate.parse(birthday);
	        long age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
	        if (age < 15) {
	            errors.put("birthday", "Tuổi phải từ 15 trở lên");
	        }
	    } catch (Exception e) {
	        errors.put("birthday", "Ngày sinh không hợp lệ");
	    }

	    if (!errors.isEmpty()) {
	        model.addAttribute("errors", errors);
	        model.addAttribute("user", user);
	        model.addAttribute("provinces", provinceService.getAllProvinces());
	        return "editProfile";
	    }

	    // Cập nhật thông tin
	    user.setEmail(email);
	    user.setBirthday(Date.valueOf(birthday));
	    user.setProvince(provinceService.getProvinceById(provinceId));

	    userService.updateUser(user);
	    session.setAttribute("user", user); // cập nhật lại session

	    return "redirect:/profile";
	}


}
