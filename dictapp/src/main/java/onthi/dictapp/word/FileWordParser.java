/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp.word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class FileWordParser {
    private String filePath;

    public FileWordParser(String filePath) {
        this.filePath = filePath;
    }

    public List<WordDTO> parse() throws IOException {
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<List<WordDTO>>(){}.getType();

        try (Reader reader = new FileReader(this.filePath, StandardCharsets.UTF_8)) {
            List<WordDTO> list = gson.fromJson(reader, typeOfT);
            return list != null ? list : new ArrayList<>();
        }
    }

    // DTO hứng dữ liệu từ JSON
    public static class WordDTO {
        private String english;
        private String pronunciation;
        private String vietnamese;

        public String getEnglish() { return english; }
        public String getPronunciation() { return pronunciation; }
        public String getVietnamese() { return vietnamese; }
    }
}
