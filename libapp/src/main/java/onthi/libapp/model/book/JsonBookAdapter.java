package onthi.libapp.book;

import java.util.ArrayList;
import java.util.List;
import onthi.libapp.model.Book;
import onthi.libapp.model.Category;

// Lớp này đóng vai trò Adapter chuyển đổi từ File Json sang List<Book>
public class JsonBookAdapter implements BookSource {
    private String filePath;

    public JsonBookAdapter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Book> loadBooks() {
        List<Book> list = new ArrayList<>();
        // Trong phòng thi không có mạng tải thư viện JSON, ta tạo dữ liệu cứng giả lập file json.
        // Nếu đề thi cho sẵn file và thư viện GSON thì bạn dùng BufferedReader để đọc nhé.
        System.out.println("Đang phân tích (Parse) dữ liệu từ file: " + filePath);
        
        Category cat = new Category(1, "IT"); // Mặc định gán danh mục 1
        
        // Dùng Builder
        Book b1 = new Book.Builder().title("Java Cơ Bản").author("Thầy Thành").publishedYear(2023).category(cat).build();
        Book b2 = new Book.Builder().title("Design Pattern").author("Thầy Thành").publishedYear(2024).category(cat).build();
        
        list.add(b1);
        list.add(b2);
        return list;
    }
}