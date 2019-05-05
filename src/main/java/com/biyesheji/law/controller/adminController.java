package com.biyesheji.law.controller;

import com.biyesheji.law.pojo.*;
import com.biyesheji.law.service.CommentService;
import com.biyesheji.law.service.ExpertUserService;
import com.biyesheji.law.service.QuestionService;
import com.biyesheji.law.service.UserService;
import com.biyesheji.law.web.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class adminController {
    private final UserService userService;
    private final QuestionService questionService;
    private final CommentService commentService;
    private final ExpertUserService expertUserService;

    @Autowired
    public adminController(UserService userService, QuestionService questionService, CommentService commentService, ExpertUserService expertUserService) {
        this.userService = userService;
        this.questionService = questionService;
        this.commentService = commentService;
        this.expertUserService = expertUserService;
    }
//    进入主页
    @RequestMapping("/index")
    public String index(){
        return "admin/index";
    }



    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "登录")
    @ResponseBody
    public ResultVO login(@RequestParam("loginName") String loginName, @RequestParam("password") String password,
                          Map<String,Object> map, HttpServletRequest request, HttpServletResponse response) {
        ResultVO resultVO = new ResultVO();

        try {
            Map<String, String> login = userService.login(loginName, password);
            switch (login.get("status")) {
                case "0":
                    resultVO.setStatus(false);
                    resultVO.setErrorMsg("此用户不存在");
                    map.put("msg", "此用户不存在");
//                return  "question";
                    break;
                case "1":
                    resultVO.setStatus(false);
                    resultVO.setErrorMsg("密码错误");
                    map.put("msg", "密码错误");
//                return  "login";
                    break;
                case "2":
                    if (!login.get("userType").equals("3")) {
                        resultVO.setStatus(false);
                        resultVO.setErrorMsg("用户权限不足，请使用管理员账号登录");
                        map.put("msg","密码错误");
                        return resultVO;
                    }
                    resultVO.addData("userId", login.get("userId"));
                    resultVO.addData("userType", login.get("userType"));
                    resultVO.addData("userPhoto", login.get("userPhoto"));
                    request.getSession().setAttribute("userId", login.get("userId"));
                    request.getSession().setAttribute("userType", login.get("userType"));
                    break;
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




//  用户管理
    @RequestMapping("/listUser")
    public String listUser(Model model){
        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "admin/user_tables";
    }
    @RequestMapping("/listOneUserInfo")
    public String listOneUserInfo(@RequestParam("userId") int userId,Model model){
        User user = userService.findUserById(userId);
        model.addAttribute("user",user);
        return "admin/user-profile-lite";
    }
    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam int userId,String LoginName,String Pwd,String IdentificationNumber,int userType){
        User user = userService.findUserById(userId);
        user.setLoginName(LoginName);
        user.setIdentificationNumber(IdentificationNumber);
        user.setPwd(Pwd);
        user.setType(userType);
        userService.updateUser(user);

        return "redirect:/admin/listUser";
    }


//评论管理
    @RequestMapping("/listAllComments")
    public String listAllComments(Model model){
        List<Question> questionList = questionService.getAllQuestionAndComment();
        model.addAttribute("questionList",questionList);
        return "admin/comment_tables";
    }
    @RequestMapping("/getOneQuestionComment")
    public String getOneQuestionComment(@RequestParam int questionId, Model model){
        List<Comment> commentList = commentService.findComment(questionId);
        model.addAttribute("commentList",commentList);
        return "admin/comment-lite";
    }
    @RequestMapping("/deleteOneComment")
    public String deleteOneComment(@RequestParam int commentId,@RequestParam int questionId){
        commentService.deleteComment(commentId);
        return "redirect:/admin/getOneQuestionComment"+"?questionId="+questionId;
    }



//问题管理
    @RequestMapping("/listQuestion")
    public String listQuestion(Model model){
        List<Question> questionList = questionService.getAll();
        model.addAttribute("questionList",questionList);
        return "admin/question_tables";
    }

    @RequestMapping("/getOneQuestion")
    public String getOneQuestion(@RequestParam int questionId,Model model){
        Question question = questionService.findOneQuestion(questionId);
        model.addAttribute("question",question);
        return "admin/question-lite";
    }
    @RequestMapping("/deleteOneQuestion")
    public String deleteQuestion(@RequestParam int questionId,Model model){
        questionService.delete(questionId);
        return "redirect:/admin/listQuestion";
    }


    @RequestMapping("/inputQuestion")
    public String inputQuestion(){
        return "admin/question-addNew";
    }
    @RequestMapping("/addNewQuestion")
    public String addNewQuestion(String content,String answer1,String answer2,String answer3,String answer4,String analysis,String rightAnswer){
        Answers answers = new Answers();
        answers.setAnswer1(answer1);
        answers.setAnswer2(answer2);
        answers.setAnswer3(answer3);
        answers.setAnswer4(answer4);
        answers.setRightAnswer(rightAnswer);

        Question question = new Question();
        question.setAnalysis(analysis);
        question.setContent(content);
        questionService.addQuestion(question,answers);

        return "redirect:/admin/listQuestion";
    }


    @RequestMapping("/updateOneQuestion")
    public String updateOneQuestion(@RequestParam int questionId, String content, String analysis, String answer1,String answer2,String answer3,String answer4,String rightAnswer){
        Question question = questionService.findOneQuestion(questionId);
        Answers answers = questionService.getAnswerByAnswerId(question.getAnswersId());
        answers.setAnswer1(answer1);
        answers.setAnswer2(answer2);
        answers.setAnswer3(answer3);
        answers.setAnswer4(answer4);
        answers.setRightAnswer(rightAnswer);

        question.setContent(content);
        question.setAnalysis(analysis);
        questionService.updateQuestion(question,answers);
        return "redirect:/admin/listQuestion";
    }


    //专家认证
    @RequestMapping("/expertCertification")
    public String expertCertification(Model model){
        List<ExpertUser> expertUsers = expertUserService.findAllCertificationUser(2);
        model.addAttribute("expertUsers",expertUsers);
        return "admin/expert _certification_tables";
    }
    @RequestMapping("/setExperts")
    public String setExperts(@RequestParam int id){
        expertUserService.setExperts(id);
        return "redirect:/admin/expertCertification";
    }
}
