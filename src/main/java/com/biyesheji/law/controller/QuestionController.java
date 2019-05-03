package com.biyesheji.law.controller;

import com.biyesheji.law.pojo.Answers;
import com.biyesheji.law.pojo.Comment;
import com.biyesheji.law.pojo.Question;
import com.biyesheji.law.repository.AnswersRepository;
import com.biyesheji.law.service.CommentService;
import com.biyesheji.law.service.QuestionService;
import com.biyesheji.law.web.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final CommentService commentService;

    @Autowired
    public QuestionController(QuestionService questionService, CommentService commentService) {
        this.questionService = questionService;
        this.commentService = commentService;
    }

    @GetMapping("/randomTenQue")
    @ApiOperation(value = "随机问题",notes = "随机10问题")
    public String listTenQues(Model model){
       List<Question> questionAndAnswer =  questionService.randomTen();
        Question[] questions = new Question[questionAndAnswer.size()];
        questionAndAnswer.toArray(questions);
       model.addAttribute("questionAndAnswer",questions);
        return "testTenQuestion";
    }

    @RequestMapping("/submitAnswers")
    @ApiOperation(value = "检测答案正确" ,notes="检测答案正确")
    public String submitAnswers(Model model,int[] answer0,int[] answer1,int[] answer2,int[] answer3,int[] answer4,int[] answer5,int[] answer6,int[] answer7,int[] answer8,int[] answer9){
        HashMap<String,Object> map0 = new HashMap<String,Object>();
        HashMap<String,Object> map1 = new HashMap<String,Object>();
        HashMap<String,Object> map2 = new HashMap<String,Object>();
        HashMap<String,Object> map3 = new HashMap<String,Object>();
        HashMap<String,Object> map4 = new HashMap<String,Object>();
        HashMap<String,Object> map5 = new HashMap<String,Object>();
        HashMap<String,Object> map6 = new HashMap<String,Object>();
        HashMap<String,Object> map7 = new HashMap<String,Object>();
        HashMap<String,Object> map8 = new HashMap<String,Object>();
        HashMap<String,Object> map9 = new HashMap<String,Object>();

        boolean a0 = testQuestion(answer0);
        boolean a1 = testQuestion(answer1);
        boolean a2 = testQuestion(answer2);
        boolean a3 = testQuestion(answer3);
        boolean a4 = testQuestion(answer4);
        boolean a5 = testQuestion(answer5);
        boolean a6 = testQuestion(answer6);
        boolean a7 = testQuestion(answer7);
        boolean a8 = testQuestion(answer8);
        boolean a9 = testQuestion(answer9);

        map0.put("questionID",answer0[0]);
        map0.put("judgeAnswer",a0);
        map0.put("currentNumber","1");

        map1.put("questionID",answer1[0]);
        map1.put("judgeAnswer",a1);
        map1.put("currentNumber","2");

        map2.put("questionID",answer2[0]);
        map2.put("judgeAnswer",a2);
        map2.put("currentNumber","3");

        map3.put("questionID",answer3[0]);
        map3.put("judgeAnswer",a3);
        map3.put("currentNumber","4");

        map4.put("questionID",answer4[0]);
        map4.put("judgeAnswer",a4);
        map4.put("currentNumber","5");

        map5.put("questionID",answer5[0]);
        map5.put("judgeAnswer",a5);
        map5.put("currentNumber","6");

        map6.put("questionID",answer6[0]);
        map6.put("judgeAnswer",a6);
        map6.put("currentNumber","7");

        map7.put("questionID",answer7[0]);
        map7.put("judgeAnswer",a7);
        map7.put("currentNumber","8");

        map8.put("questionID",answer8[0]);
        map8.put("judgeAnswer",a8);
        map8.put("currentNumber","9");

        map9.put("questionID",answer9[0]);
        map9.put("judgeAnswer",a9);
        map9.put("currentNumber","10");

        List<HashMap<String,Object>> answerList = new ArrayList<>();
        answerList.add(map0);
        answerList.add(map1);
        answerList.add(map2);
        answerList.add(map3);
        answerList.add(map4);
        answerList.add(map5);
        answerList.add(map6);
        answerList.add(map7);
        answerList.add(map8);
        answerList.add(map9);
        model.addAttribute("answerList",answerList);
//
//        model.addAttribute("map0",map0);
//        model.addAttribute("map1",map1);
//        model.addAttribute("map2",map2);
//        model.addAttribute("map3",map3);
//        model.addAttribute("map4",map4);
//        model.addAttribute("map5",map5);
//        model.addAttribute("map6",map6);
//        model.addAttribute("map7",map7);
//        model.addAttribute("map8",map8);
//        model.addAttribute("map9",map9);

        return "submitAnswers";
    }

    private boolean testQuestion(int[] answer){
        int answerid = questionService.findOneQuestion(answer[0]).getAnswersId();
        Answers answers = questionService.getAnswerByAnswerId(answerid);
        String rightAnswer = answers.getRightAnswer();

        ArrayList<Integer> rightAnswerArray = new ArrayList<>();
        for (int i = 0; i < rightAnswer.length(); i++){
            rightAnswerArray.add(Integer.parseInt(rightAnswer.substring(i, i + 1)));
        }
        rightAnswerArray.sort(Integer::compareTo );
        String rightAnswerString= "";
        for(int i =0 ; i<rightAnswerArray.size();i++) {
            rightAnswerString += rightAnswerArray.get(i);
        }
        String AnswerString= "";
        for(int i = 1; i<answer.length;i++) {
            AnswerString += answer[i];
        }
        return rightAnswerString.equals(AnswerString);
    }


    @RequestMapping("/getCommentAndAnalysis")
    @ApiOperation(value = "显示评论和解析",notes = "显示评论和解析")
    public String getCommentAndAnalysis(@RequestParam int id,Model model){
        String analysis = questionService.geiAnalysis(id);
        List<Comment> comments = commentService.findComment(id);

        model.addAttribute("analysis",analysis);
        model.addAttribute("comments",comments);
        return "analysisAndComments";
    }

    @PostMapping("/addQuestion")
    @ApiOperation(value = "新增问题",notes = "新增问题")
    @ResponseBody
    public ResultVO addQuestion(Question question, @RequestBody Answers answers){
        ResultVO resultVO = new ResultVO();
        try {
            questionService.addQuestion(question,answers);
            resultVO.addData("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
        }
        return resultVO;
    }

    @PostMapping("/deleteQuestion")
    @ApiOperation(value = "删除问题",notes="删除问题")
    @ResponseBody
    public  ResultVO deleteQuestion(@RequestParam("questionId") int questionId){
        ResultVO resultVO = new ResultVO();
        try {
            questionService.delete(questionId);
            resultVO.addData("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(false);
        }
        return resultVO;
    }

    @PostMapping("/updateQuestion")
    @ApiOperation(value = "修改",notes="修改")
    public  String updateQuestion(Question question,Answers answers){
        question.setAnswers(answers);
        answers.setId(question.getAnswersId());
        questionService.updateQuestion(question);
        return "redirect:listQuestions";
    }

    @GetMapping("/editOneQuestion")
    @ApiOperation(value = "编辑一个问题",notes = "编辑一个问题")
    public String EditOneQuestion(Model model ,@RequestParam("id") int id){
        Question question = questionService.findOneQuestion(id);
        model.addAttribute("question",question);
        return "updateQuestion";
    }

    @GetMapping("/listQuestions")
    @ApiOperation(value = "随机问题1", notes = "随机10问题")
    public String listQuestions(Model model){
        List<Question> questionList = questionService.getAll();
        model.addAttribute("question", questionList);
        return "question";
    }

    @GetMapping("/listAllQuestionAndComment")
    public String listAllQuestionAndComment(Model model){
        List<Question> questionList = questionService.getAllQuestionAndComment();
        model.addAttribute("questionList",questionList);

        return "listAllQuestionAndComment2";
    }
}