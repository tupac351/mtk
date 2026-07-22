/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author ASUS
 */
public class MyAlert {
    private static MyAlert instance;
    
    private Alert alert;
    
    private MyAlert(){
        alert = new Alert(Alert.AlertType.INFORMATION);
    }
    
    public static MyAlert getInstance(){
        if(instance == null)
            instance = new MyAlert();
        return instance;
    }
    
    public Optional<ButtonType> showMessage(String message){
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz App");
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        return alert.showAndWait();
    }
    
    public Optional<ButtonType> showError(String message){
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setTitle("Quiz App");
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
