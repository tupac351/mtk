/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.theme;

public class DefaultTheme implements ThemeFactory {

    @Override
    public String getBackgroundStyle() {
        return "-fx-background-color:#ADD8E6;";
    }

    @Override
    public String getTitleStyle() {
        return "-fx-text-fill:red;"
                + "-fx-font-size:36px;"
                + "-fx-font-weight:bold;";
    }

    @Override
    public String getButtonStyle() {
        return "-fx-background-color:#00008B;"
                + "-fx-text-fill:white;"
                + "-fx-font-size:16px;"
                + "-fx-font-weight:bold;";
    }

    @Override
    public String getTextFieldStyle() {
        return "-fx-background-color:white;"
                + "-fx-text-fill:black;";
    }
}