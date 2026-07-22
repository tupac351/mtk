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
class QuestionUpdateService {
        QuestionRepository repository = new QuestionRepository();
    
    boolean addQuestion(List<Question> questions){
        return repository.addQuesstion(questions);
    }
}
