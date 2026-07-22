/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp.theme;

/**
 *
 * @author ASUS
 */

public class LightThemeFactory implements ThemeFactory {
    @Override
    public Theme createTheme() {
        return new LightTheme();
    }
}
