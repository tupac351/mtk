package onthi.libapp.model;

// Implements Cloneable cho Pattern Prototype
public class Book implements Cloneable {
    private int id;
    private String title;
    private String author;
    private int publishedYear;
    private Category category;

    public Book() {}

    // Constructor nhận Builder
    public Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.publishedYear = builder.publishedYear;
        this.category = builder.category;
    }

    // Pattern Prototype: Clone bản sao
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    // --- Lớp Builder nằm bên trong ---
    public static class Builder {
        private int id;
        private String title;
        private String author;
        private int publishedYear;
        private Category category;

        public Builder id(int id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder author(String author) { this.author = author; return this; }
        public Builder publishedYear(int year) { this.publishedYear = year; return this; }
        public Builder category(Category category) { this.category = category; return this; }

        public Book build() {
            return new Book(this);
        }
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getPublishedYear() { return publishedYear; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    // Trả về tên danh mục để đưa lên TableView
    public String getCategoryName() { return category != null ? category.getName() : ""; }
}