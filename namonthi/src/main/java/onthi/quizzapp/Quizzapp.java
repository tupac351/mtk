/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package onthi.quizzapp;

import java.io.File;
import onthi.quizzapp.question.QuestionRepository;
import onthi.quizzapp.question.FileQuestionParser;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import onthi.quizzapp.question.Choice;
import onthi.quizzapp.question.FileQuestionAdapter;
import onthi.quizzapp.question.Question;
import onthi.quizzapp.question.QuestionFacade;
import onthi.quizzapp.question.QuestionQueryBuilder;
import onthi.quizzapp.question.QuestionSource;
import onthi.quizzapp.theme.DarkTheme;
import onthi.quizzapp.theme.DefaultTheme;
import onthi.quizzapp.theme.LightTheme;
import onthi.quizzapp.theme.Theme;
import static onthi.quizzapp.theme.Theme.DARK;
import static onthi.quizzapp.theme.Theme.DEFAULT;
import static onthi.quizzapp.theme.Theme.LIGHT;
import onthi.quizzapp.theme.ThemeFactory;
import onthi.quizzapp.theme.ThemeFactoryProducer;

/**
 *
 * @author ASUS
 */
public class Quizzapp extends Application{
    private ThemeFactory currTheme; //đối tượng đang dàng
    private Theme currentThemeType = Theme.DEFAULT; //lưu trạng thái của đối tượng
    private Stage mainStage;
    ComboBox cbCategory = new ComboBox();
    ComboBox cbLevel = new ComboBox();
    
    private List<Question> lstQuestions = new ArrayList<>();
    private int currQuestion = 0;
    QuestionSource source ;
    QuestionFacade facade = new QuestionFacade();
    
    
    
    ComboBox cbSearchCategories = new ComboBox();
    ComboBox cbSearchLevel = new ComboBox();
    TextField txtSearch = new TextField();

    private TableView tbvQuestion = new TableView();
    private ObservableList<Question> data = FXCollections.observableArrayList();

    
    
    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        this.mainStage.setTitle("Quiz App");
        currTheme = ThemeFactoryProducer.getFactory(currentThemeType);
        showHome();
        this.mainStage.show();


    }
    // ================== MÀN HÌNH CHÍNH KHI MỞ APP ==================
    private void showHome(){
        Label label = new Label("QUIZ APP");
        label.setStyle(currTheme.getTitleStyle());

        Button btnQuestion = new Button("Quản lí câu hỏi");
        btnQuestion.setStyle(currTheme.getButtonStyle());
        Button btnPratice = new Button("Luyện tập");
        btnPratice.setStyle(currTheme.getButtonStyle());
        Button btnExam = new Button("Luyện thi");
        btnExam.setStyle(currTheme.getButtonStyle());
        Button btnExit = new Button("Thoát chương trình");
        btnExit.setStyle(currTheme.getButtonStyle());
        
        ComboBox<Theme> cbTheme = new ComboBox<>(FXCollections.observableArrayList(Theme.values()));
        cbTheme.setValue(currentThemeType);
        cbTheme.setPrefWidth(250);
        cbTheme.setStyle(currTheme.getButtonStyle());
        
        btnQuestion.setPrefWidth(250);
        btnPratice.setPrefWidth(250);
        btnExam.setPrefWidth(250);
        btnExit.setPrefWidth(250);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(label, btnQuestion, btnPratice, btnExam, cbTheme, btnExit);
        root.setStyle(currTheme.getBackgroundStyle());


        cbTheme.setOnAction(e -> {
            currentThemeType = cbTheme.getValue();
            currTheme = ThemeFactoryProducer.getFactory(currentThemeType);
            showHome();

        });
        

        //NÚT QUẢN LÝ CÂU HỎI
        btnQuestion.setOnAction(e -> {
            showQuestionMgtFrm();
        });
        
        btnPratice.setOnAction(e ->{
            showPracticeFrm();       
        });
        
        
        btnExit.setOnAction(e -> {
            mainStage.close();
            System.exit(0);
        });
        
        Scene scene = new Scene(root, 640, 480);
        mainStage.setScene(scene);

    }
    // ================== MÀN HÌNH QUẢN LÝ CÂU HỎI ================== 
    private void showQuestionMgtFrm() {
        Label title = new Label("QUẢN LÝ CÂU HỎI");
        title.setStyle(currTheme.getTitleStyle());

        TextField txtContent = new TextField();
        txtContent.setMaxWidth(500);
        txtContent.setPromptText("Nhập nội dung câu hỏi");

        TextField txtHint = new TextField();
        txtHint.setPromptText("Gợi ý trả lời");
        txtHint.setMaxWidth(500);
        
        cbCategory.getItems().addAll("Danh mục", "Grammar", "Vocabulary", "Reading");
        cbCategory.setValue("Danh mục");
        cbCategory.setStyle(currTheme.getButtonStyle());

        cbLevel.getItems().addAll("Độ khó", "Easy", "Medium", "Hard");
        cbLevel.setValue("Độ khó");
        cbLevel.setStyle(currTheme.getButtonStyle());

        HBox hbDanhMuc = new HBox(10, cbCategory, cbLevel);
        

        ToggleGroup correctGroup = new ToggleGroup();

        RadioButton rdA = new RadioButton();
        rdA.setToggleGroup(correctGroup);
        TextField txtAnswerA = new TextField();
        txtAnswerA.setPromptText("Đáp án A");

        RadioButton rdB = new RadioButton();
        rdB.setToggleGroup(correctGroup);
        TextField txtAnswerB = new TextField();
        txtAnswerB.setPromptText("Đáp án B");

        HBox row1 = new HBox(10, rdA, txtAnswerA, rdB, txtAnswerB);

        RadioButton rdC = new RadioButton();
        rdC.setToggleGroup(correctGroup);
        TextField txtAnswerC = new TextField();
        txtAnswerC.setPromptText("Đáp án C");

        RadioButton rdD = new RadioButton();
        rdD.setToggleGroup(correctGroup);
        TextField txtAnswerD = new TextField();
        txtAnswerD.setPromptText("Đáp án D");

        rdA.setUserData("A");
        rdB.setUserData("B");
        rdC.setUserData("C");
        rdD.setUserData("D");

        HBox row2 = new HBox(10, rdC, txtAnswerC, rdD, txtAnswerD);

        Button btnBack = new Button("Quay lại");
        btnBack.setStyle(currTheme.getButtonStyle());
        btnBack.setOnAction(eh -> showHome());

        Button btnAddQuestion = new Button("Thêm câu hỏi");
        btnAddQuestion.setStyle(currTheme.getButtonStyle());
        btnAddQuestion.setOnAction(eh -> {
            //điều kiện tiên quyết
            if (txtContent.getText().trim().isEmpty() || txtHint.getText().trim().isEmpty()) {
                MyAlert.getInstance().showError("Vui lòng nhập câu hỏi và gợi ý.");
                return;
            }

            if (txtAnswerA.getText().trim().isEmpty() || txtAnswerB.getText().trim().isEmpty()) {
                MyAlert.getInstance().showError("Vui lòng nhập đáp án.");
                return;
            }

            if (txtAnswerC.getText().trim().isEmpty() || txtAnswerD.getText().trim().isEmpty()) {
                MyAlert.getInstance().showError("Vui lòng nhập đáp án.");
                return;
            }

            if (correctGroup.getSelectedToggle() == null) {
                MyAlert.getInstance().showError("Vui lòng chọn đáp án đúng.");
                return;
            }

            if (cbCategory.getValue() == null || cbLevel.getValue() == null) {
                MyAlert.getInstance().showError("Vui long chon danh muc");
                return;
            }
            //Lấy dữ liệu từ UI để tạo đối tượng question
            String category = cbCategory.getValue().toString();
            String level = cbLevel.getValue().toString();
            String correctAnswer = correctGroup.getSelectedToggle().getUserData().toString();

            ArrayList<Choice> choices = new ArrayList<>();
            choices.add(new Choice(txtAnswerA.getText().trim(), correctAnswer.equals("A")));
            choices.add(new Choice(txtAnswerB.getText().trim(), correctAnswer.equals("B")));
            choices.add(new Choice(txtAnswerC.getText().trim(), correctAnswer.equals("C")));
            choices.add(new Choice(txtAnswerD.getText().trim(), correctAnswer.equals("D")));

            try (Connection conn = JdbcConnector.getInstance().connect()) {
                // Tạo thanh Question
                Question.Builder builder = new Question.Builder()
                        .category(category)
                        .choices(choices)
                        .level(level)
                        .content(txtContent.getText().trim())
                        .hint(txtHint.getText().trim());

                Question question = new Question(builder);

                QuestionRepository repository = new QuestionRepository();
                boolean result = repository.addQuestionToDB(conn, question);
                if (result) {
                    MyAlert.getInstance().showMessage("Thêm câu hỏi thành công.");
                    txtAnswerA.clear();
                    txtContent.clear();
                    txtHint.clear();
                    txtAnswerB.clear();
                    txtAnswerC.clear();
                    txtAnswerD.clear();
                    cbCategory.setValue(null);
                    cbLevel.setValue(null);
                } else {
                    MyAlert.getInstance().showMessage("Thêm câu hỏi không thành công.");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Quizzapp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //nút thêm câu hỏi từ file
        Button btnAddFrFile = new Button("Thêm câu hỏi từ file");
        btnAddFrFile.setStyle("-fx-font-size:14px;-fx-background-color: coral");

        btnAddFrFile.setOnAction(eh -> {
            importQuestionFromFile(); //hàm dưới xử lý thêm câu hỏi từ file, sau đó hiển thị ra trang chủ
        });
        
        HBox btnGrp = new HBox(15, btnBack, btnAddQuestion, btnAddFrFile);
        
        txtSearch.setOnAction(eh -> {
            loadQuestion();
        });

        cbSearchLevel.getItems().addAll("Tất cả", "Easy", "Medium", "Hard");
        cbSearchLevel.setValue("Tất cả");
        cbSearchLevel.setOnAction(e -> {
            loadQuestion();
        });
        cbSearchCategories.getItems().addAll("Tất cả", "Grammar", "Vocabulary", "Reading");
        cbSearchCategories.setValue("Tất cả");
        cbSearchCategories.setOnAction(e -> {
            loadQuestion();
        });
        HBox rowSearch = new HBox(10, txtSearch, cbSearchCategories, cbSearchLevel);

        //hiển thị table
        initTable();
        loadQuestion();
        
        
        //canvas hiển thị
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(title, 
    txtContent, 
    txtHint, 
    hbDanhMuc,
    row1, 
    row2,
    btnBack,btnGrp,rowSearch,tbvQuestion);
        mainLayout.setStyle(currTheme.getBackgroundStyle());


        Scene scene = new Scene(mainLayout, 640, 520);
        mainStage.setScene(scene);
    }

// ================== MÀN HÌNH LUYỆN TẬP NÚT 1 ================== 
    private void showPracticeFrm(){
        Label title = new Label("Thiết lập Câu hỏi");
        title.setAlignment(Pos.CENTER);
        title.setStyle(currTheme.getTitleStyle());


        TextField txtKeyWord = new TextField();
        txtKeyWord.setPromptText("Nhập từ khóa");
        txtKeyWord.setMaxWidth(300);

        ComboBox<String> cbLevel = new ComboBox<>();
        cbLevel.getItems().addAll("Tất cả", "Easy", "Medium", "Hard");
        cbLevel.setValue("Tất cả");
        cbLevel.setStyle(currTheme.getButtonStyle());


        ComboBox<String> cbCategory = new ComboBox<>();
        cbCategory.getItems().addAll("Tất cả", "Grammar", "Vocabulary", "Reading");
        cbCategory.setValue("Tất cả");
        cbCategory.setStyle(currTheme.getButtonStyle());


        TextField txtNumber = new TextField();
        txtNumber.setPromptText("Nhập số câu hỏi ");
        
        //hai nút chức năng thoát + bắt đầu luyện tập
        Button btnBack = new Button("Quay lại");
        btnBack.setStyle(currTheme.getButtonStyle());
        btnBack.setOnAction(e -> {
            showHome();
        });
        
        
        Button btnStart = new Button("Bắt đầu luyện tập");
        btnStart.setStyle(currTheme.getButtonStyle());
        btnStart.setOnAction(e->{
            if (txtNumber.getText().trim().isEmpty()) {
                MyAlert.getInstance().showMessage("Vui long nhap so cau hoi.");
                return;
            }
            //chuyển đổi dữ liệu từ UI sang dữ liệu valid
            Integer number = Integer.valueOf(txtNumber.getText().trim());
            Integer categoryId = QuestionRepository.getCategoryId(cbCategory.getValue());
            Integer levelId = QuestionRepository.getLevelId(cbLevel.getValue());
            
            //tạo thùng chứa, chứa list câu hỏi
            QuestionRepository repo = new QuestionRepository();
            lstQuestions = repo.getQuestionByFilter(txtKeyWord.getText(), categoryId, levelId, number);
            if (lstQuestions.isEmpty()) {
                MyAlert.getInstance().showMessage("Khong co cau hoi duoc chon.");
                return;
            }
            //hiển thị UI luyện tập
            showPracticeQuestion();
        });
        
        VBox root = new VBox(15);
        root.getChildren().addAll(title, txtKeyWord, cbLevel, cbCategory, txtNumber, btnBack, btnStart);
        root.setStyle(currTheme.getBackgroundStyle());
        root.setPadding(new Insets(20));
        Scene scene = new Scene(root, 640, 480);
        this.mainStage.setScene(scene);
}
    private void showPracticeQuestion(){
        Label label = new Label("Câu hỏi: " + (currQuestion + 1) + "/" + lstQuestions.size());
        label.setStyle(currTheme.getTitleStyle());
        final Question question = lstQuestions.get(currQuestion);

        Label lblContent = new Label(question.getContent());
        lblContent.setPrefWidth(500);
        lblContent.setWrapText(true);

        // Hiển thị danh sách các đáp án
        VBox answerBox = new VBox(10);
        answerBox.setAlignment(Pos.TOP_LEFT);

        ToggleGroup correctGroup = new ToggleGroup();
        for (Choice choice : question.getChoices()) {
            RadioButton rd = new RadioButton(choice.getContent());
            rd.setUserData(choice);
            rd.setToggleGroup(correctGroup);
            answerBox.getChildren().add(rd);
        }

        Label lblResult = new Label();
        lblResult.setStyle(currTheme.getTitleStyle());

        // Button: Kiểm tra đáp án, và Next: Chuyển câu hỏi        
        Button btnCheck = new Button("Kiểm tra đáp án");
        btnCheck.setStyle(currTheme.getButtonStyle());
        btnCheck.setOnAction(e -> {
            RadioButton selected = (RadioButton) correctGroup.getSelectedToggle();

            if (selected == null) {
                lblResult.setText("Vui lòng chọn một đáp án");
                return;
            }

            Choice choice = (Choice) selected.getUserData();
            if (choice.getIsCorrect()) {
                lblResult.setText("Chính xác.");
            } else {
                lblResult.setText("Chưa chính xác");
            }
        });

        Button btnNext = new Button("Tiếp theo");
        btnNext.setStyle(currTheme.getButtonStyle());
        btnNext.setOnAction(e -> {
            currQuestion++;
            if (currQuestion >= lstQuestions.size()) {
                MyAlert.getInstance().showMessage("Bạn đã hoàn thành luyện tập");
            } else {
                showPracticeQuestion();
            }
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(20));
        root.setStyle(currTheme.getBackgroundStyle());
        root.getChildren().addAll(label, lblContent, lblResult, answerBox, btnCheck, btnNext);

        Scene scene = new Scene(root, 640, 480);
        this.mainStage.setScene(scene);
        this.mainStage.show();
    }
    
    
    //CÁC HÀM CHO THAO TÁC VỚI DB
    private void importQuestionFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Json File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.json")
        );
        
        if(cbCategory.getValue() == null || "Danh mục".equals(cbCategory.getValue().toString())){
            MyAlert.getInstance().showError("Vui lòng chọn loại danh mục");
            return;
        }
        
        if(cbLevel.getValue() == null || "Độ khó".equals(cbLevel.getValue().toString())){
            MyAlert.getInstance().showError("Vui lòng chọn độ khó");
            return;
        }
        
        File selectedFile = fileChooser.showOpenDialog(mainStage);

        if (selectedFile == null) {
            return;
        }

        FileQuestionParser parser = new FileQuestionParser(selectedFile.getAbsolutePath());        
        source = new FileQuestionAdapter(parser, cbLevel.getValue().toString(), cbCategory.getValue().toString());
        
        boolean success = facade.importQuestion(source);
        
        if(!success){
            MyAlert.getInstance().showError("Không thể nhập câu hỏi từ JSON");
        }
        else{
            MyAlert.getInstance().showMessage("Nhập câu hỏi thành công");
        }
        
        loadQuestion();
    }
    
    private void loadQuestion() {
        QuestionRepository repository = new QuestionRepository();
        Integer categoriesId = null;
        Integer levelId = null;
        if (cbSearchCategories.getValue() != null) {
            categoriesId = QuestionRepository.getCategoryId(cbSearchCategories.getValue().toString());
        }

        if (cbSearchLevel.getValue() != null) {
            levelId = QuestionRepository.getLevelId(cbSearchLevel.getValue().toString());
        }

        List<Question> lstQuestions = repository.getQuestionByFilter(txtSearch.getText().trim(),
                categoriesId, levelId, 1000);
        data = FXCollections.observableArrayList(lstQuestions);
        tbvQuestion.setItems(data);

    }
        private void initTable() {
        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory<Question, Integer>("id"));

        TableColumn contentCol = new TableColumn("Content");
        contentCol.setCellValueFactory(new PropertyValueFactory<Question, String>("content"));

        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<Question, String>("category"));

        TableColumn levelCol = new TableColumn("Level");
        levelCol.setCellValueFactory(new PropertyValueFactory<Question, String>("level"));

        tbvQuestion.setItems(data);
        tbvQuestion.getColumns().addAll(idCol, contentCol, categoryCol, levelCol);

    }
    
    
    public static void main(String[] args) {
        
        
        
          System.out.println("--- TEST SINGLETON ---");
        // Gọi thử 2 lần getInstance
        JdbcConnector db1 = JdbcConnector.getInstance();
        JdbcConnector db2 = JdbcConnector.getInstance();
        
        // Kiểm tra xem db1 và db2 có phải là CÙNG MỘT đối tượng trong bộ nhớ không
        if (db1 == db2) {
            System.out.println("Thành công: Singleton hoạt động đúng! db1 và db2 là một.");
        } else {
            System.out.println("Thất bại: Singleton sai rồi, nó tạo ra 2 đối tượng khác nhau.");
        }
        
        
        
        System.out.println("\n--- TEST KẾT NỐI DATABASE ---");
        try {
            Connection ketNoi = db1.connect();
            
            if (ketNoi != null) {
                System.out.println("Ket noi csdl thanh cong");
            }
            
        } catch (SQLException e) {
            System.out.println("Ket noi csdl that bai");
            e.printStackTrace(); // In ra lỗi màu đỏ để biết sai ở đâu
        }
        System.out.println("\n--- TEST BUILDER + PROTOTYPE ---");

        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice("Paris", true));
        choices.add(new Choice("London", false));
        choices.add(new Choice("Berlin", false));
        choices.add(new Choice("Madrid", false));
        // Builder
        Question q1 = new Question.Builder()
                .id(1)
                .content("Thu do nuoc Phap la")
                .hint("Bat dau bang chu P")
                .category("Geography")
                .level("Easy")
                .choices(choices)
                .build();

            // Prototype - clone bản gốc
        Question q2 = (Question)q1.clone();
        q2.setId(2);
        q2.setContent("Thu do nuoc Phap ban sao 2");

        System.out.println("q1: " + q1.getId() + " - " + q1.getContent());
        System.out.println("q2: " + q2.getId() + " - " + q2.getContent());
        
        //Kiểm tra sql query
        QuestionQueryBuilder queryBuilder = new QuestionQueryBuilder()
                .keyword("xin chao") // Test có chữ xin chào
                .categoryId(2) // Test chọn danh mục số 2
                .levelId(2) // Test chọn level số 2
                .limit(3);             // Test lấy 3 câu
        String sql = queryBuilder.build();
        System.out.println("SQL: " + sql);
        System.out.println("Params size: " + queryBuilder.getParams().size());
        
        launch(args);
    }
}
    