
package onthi.quizzapp.question;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Import thư viện Gson
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import onthi.quizzapp.model.QuestionDTO;



public class FileQuestionParser {
    private String path;

    public FileQuestionParser(String path) {
        this.path = path;
    }
    
    public List<Question> parse(String category, String level) throws IOException{
        
        if(category == null)
            throw new IllegalArgumentException("Danh mục không được chọn");
        
        if(level == null)
            throw new IllegalArgumentException("Độ khó chưa được chọn");
        
        Path jsonPath = Path.of(this.path);
        
        if(!Files.exists(jsonPath)){
            throw new IOException("File khong tim thay: "+ path);
        }
        
        Gson gson = new Gson();        
        Type typeOfT = new TypeToken<List<QuestionDTO>>(){}.getType();
        try (Reader reader = new FileReader(this.path, StandardCharsets.UTF_8)) {
            List<QuestionDTO> lsQuestionDTO;
            
            lsQuestionDTO = gson.fromJson(reader, typeOfT);
            List<Question> listQuestion = new ArrayList<>();
            
            for (int i = 0; i < lsQuestionDTO.size(); i++) {
                QuestionDTO qdto = lsQuestionDTO.get(i);
                
                List<Choice> choices = new ArrayList<>();
                for (Choice choice : choices) {
                    choices.add(new Choice(choice.getContent(), choice.getIsCorrect()));
                }
                
                Question q = new Question.Builder()
                        .content(qdto.getContent())
                        .hint(qdto.getHint())
                        .category(category)
                        .level(level)
                        .choices(choices)
                        .build();
                
                listQuestion.add(q);
            }            
            System.out.println("Size: " + lsQuestionDTO.size());
            return listQuestion;
        }           
    }
}
