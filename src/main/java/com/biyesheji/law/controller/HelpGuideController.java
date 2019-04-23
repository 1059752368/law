package com.biyesheji.law.controller;

import com.biyesheji.law.pojo.HelpGuide;
import com.biyesheji.law.service.HelpGuideService;
import com.biyesheji.law.web.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/HelpGuide")
public class HelpGuideController {
    private final HelpGuideService helpGuideService;

    @Autowired
    public HelpGuideController(HelpGuideService helpGuideService) {
        this.helpGuideService = helpGuideService;
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新",notes = "更新")
    @ResponseBody
    public ResultVO updateQuestion(HelpGuide helpGuide){
        ResultVO resultVO = new ResultVO();
        try {
            helpGuideService.updateQuestion(helpGuide);
            resultVO.addData("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
        }
        return resultVO;
    }
}
