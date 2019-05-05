$(function () {
    $(".questions").delegate(".result-answer-item","click",function(){
        // $(this).prev().siblings(".answer").prev("checked",false);
        console.log($(this).prev());
        if($(this).prev().prop("checked")){
            $(this).prev().prop("checked",false);
            $(this).css("backgroundColor","#fff");
        }else{
            $(this).prev().prop("checked",true);
            $(this).css("backgroundColor","green");
        }
        // $(this).siblings(".result-answer-item").css("backgroundColor","#fff");
    })
});