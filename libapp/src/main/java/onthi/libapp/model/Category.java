package onthi.libapp.model;

public class Category {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    // Ghi đè hàm này để ComboBox hiển thị chữ thay vì địa chỉ bộ nhớ
    @Override
    public String toString() {
        return name;
    }
}