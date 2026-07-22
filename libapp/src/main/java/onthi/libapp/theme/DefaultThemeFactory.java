package onthi.libapp.theme;

public class DefaultThemeFactory implements ThemeFactory {
    @Override public String getBgColor() { return "#ADD8E6"; }      // Xanh dương nhạt
    @Override public String getLabelColor() { return "#000000"; }   // Đen
    @Override public String getBtnColor() { return "#00008B"; }     // Xanh dương đậm
    @Override public String getBtnTextColor() { return "#FFFFFF"; } // Trắng
}

//package onthi.libapp.theme;
//
//public class DarkThemeFactory implements ThemeFactory {
//    @Override public String getBgColor() { return "#2b2b2b"; }      // Xám đen
//    @Override public String getLabelColor() { return "#FFFF00"; }   // Vàng
//    @Override public String getBtnColor() { return "#555555"; }     // Xám tro
//    @Override public String getBtnTextColor() { return "#FFFFFF"; } // Trắng
//}

//package onthi.libapp.theme;
//
//public class LightThemeFactory implements ThemeFactory {
//    @Override public String getBgColor() { return "#FFFFFF"; }      // Trắng
//    @Override public String getLabelColor() { return "#000000"; }   // Đen
//    @Override public String getBtnColor() { return "#E0E0E0"; }     // Xám nhạt
//    @Override public String getBtnTextColor() { return "#000000"; } // Đen
//}