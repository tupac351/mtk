/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package onthi.quizzapp.question;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class FileQuestionAdapter implements QuestionSource {
    
    private FileQuestionParser adaptee;    
    private String level;
    private String category;

    public FileQuestionAdapter(FileQuestionParser adaptee, String level, String category) {
        this.level = level;
        this.category = category;
        this.adaptee = adaptee;
    }

    @Override
    public List<Question> loadQuestions() {
        try {
            return adaptee.parse(category, level);
        } catch (IOException ex) {
            Logger.getLogger(FileQuestionAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}

