/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;

import onthi.dictapp.model.Word;
import onthi.dictapp.model.WordType;
import onthi.dictapp.theme.Theme;
import onthi.dictapp.theme.ThemeFactory;
import onthi.dictapp.theme.ThemeFactoryProducer;
import onthi.dictapp.theme.ThemeType;
import onthi.dictapp.word.FileWordAdapter;
import onthi.dictapp.word.FileWordParser;
import onthi.dictapp.word.WordQueryBuilder;
import onthi.dictapp.word.WordRepository;
import onthi.dictapp.word.WordSource;

public class MainFrm extends Application {

    private TextField txtEnglish, txtPronun, txtVietnamese, txtSearch;
    private ComboBox<WordType> cbType, cbFilterType;
    private TableView<Word> tbWords;
    private WordRepository repository = new WordRepository();
    
    Button btnImportJson = new Button("Nhập từ file JSON");
    
    ComboBox<ThemeType> cbTheme = new ComboBox<>();
    private VBox mainLayout;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ỨNG DỤNG TỪ ĐIỂN ANH - VIỆT");

        Label lblTitle = new Label("QUẢN LÝ TỪ ĐIỂN ANH - VIỆT");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        cbTheme.getItems().setAll(ThemeType.values()); // Tự động nạp LIGHT, DARK
        cbTheme.setValue(ThemeType.LIGHT);
        // Bắt sự kiện đổi Theme
        cbTheme.setOnAction(e -> {
            ThemeType selectedType = cbTheme.getValue();

            // Dùng Producer lấy Factory tương ứng với Enum đã chọn
            ThemeFactory factory = ThemeFactoryProducer.getFactory(selectedType);
            Theme theme = factory.createTheme();

            // Đổi màu giao diện
            applyTheme(mainLayout, lblTitle, theme);
        });
        
        // --- Bổ sung nút Import JSON ---
    Button btnImportJson = new Button("Nhập từ file JSON");
    btnImportJson.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");

    // Gắn sự kiện khi click nút -> Gọi hàm handleImportJson
    btnImportJson.setOnAction(e -> handleImportJson(primaryStage));
    

        // --- 1. KHU VỰC NHẬP LIỆU (THÊM TỪ MỚI) ---
        txtEnglish = new TextField(); txtEnglish.setPromptText("Tiếng Anh (e.g. Hello)");
        txtPronun = new TextField(); txtPronun.setPromptText("Phát âm (e.g. /həˈləʊ/)");
        txtVietnamese = new TextField(); txtVietnamese.setPromptText("Nghĩa tiếng Việt");
        cbType = new ComboBox<>(); cbType.setPromptText("Chọn loại từ");

        GridPane gridInput = new GridPane();
        gridInput.setHgap(10); gridInput.setVgap(10);
        gridInput.add(new Label("Tiếng Anh:"), 0, 0); gridInput.add(txtEnglish, 1, 0);
        gridInput.add(new Label("Phát âm:"), 2, 0); gridInput.add(txtPronun, 3, 0);
        gridInput.add(new Label("Loại từ:"), 4, 0); gridInput.add(cbType, 5, 0);
        gridInput.add(new Label("Tiếng Việt:"), 0, 1); gridInput.add(txtVietnamese, 1, 1, 3, 1);

        Button btnAdd = new Button("Thêm Từ Mới");
        btnAdd.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAdd.setOnAction(e -> handleAddWord());

        HBox boxAdd = new HBox(10, gridInput, btnAdd, btnImportJson);
        boxAdd.setAlignment(Pos.CENTER_LEFT);

        // --- 2. KHU VỰC TÌM KIẾM & LỌC ---
        txtSearch = new TextField();
        txtSearch.setPromptText("Nhập từ tiếng Anh hoặc tiếng Việt...");
        txtSearch.setPrefWidth(250);

        cbFilterType = new ComboBox<>();
        cbFilterType.setPromptText("Tất cả loại từ");

        // Bắt sự kiện gõ phím tìm kiếm hoặc chọn lọc ComboBox
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> loadTableData());
        cbFilterType.setOnAction(e -> loadTableData());

HBox boxSearch = new HBox(15, new Label("Tìm kiếm:"), txtSearch, new Label("Lọc loại từ:"), cbFilterType, new Label("Giao diện:"), cbTheme);        
        
        boxSearch.setAlignment(Pos.CENTER_LEFT);

        // --- 3. BẢNG TABLEVIEW HIỂN THỊ ---
        tbWords = new TableView<>();
        setupTableView();

        // Nạp dữ liệu Loại từ vào các ComboBox
        loadWordTypes();
        // Nạp dữ liệu ban đầu lên Bảng
        loadTableData();

        mainLayout = new VBox(15, lblTitle, boxAdd, new Separator(), boxSearch, tbWords);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Thiết lập các cột cho TableView
    private void setupTableView() {
        TableColumn<Word, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Word, String> colEng = new TableColumn<>("Tiếng Anh");
        colEng.setCellValueFactory(new PropertyValueFactory<>("english"));
        colEng.setPrefWidth(180);

        TableColumn<Word, String> colPro = new TableColumn<>("Phát Âm");
        colPro.setCellValueFactory(new PropertyValueFactory<>("pronunciation"));
        colPro.setPrefWidth(120);

        TableColumn<Word, String> colType = new TableColumn<>("Loại Từ");
        colType.setCellValueFactory(new PropertyValueFactory<>("wordType"));
        colType.setPrefWidth(120);

        TableColumn<Word, String> colVie = new TableColumn<>("Nghĩa Tiếng Việt");
        colVie.setCellValueFactory(new PropertyValueFactory<>("vietnamese"));
        colVie.setPrefWidth(350);

        tbWords.getColumns().addAll(colId, colEng, colPro, colType, colVie);
    }

    // BƯỚC THỰC THI CÂU 3: Lấy dữ liệu ô nhập -> Thêm vào DB -> Load lại bảng
    private void handleAddWord() {
        String eng = txtEnglish.getText().trim();
        String pro = txtPronun.getText().trim();
        String vie = txtVietnamese.getText().trim();
        WordType selectedType = cbType.getValue();

        if (eng.isEmpty() || vie.isEmpty() || selectedType == null) {
            MyAlert.getInstance().showError("Vui lòng điền đủ Tiếng Anh, Tiếng Việt và Loại từ!");
            return;
        }

        Word word = new Word.Builder()
                .english(eng)
                .pronunciation(pro)
                .vietnamese(vie)
                .build();

        boolean success = repository.addWord(word, selectedType.getId());
        if (success) {
            MyAlert.getInstance().showMessage("Thêm từ mới thành công!");
            txtEnglish.clear();
            txtPronun.clear();
            txtVietnamese.clear();
            cbType.setValue(null);
            loadTableData(); // Refresh lại bảng hiển thị
        } else {
            MyAlert.getInstance().showError("Thêm từ thất bại!");
        }
    }

    // BƯỚC THỰC THI CÂU 4: Dùng QueryBuilder để lấy dữ liệu đổ lên TableView
    private void loadTableData() {
        String keyword = txtSearch.getText();
        WordType filterType = cbFilterType.getValue();
        Integer typeId = (filterType != null) ? filterType.getId() : null;

        WordQueryBuilder builder = new WordQueryBuilder()
                .keyword(keyword)
                .typeId(typeId);

        List<Word> list = repository.searchWords(builder);
        tbWords.setItems(FXCollections.observableArrayList(list));
    }

    // Đọc danh sách Loại từ nạp vào ComboBox
    private void loadWordTypes() {
        List<WordType> list = new ArrayList<>();
        String sql = "SELECT id, name FROM word_type";

        try (Connection conn = JdbcConnector.getInstance().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new WordType(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cbType.setItems(FXCollections.observableArrayList(list));
        cbFilterType.setItems(FXCollections.observableArrayList(list));
    }
    private WordRepository wordRepository = new WordRepository();

// Hàm này được gọi trong start() hoặc initialize() khi mở app
private void loadWordTypesToComboBox() {
    // 1. Gọi Repository lấy danh sách loại từ từ DB
    List<WordType> wordTypeList = wordRepository.getAllWordTypes();
    
    // 2. Chuyển List thành ObservableList và gán vào 2 ComboBox
    cbType.setItems(FXCollections.observableArrayList(wordTypeList));
    cbFilterType.setItems(FXCollections.observableArrayList(wordTypeList));
}
private void handleImportJson(Stage stage) {
    // 1. Kiểm tra loại từ trên ComboBox
    WordType selectedType = cbType.getValue();
    if (selectedType == null) {
        MyAlert.getInstance().showError("Vui lòng chọn Loại từ trước khi import file!");
        return;
    }

    // 2. Mở FileChooser chọn file JSON
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Chọn file từ vựng JSON");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json")
    );

    java.io.File selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile == null) return;

    // 3. Đọc dữ liệu thông qua Adapter Pattern
    FileWordParser parser = new FileWordParser(selectedFile.getAbsolutePath());
    WordSource source = new FileWordAdapter(parser, selectedType.getName());

    // 4. Lấy trực tiếp danh sách List<Word> từ Adapter
    List<Word> words = source.loadWords();

    if (words == null || words.isEmpty()) {
        MyAlert.getInstance().showError("File JSON rỗng hoặc không đúng cấu trúc!");
        return;
    }

    // 5. Lưu trực tiếp vào CSDL qua Repository (Không qua Facade nữa)
    boolean allSuccess = true;
    for (Word w : words) {
        boolean result = repository.addWord(w, selectedType.getId());
        if (!result) {
            allSuccess = false;
        }
    }

    // 6. Thông báo & Tải lại TableView
    if (allSuccess) {
        MyAlert.getInstance().showMessage("Import thành công " + words.size() + " từ vựng!");
        loadTableData(); // Refresh lại bảng
    } else {
        MyAlert.getInstance().showError("Có một số từ bị lỗi không thể lưu vào DB!");
    }
}
private void applyTheme(Pane root, Label title, Theme theme) {
    if (root != null && theme != null) {
        // Đổi màu nền chính
        root.setStyle("-fx-background-color: " + theme.getBgColor() + ";");
    }
    if (title != null && theme != null) {
        // Đổi màu chữ tiêu đề
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + theme.getTextColor() + ";");
    }
}
    public static void main(String[] args) {
        launch(args);
    }
}
