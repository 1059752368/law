package com.biyesheji.law.controller;

import com.biyesheji.law.pojo.User;
import com.biyesheji.law.service.ExpertUserService;
import com.biyesheji.law.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/expertUser")
public class ExpertUserController {
    private final ExpertUserService expertUserService;
    private final UserService userService;

    public ExpertUserController(ExpertUserService expertUserService, UserService userService) {
        this.expertUserService = expertUserService;
        this.userService = userService;
    }

    public String aboutMe(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        User currentUser = userService.findUserById(userId);

        List<User> userList = new ArrayList<>();

        return "expertReply";
    }
}
