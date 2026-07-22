package onthi.libapp.book;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import onthi.libapp.JdbcConnector;
import onthi.libapp.model.Category;

public class CategoryFlyweightFactory {
    // Nơi lưu trữ cache (Tránh gọi DB nhiều lần)
    private static final Map<String, List<Category>> cache = new HashMap<>();

    public static List<Category> getCategories() {
        // Nếu cache đã có, trả về luôn -> Tiết kiệm tài nguyên (Đúng chuẩn Flyweight)
        if (cache.containsKey("ALL")) {
            System.out.println("Lấy danh mục từ Cache (Flyweight)");
            return cache.get("ALL");
        }

        // Nếu chưa có mới xuống DB lấy
        System.out.println("Lấy danh mục từ CSDL (Chưa có Cache)");
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (Connection conn = JdbcConnector.getInstance().connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
            // Lưu vào cache cho lần sau dùng
            cache.put("ALL", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}