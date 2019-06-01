package com.biyesheji.law.controller;

import com.aliyun.oss.OSSClient;
import com.biyesheji.law.pojo.Advisory;
import com.biyesheji.law.pojo.ExpertUser;
import com.biyesheji.law.pojo.User;
import com.biyesheji.law.service.AdvisoryService;
import com.biyesheji.law.service.ExpertUserService;
import com.biyesheji.law.service.UserService;
import com.biyesheji.law.web.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ExpertUserService expertUserService;
    private final AdvisoryService advisoryService;

    @Value("${localPath}")
    String localPath;
    @Value("${accessKeyId}")
    String accessKeyId;
    @Value("${accessKeySecret}")
    String accessKeySecret;

    @Autowired
    public UserController(UserService userService, ExpertUserService expertUserService, AdvisoryService advisoryService) {
        this.userService = userService;
        this.expertUserService = expertUserService;
        this.advisoryService = advisoryService;
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
    public ResultVO login(@RequestParam("loginName") String loginName, @RequestParam("password") String password,
                          Map<String,Object> map, HttpServletRequest request, HttpServletResponse response) {
        ResultVO resultVO = new ResultVO();

        try {
            Map<String, String> login = userService.login(loginName, password);
            if (login.get("status").equals("0")) {
                resultVO.setStatus(false);
                resultVO.setErrorMsg("此用户不存在");
                map.put("msg","此用户不存在");
//                return  "question";
            }
            if (login.get("status").equals("1")) {
                resultVO.setStatus(false);
                resultVO.setErrorMsg("密码错误");
                map.put("msg","密码错误");
//                return  "login";
            }
            if (login.get("status").equals("2")) {
                resultVO.addData("userId", login.get("userId"));
                resultVO.addData("userType", login.get("userType"));
                resultVO.addData("userPhoto", login.get("userPhoto"));
                request.getSession().setAttribute("userId",login.get("userId"));
                request.getSession().setAttribute("userType",login.get("userType"));
//                response.addCookie(new Cookie("userId", "" + login.get("userId")));
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
                user.setPhotoId("http://user-photo-image.oss-cn-beijing.aliyuncs.com/10.jpg?x-oss-process=image/resize,w_300");
                user.setType(1);
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

    @RequestMapping("/setPhoto")
    public String setPhoto(Model model, HttpServletRequest request){
       try {
           int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
           String photoId = userService.findUserById(userId).getPhotoId();

           model.addAttribute("headPhoto",photoId);
           return "headPhoto";
       }
       catch (Exception e){
           e.printStackTrace();
           return "login";
       }
    }

    @PostMapping(value = "/uploadHeadPhoto")
    @ResponseBody
    public ResultVO upload(@RequestParam("file") MultipartFile postFile,HttpServletRequest request) {
        ResultVO resultVO = new ResultVO();
        try {
            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
            User currentUser = userService.findUserById(userId);

            //获取原文件名和文件格式
            String oriFileName = postFile.getOriginalFilename();
            String type = "";
            int position = oriFileName.lastIndexOf(".");
            if (position >= 0) {
                type = oriFileName.substring(position + 1);
            }

            //附件检测
            if (!StringUtils.containsAny(type,"bmp", "jpg", "png", "svg", "gif")) {
                resultVO.setStatus(false);
                resultVO.setErrorMsg("上传附件请选择图片，支持\"jpg\", \"png\", \"svg\", \"gif\"格式");
                return resultVO;
            }
            String newFileName = (int) (Math.random() * 1000) + "5" + System.currentTimeMillis() + "5" + (int) (Math.random() * 10000) + "5" + "." + type;
//            String filePath = request.getSession().getServletContext().getRealPath("/upload/" + newFileName);
            String filePath = localPath + newFileName;

            currentUser.setPhotoId(filePath);
            try {
                currentUser.setPhotoId("http://user-photo-image.oss-cn-beijing.aliyuncs.com/"+currentUser.getId()+".jpg?x-oss-process=image/bright,5");
                userService.updateUser(currentUser);
            } catch (Exception e) {
                e.printStackTrace();
                resultVO.setStatus(false);
                resultVO.setErrorMsg("头像存储到数据库出错");
                return resultVO;
            }
            //存储文件
            try {
                File tempFile = new File(filePath);
                FileUtils.copyInputStreamToFile(postFile.getInputStream(), tempFile);

                OSSClient ossClient = new OSSClient("oss-cn-beijing.aliyuncs.com",accessKeyId,accessKeySecret);
                boolean found = ossClient.doesObjectExist("user-photo-image", currentUser.getId().toString()+".jpg");
                if (found){
                    ossClient.deleteObject("user-photo-image", currentUser.getId().toString()+".jpg");
                }
                // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
                ossClient.putObject("user-photo-image", currentUser.getId().toString()+".jpg", new File(filePath));
                // 关闭OSSClient。
                ossClient.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
                resultVO.setStatus(false);
                resultVO.setErrorMsg("上传附件请选择图片，支持\"jpg\", \"png\", \"svg\", \"gif\"格式");
                return resultVO;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
            resultVO.setErrorMsg("未知错误");
        }
        return resultVO;
    }

    @RequestMapping("/expertCertification")
    public String expertCertification(HttpServletRequest request,Model model){
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        User currentUser = userService.findUserById(userId);
        model.addAttribute("currentUser",currentUser);

        return "expertCertification";
    }

    @RequestMapping("/setExpert")
    public String setExpert(HttpServletRequest request,String realName,String phoneNumber,String certificationInformation){
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        User currentUser = userService.findUserById(userId);
//        currentUser.setType(2);
//        userService.updateUser(currentUser);

        ExpertUser expertUser = new ExpertUser();
        expertUser.setRealName(realName);
        expertUser.setPhoneNumber(phoneNumber);
        expertUser.setUserId(userId);
        expertUser.setCertificationInformation(certificationInformation);
        expertUser.setStatus(2);
        expertUserService.addExpertUser(expertUser);

        return "redirect:expertCertification";
    }

    @RequestMapping("/listAllExperts")
    @ResponseBody
    public ResultVO listAllExperts(){
        List<ExpertUser> experts = expertUserService.findAllExperts(1);
        ResultVO resultVO  = new ResultVO();
        resultVO.addData("experts",experts);
        return resultVO;
    }

    @GetMapping("/connectExperts")
    public String connectExperts(@RequestParam int expertId,HttpServletRequest request,Model model){
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
//        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        User currentUser = userService.findUserById(userId);
        ExpertUser expertUser = expertUserService.findOneExpertUser(expertId);

        List<Advisory> list =advisoryService.historyAdvisories(currentUser,expertUser);
        for (Advisory advisory:list
             ) {
            advisory.setUser(currentUser);
            advisory.setExpertUser(expertUser);
        }
        model.addAttribute("expertId",expertId);
        model.addAttribute("list",list);
        return "connectExperts";
    }

    @PostMapping("/findUserById")
    @ResponseBody
    public ResultVO findUserById(@RequestParam("userId") int userId) {
        ResultVO resultVO = new ResultVO();
        try {
            User user = userService.findUserById(userId);
            resultVO.addData("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
            resultVO.setErrorMsg("查找用户失败！");
        }
        return resultVO;
    }


}
