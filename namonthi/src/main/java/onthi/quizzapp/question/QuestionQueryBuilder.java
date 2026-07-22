/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import onthi.quizzapp.JdbcConnector;

/**
 *
 * @author ASUS
 */
public class QuestionQueryBuilder {
    private Integer categoryId;
    private Integer levelId;
    private String keyWord;
    
    private int limit = 5;
    private List<Object> params;
    
    public QuestionQueryBuilder() {
        params = new ArrayList<>();
    }

    public QuestionQueryBuilder(Integer categoryId, Integer levelId, String keyWord) {
        params = new ArrayList<>();
        this.categoryId = categoryId;
        this.levelId = levelId;
        this.keyWord = keyWord;
    }
    public String build(){
        StringBuilder sqlBuilder =new StringBuilder();
        sqlBuilder.append(" SELECT q.id, q.content, q.hint, q.image, "
                + "c.name as category, lv.name as level");
        sqlBuilder.append(" FROM question q ");
        sqlBuilder.append(" INNER JOIN category c ON q.category_id = c.id ");
        sqlBuilder.append(" INNER JOIN level lv ON q.level_id = lv.id ");
        sqlBuilder.append(" WHERE 1 = 1");
        
        if(keyWord != null && !keyWord.trim().isEmpty()){
            sqlBuilder.append(" AND q.content LIKE ?");
            params.add("%" + keyWord.trim() +"%");
        }
        
        if(categoryId != null){
            sqlBuilder.append(" AND q.category_id = ?");
            params.add(categoryId);
        }
            
        if(levelId != null){
            sqlBuilder.append(" AND q.level_id = ?");
            params.add(levelId);
        }
        
        sqlBuilder.append(" ORDER BY q.id LIMIT ?");
        params.add(limit);
        
        return sqlBuilder.toString();
    }

    public List<Object> getParams() {
        return params;
    }
    
    public QuestionQueryBuilder keyword(String keyword){
        this.keyWord = keyword;
        return this;
    }

    public QuestionQueryBuilder categoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }
    
    public QuestionQueryBuilder levelId(Integer levelId){
        this.levelId = levelId;
        return this;
    }
    
    public QuestionQueryBuilder limit(int limit){
        this.limit = limit;
        return this;
    } 
    
}
