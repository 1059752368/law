package com.biyesheji.law.controller;

import com.biyesheji.law.pojo.User;
import com.biyesheji.law.service.AdvisoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/advisory")
public class AdvisoryController {
    private final AdvisoryService advisoryService;

    @Autowired
    public AdvisoryController(AdvisoryService advisoryService) {
        this.advisoryService = advisoryService;
    }

    @RequestMapping("/addAdvisory")
    public String addAdvisory( int expertId, String advisory,  HttpServletRequest request){
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        advisoryService.addAdvisory(userId,expertId,advisory);
        return "redirect:../user/connectExperts"+"?expertId="+expertId;
    }
}
