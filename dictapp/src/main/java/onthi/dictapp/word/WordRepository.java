/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp.word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import onthi.dictapp.JdbcConnector;
import onthi.dictapp.model.Word;
import onthi.dictapp.model.WordType;
/**
 *
 * @author ASUS
 */
public class WordRepository {
    // --- CÂU 3: Thêm từ mới vào CSDL ---
    public boolean addWord(Word word, int typeId) {
        String sql = "INSERT INTO word(english, pronunciation, vietnamese, type_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = JdbcConnector.getInstance().connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, word.getEnglish());
            stmt.setString(2, word.getPronunciation());
            stmt.setString(3, word.getVietnamese());
            stmt.setInt(4, typeId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- CÂU 4: Tìm kiếm & Lọc từ vựng bằng WordQueryBuilder ---
    public List<Word> searchWords(WordQueryBuilder builder) {
        List<Word> list = new ArrayList<>();
        String sql = builder.build();
        List<Object> params = builder.getParams();

        try (Connection conn = JdbcConnector.getInstance().connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán các tham số dấu ? từ QueryBuilder vào PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Word w = new Word.Builder()
                            .id(rs.getInt("id"))
                            .english(rs.getString("english"))
                            .pronunciation(rs.getString("pronunciation"))
                            .vietnamese(rs.getString("vietnamese"))
                            .wordType(rs.getString("wordType"))
                            .build();
                    list.add(w);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lấy danh sách loại từ (Noun, Verb, Adjective...) từ DB SQLite
    public List<WordType> getAllWordTypes() {
        List<WordType> list = new ArrayList<>();
        String sql = "SELECT id, name FROM word_type";

        try (Connection conn = JdbcConnector.getInstance().connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new WordType(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
