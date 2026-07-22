package onthi.dictapp.theme;

public class ThemeFactoryProducer {
    public static ThemeFactory getFactory(ThemeType type) {
        switch (type) {
            case DARK:
                return new DarkThemeFactory();
            case LIGHT:
            default:
                return new LightThemeFactory();
        }
    }
}