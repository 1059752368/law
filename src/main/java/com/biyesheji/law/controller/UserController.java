package com.biyesheji.law.controller;

import com.biyesheji.law.pojo.User;
import com.biyesheji.law.service.UserService;
import com.biyesheji.law.web.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @RequestMapping("/login")
//    @ApiOperation(value = "登录",notes = "登录")
//    public String regist(){
//        return "regist.html";
//    }

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户",notes = "添加用户添加用户")
    public ResultVO addUser(@RequestBody User user) {

        ResultVO resultVO = new ResultVO();
        try {
            User resultUser = userService.addUser(user);
            resultVO.addData("data", resultUser);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
            resultVO.setErrorMsg("添加用户失败" + e.getMessage());
        }
        return resultVO;
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "登录")
    @ResponseBody
    public ResultVO login(@RequestParam("loginName") String loginName,@RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session) {
        ResultVO resultVO = new ResultVO();

        try {
            Map<String, Integer> login = userService.login(loginName, password);
            if (login.get("status") == 0) {
                resultVO.setStatus(false);
                resultVO.setErrorMsg("此用户不存在");
                map.put("msg","此用户不存在");
//                return  "question";
            }
            if (login.get("status") == 1) {
                resultVO.setStatus(false);
                resultVO.setErrorMsg("密码错误");
                map.put("msg","密码错误");
//                return  "login";
            }
            if (login.get("status") == 2) {
                resultVO.addData("userId", login.get("userId"));
                resultVO.addData("userType", login.get("userType"));
                session.setAttribute("loginUser",resultVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
            resultVO.setErrorMsg("登录失败，" + e.getMessage());
            map.put("msg","登录失败");
//            return  "login";
        }
        return resultVO;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultVO register(User user){
        ResultVO resultVO = new ResultVO();
        try {
            if (userService.findUserByLoginName(user.getLoginName()) == null) {
                //默认为学生身份
                user.setType(2);
                userService.addUser(user);
                return resultVO;
            } else {
                resultVO.setStatus(false);
                resultVO.setErrorMsg("此用户已存在");
                return resultVO;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
            resultVO.setErrorMsg("注册失败，请联系管理员");
            return resultVO;
        }
    }

    @RequestMapping("/userLogin")
    public String userLogin(){
        return "login";
    }

    @PostMapping("/forgetPwd")
    @ResponseBody
    public ResultVO forgetPwd(User user){
        ResultVO resultVO = new ResultVO();
        try{
            User resultUser = userService.findUserByLoginName(user.getLoginName());
            if (resultUser == null){
                resultVO.setStatus(false);
                resultVO.setErrorMsg("用户名不存在");
            } else {
                if(resultUser.getIdentificationNumber().equals(user.getIdentificationNumber())){
                    resultUser.setPwd(user.getPwd());
                    userService.addUser(resultUser);
                }else {
                    resultVO.setStatus(false);
                    resultVO.setErrorMsg("身份证号码不正确");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            resultVO.setStatus(false);
            resultVO.setErrorMsg("修改失败，网络服务器出错");
        }
        return resultVO;
    }
}
