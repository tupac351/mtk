package onthi.dictapp.theme;

public class DarkThemeFactory implements ThemeFactory {
    @Override
    public Theme createTheme() {
        return new DarkTheme();
    }
}