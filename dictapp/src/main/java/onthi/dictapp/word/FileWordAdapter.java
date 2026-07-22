/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp.word;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Worker;
import onthi.dictapp.model.Word;
/**
 *
 * @author ASUS
 */
public class FileWordAdapter implements WordSource{
    private FileWordParser parser;
    private String wordType; // Loại từ gán chung khi import (vd: Noun)

    public FileWordAdapter(FileWordParser parser, String wordType) {
        this.parser = parser;
        this.wordType = wordType;
    }

    @Override
    public List<Word> loadWords() {
        List<Word> words = new ArrayList<>();
        try {
            List<FileWordParser.WordDTO> dtos = parser.parse();
            for (FileWordParser.WordDTO dto : dtos) {
                Word w = new Word.Builder()
                        .english(dto.getEnglish())
                        .pronunciation(dto.getPronunciation())
                        .vietnamese(dto.getVietnamese())
                        .wordType(this.wordType)
                        .build();
                words.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}
