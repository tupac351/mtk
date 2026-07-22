/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import onthi.quizzapp.JdbcConnector;
import onthi.quizzapp.MyAlert;

/**
 *
 * @author ASUS
 */
public class QuestionRepository {
    public List<Question> getQuestionByFilter(String keyword, Integer categoryId, Integer levelId, int limit) {
        List<Question> questions = new ArrayList<>();

        QuestionQueryBuilder queryBuilder = new QuestionQueryBuilder()
                .keyword(keyword)
                .categoryId(categoryId)
                .levelId(levelId)
                .limit(limit);

        String sql = queryBuilder.build();
        System.out.println("SQL: " + sql);
        System.out.println("Params size: " + queryBuilder.getParams().size());

        try (Connection conn = JdbcConnector.getInstance().connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            int idx = 1;
            for (Object param : queryBuilder.getParams()) {
                stmt.setObject(idx, param);
                idx++;
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int questionId = rs.getInt("id");
                    List<Choice> choices = getChoices(questionId);
                    Question q = new Question.Builder()
                            .category(rs.getString("category"))
                            .content(rs.getString("content"))
                            .hint(rs.getString("hint"))
                            .id(questionId)
                            .choices(choices)
                            .level(rs.getString("level")).build();

                    questions.add(q);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuestionRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return questions;
    }
    private List<Choice> getChoices(int questionId) {
        List<Choice> choices = new ArrayList<>();
        String sql = "SELECT c.content, c.is_correct FROM choice c WHERE c.question_id = ?";

        try (Connection conn = JdbcConnector.getInstance().connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, questionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Choice choice = new Choice(rs.getString("content"), rs.getInt("is_correct") == 1);
                    choices.add(choice);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuestionRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return choices;
    }
    public static Integer getCategoryId(String category) {
        switch (category) {
            case "Grammar":
                return 1;
            case "Vocabulary":
                return 2;
            case "Reading":
                return 3;
            default:
                return null;
        }
    }

    public static Integer getLevelId(String level) {
        switch (level) {
            case "Easy":
                return 1;
            case "Medium":
                return 2;
            case "Hard":
                return 3;
            default:
                return null;
        }
    }
    
    //Câu 6 lưu thao tác với database:
    private void insertChoice(Connection conn, Choice choice, int questionId) throws SQLException {
        String sql = "INSERT INTO choice(content,is_correct,question_id) "
                + " VALUES (?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, choice.getContent());
        stmt.setInt(2, choice.getIsCorrect() ? 1 : 0);
        stmt.setInt(3, questionId);
    }
    public boolean addQuestionToDB(Connection conn, Question question) throws SQLException {

        String sqlConn = " INSERT INTO question(content,hint,image,category_id,level_id) "
                + "VALUES (?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlConn, Statement.RETURN_GENERATED_KEYS);

        int categoryId = getCategoryId(question.getCategory());
        int levelId = getLevelId(question.getLevel());

        stmt.setString(1, question.getContent());
        stmt.setString(2, question.getHint());
        stmt.setString(3, question.getImage());
        stmt.setInt(4, categoryId);
        stmt.setInt(5, levelId);

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();

        int questionId = -1;

        if (rs.next()) {
            questionId = rs.getInt(1);
        }

        if (questionId == -1) {
            return false;
        }

        // Insert choice
        for (Choice choice : question.getChoices()) {
            insertChoice(conn, choice, questionId);
        }

        return true;
    }
    public boolean addQuesstion(List<Question> questions) {
        Boolean result = true;
        try (Connection conn = JdbcConnector.getInstance().connect()) {
            for (Question question : questions) {
                result = this.addQuestionToDB(conn, question);
                if(!result){
                    MyAlert.getInstance().showError("Thêm câu hỏi không thành công.");
                    break;
                }
            }
            return result;
        } 
        catch (Exception e) {
        }
        return result;
    }
}
