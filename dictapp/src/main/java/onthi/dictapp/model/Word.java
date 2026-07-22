/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp.model;

/**
 *
 * @author ASUS
 */
public class Word implements Cloneable {
    private int id;
    private String english;
    private String vietnamese;
    private String pronunciation;
    private String wordType; // Tên loại từ (Noun, Verb...)

    // Constructor private nhận vào Builder
    private Word(Builder builder) {
        this.id = builder.id;
        this.english = builder.english;
        this.vietnamese = builder.vietnamese;
        this.pronunciation = builder.pronunciation;
        this.wordType = builder.wordType;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getEnglish() { return english; }
    public String getVietnamese() { return vietnamese; }
    public String getPronunciation() { return pronunciation; }
    public String getWordType() { return wordType; }

    // --- PROTOTYPE PATTERN ---
    @Override
    public Word clone() {
        try {
            return (Word) super.clone();
        } catch (CloneNotSupportedException e) {
            // Fallback nếu không clone bằng super được
            return new Word.Builder()
                    .id(this.id)
                    .english(this.english)
                    .vietnamese(this.vietnamese)
                    .pronunciation(this.pronunciation)
                    .wordType(this.wordType)
                    .build();
        }
    }

    // --- BUILDER PATTERN ---
    public static class Builder {
        private int id;
        private String english;
        private String vietnamese;
        private String pronunciation;
        private String wordType;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder english(String english) {
            this.english = english;
            return this;
        }

        public Builder vietnamese(String vietnamese) {
            this.vietnamese = vietnamese;
            return this;
        }

        public Builder pronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
            return this;
        }

        public Builder wordType(String wordType) {
            this.wordType = wordType;
            return this;
        }

        public Word build() {
            return new Word(this);
        }
    }
}