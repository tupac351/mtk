/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp.theme;

/**
 *
 * @author ASUS
 */
public class ThemeFactoryProducer {
    public static ThemeFactory getFactory(Theme themeType) {
        return switch (themeType) {
            case DEFAULT ->
                new DefaultTheme();
            case DARK ->
                new DarkTheme();
            case LIGHT ->
                new LightTheme();
            default ->
                new DefaultTheme();
        };
    }
}
