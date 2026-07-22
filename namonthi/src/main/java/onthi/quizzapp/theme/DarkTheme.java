/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.theme;

public class DarkTheme implements ThemeFactory {

    @Override
    public String getBackgroundStyle() {
        return "-fx-background-color:#121212;";
    }

    @Override
    public String getTitleStyle() {
        return "-fx-text-fill:yellow;"
                + "-fx-font-size:36px;"
                + "-fx-font-weight:bold;";
    }

    @Override
    public String getButtonStyle() {
        return "-fx-background-color:#2C2C2C;"
                + "-fx-text-fill:white;"
                + "-fx-font-size:16px;"
                + "-fx-font-weight:bold;";
    }

    @Override
    public String getTextFieldStyle() {
        return "-fx-background-color:#3A3A3A;"
                + "-fx-text-fill:white;";
    }
}