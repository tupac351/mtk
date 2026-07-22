/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.theme;

/**
 *
 * @author ASUS
 */
public class LightTheme implements ThemeFactory {

    @Override
    public String getBackgroundStyle() {
        return "-fx-background-color:white;";
    }

    @Override
    public String getTitleStyle() {
        return "-fx-text-fill:#FFD700;"
                + "-fx-font-size:36px;"
                + "-fx-font-weight:bold;";
    }

    @Override
    public String getButtonStyle() {
        return "-fx-background-color:#E0E0E0;"
                + "-fx-text-fill:black;"
                + "-fx-font-size:16px;"
                + "-fx-font-weight:bold;";
    }

    @Override
    public String getTextFieldStyle() {
        return "-fx-background-color:white;"
                + "-fx-text-fill:black;";
    }
}
