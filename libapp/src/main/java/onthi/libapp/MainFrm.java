package onthi.libapp;

import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Import các package
import onthi.libapp.model.*;
import onthi.libapp.book.*;
import onthi.libapp.theme.*; // Import package theme vừa tạo

public class MainFrm extends Application {

    private BookRepository repo = new BookRepository();
    
    // --- KHAI BÁO BIẾN TOÀN CỤC ĐỂ DỄ ĐỔI MÀU ---
    private VBox root = new VBox(10); // Khung chứa tổng
    private Label lblTitle = new Label("Tên sách:");
    private Label lblAuthor = new Label("Tác giả:");
    private Label lblYear = new Label("Năm XB:");
    private Label lblCat = new Label("Thể loại:");
    private Label lblTheme = new Label("Giao diện:");
    
    private TextField txtTitle = new TextField();
    private TextField txtAuthor = new TextField();
    private TextField txtYear = new TextField();
    private ComboBox<Category> cbCategory = new ComboBox<>();
    private ComboBox<ThemeType> cbTheme = new ComboBox<>(); // ComboBox đổi Theme
    
    private Button btnAdd = new Button("Thêm thủ công");
    private Button btnJson = new Button("Thêm từ JSON");
    
    private TableView<Book> table = new TableView<>();

    @Override
    public void start(Stage stage) {
        // --- PHẦN 1: TẠO FORM NHẬP LIỆU ---
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10); formPane.setVgap(10);

        formPane.add(lblTitle, 0, 0);  formPane.add(txtTitle, 1, 0);
        formPane.add(lblAuthor, 0, 1); formPane.add(txtAuthor, 1, 1);
        formPane.add(lblYear, 0, 2);   formPane.add(txtYear, 1, 2);
        formPane.add(lblCat, 0, 3);    formPane.add(cbCategory, 1, 3);
        formPane.add(lblTheme, 0, 4);  formPane.add(cbTheme, 1, 4); // Thêm chọn Theme vào form

        // Load danh mục (Flyweight)
        List<Category> catList = CategoryFlyweightFactory.getCategories();
        cbCategory.getItems().addAll(catList);

        // Load danh sách Theme vào ComboBox
        cbTheme.getItems().addAll(ThemeType.values());
        cbTheme.setValue(ThemeType.DEFAULT); // Gán giá trị ban đầu

        // Hàng nút bấm
        HBox btnBox = new HBox(10, btnAdd, btnJson);
        formPane.add(btnBox, 1, 5);

        // --- PHẦN 2: TABLEVIEW ---
        TableColumn<Book, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Book, String> colTitle = new TableColumn<>("Tên sách");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> colAuthor = new TableColumn<>("Tác giả");
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> colCat = new TableColumn<>("Thể loại");
        colCat.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        table.getColumns().addAll(colId, colTitle, colAuthor, colCat);
        loadTableData();

        // --- PHẦN 3: XỬ LÝ SỰ KIỆN ---

        // Sự kiện: BẤM NÚT ĐỔI THEME (ABSTRACT FACTORY CHẠY Ở ĐÂY)
        cbTheme.setOnAction(e -> {
            changeTheme(cbTheme.getValue());
        });

        btnAdd.setOnAction(e -> {
            try {
                Book newBook = new Book.Builder()
                        .title(txtTitle.getText())
                        .author(txtAuthor.getText())
                        .publishedYear(Integer.parseInt(txtYear.getText()))
                        .category(cbCategory.getValue())
                        .build();

                if (repo.addBook(newBook)) {
                    MyAlert.getInstance().showInfo("Thêm sách thành công!");
                    loadTableData();
                }
            } catch (Exception ex) {
                MyAlert.getInstance().showError("Lỗi nhập liệu!");
            }
        });

        btnJson.setOnAction(e -> {
            BookSource source = new JsonBookAdapter("data/books.json");
            List<Book> booksFromJson = source.loadBooks();
            for (Book b : booksFromJson) { repo.addBook(b); }
            MyAlert.getInstance().showInfo("Đã thêm thành công từ JSON!");
            loadTableData();
        });

        // --- HIỂN THỊ ---
        root.getChildren().addAll(formPane, table);
        root.setPadding(new Insets(10));
        
        // Gọi hàm đổi Theme lần đầu để áp dụng màu DEFAULT
        changeTheme(ThemeType.DEFAULT);

        Scene scene = new Scene(root, 550, 550);
        stage.setTitle("Quản Lý Thư Viện - Full Pattern");
        stage.setScene(scene);
        stage.show();
    }

    // --- HÀM LÕI CỦA ABSTRACT FACTORY ---
    private void changeTheme(ThemeType selectedTheme) {
        ThemeFactory factory; // Gọi Interface Bản thiết kế

        // Dựa vào Enum để tạo ra đúng cái Factory cần dùng
        switch (selectedTheme) {
            case DARK:
                factory = new DarkThemeFactory();
                break;
            case LIGHT:
                factory = new LightThemeFactory();
                break;
            default:
                factory = new DefaultThemeFactory();
                break;
        }

        // Lấy màu từ Factory (Không cần biết nó đang là Dark hay Light)
        String bgColor = factory.getBgColor();
        String labelColor = factory.getLabelColor();
        String btnColor = factory.getBtnColor();
        String btnTextColor = factory.getBtnTextColor();

        // Ép màu (Style CSS) vào các thẻ JavaFX
        root.setStyle("-fx-background-color: " + bgColor + ";");
        
        String lblStyle = "-fx-text-fill: " + labelColor + "; -fx-font-weight: bold;";
        lblTitle.setStyle(lblStyle);
        lblAuthor.setStyle(lblStyle);
        lblYear.setStyle(lblStyle);
        lblCat.setStyle(lblStyle);
        lblTheme.setStyle(lblStyle);
        
        String btnStyle = "-fx-background-color: " + btnColor + "; -fx-text-fill: " + btnTextColor + ";";
        btnAdd.setStyle(btnStyle);
        btnJson.setStyle(btnStyle);
    }

    private void loadTableData() {
        List<Book> list = repo.getAllBooks();
        table.setItems(FXCollections.observableArrayList(list));
    }

    public static void main(String[] args) {
        launch(args);
    }
}