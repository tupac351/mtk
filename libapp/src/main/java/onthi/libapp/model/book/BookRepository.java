package onthi.libapp.book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import onthi.libapp.JdbcConnector;
import onthi.libapp.model.Book;
import onthi.libapp.model.Category;

public class BookRepository {
    
    // Đổ danh sách lên TableView
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.author, b.published_year, c.id as cat_id, c.name as cat_name "
                   + "FROM book b INNER JOIN category c ON b.category_id = c.id";
                   
        try (Connection conn = JdbcConnector.getInstance().connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Category cat = new Category(rs.getInt("cat_id"), rs.getString("cat_name"));
                
                // Dùng Builder tạo Book
                Book b = new Book.Builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .author(rs.getString("author"))
                        .publishedYear(rs.getInt("published_year"))
                        .category(cat)
                        .build();
                books.add(b);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return books;
    }

    // Thêm sách mới
    public boolean addBook(Book book) {
        String sql = "INSERT INTO book(title, author, published_year, category_id) VALUES (?,?,?,?)";
        try (Connection conn = JdbcConnector.getInstance().connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setInt(4, book.getCategory().getId());
            
            return stmt.executeUpdate() > 0; // Trả về true nếu insert thành công
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
}