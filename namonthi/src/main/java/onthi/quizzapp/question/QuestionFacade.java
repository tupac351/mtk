/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.question;

import java.util.List;

/**
 *
 * @author ASUS
 */
public class QuestionFacade {
    QuestionUpdateService dbService = new QuestionUpdateService();
    
    public boolean importQuestion(QuestionSource questionSource){
        if(questionSource == null)
            return false;
        
        List<Question> questions = questionSource.loadQuestions();
        return dbService.addQuestion(questions);
    }
}
