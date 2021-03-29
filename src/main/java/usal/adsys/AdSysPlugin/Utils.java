package usal.adsys.AdSysPlugin;
import java.util.Collections;

public class Utils {
    public static String repeat(String s, int times) {
        return String.join("", Collections.nCopies(times, s));
    }
}
