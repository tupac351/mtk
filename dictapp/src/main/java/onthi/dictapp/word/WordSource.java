/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp.word;
import java.util.List;
import onthi.dictapp.model.Word;
/**
 *
 * @author ASUS
 */
public interface WordSource {
    List<Word> loadWords();
}
