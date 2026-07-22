/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.question;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Question implements Cloneable{
    private int id;
    private String content;
    private String hint;
    private String level;
    private String image;
    private String category;
    private List<Choice> choices;
    
    //cái cần cho builder khi tạo quest không tham số
    public Question(){}
    
    public Question(int id, String content, String hint, String level, String image, String category, List<Choice> choices) {
        this.id = id;
        this.content = content;
        this.hint = hint;
        this.level = level;
        this.image = image;
        this.category = category;
        this.choices = choices;
    }
    
    //Tạo builder các thuộc tính cho đối tượng question
    public Question(Builder builder) {
        this.id = builder.id;
        this.content = builder.content;
        this.hint = builder.hint;
        this.level = builder.level;
        this.category = builder.category;
        this.image = builder.image;
        this.choices = builder.choices;
    }
    
    @Override
    //sửa protected thành public
    public Object clone() {
        try {
            Question copy = (Question) super.clone();
            copy.choices = new ArrayList<>(this.choices);
            return copy;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }  

    //tạo lớp Builder mới dùng bên trên được nha + các hàm gán giá trị
    public static class Builder{
        private int id;
        private String content;
        private String hint;
        private String category;
        private String level;
        private String image;
        private List<Choice> choices = new ArrayList<>();
        
        public Builder id(int id){
            this.id = id;
            return this;
        }
        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }
        public Builder choices(List<Choice> choices){
            this.choices = choices;
            return this;
        } 
        //hàm build chốt sổ    
        public Question build(){
            return new Question(this);
        }
        
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
    
    
}
