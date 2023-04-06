import java.nio.charset.Charset;
import java.util.Scanner;
public class Main {
    static Syllabificator s = new Syllabificator();
    public String func (String inputText, String clef, String note) {
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset());
      

        String text = "(" + clef + ")" + syllable(inputText) + " ";

        String[] toReplace = {"-", "-(" + note + ")", " ", "(" + note + ") "};

        for (int i = 0; i < toReplace.length; i += 2) {
            text = text.replace(toReplace[i], toReplace[i + 1]);
        }

        return text;
    }

    public static String syllable(String text) {
        String[] lpalavra = text.split(" ");
        String[] where = new String[lpalavra.length];
        String[] separated = new String[lpalavra.length];
        boolean skip = false;
        boolean hasUpper = false;

        for (int i = 0; i < lpalavra.length; i++) {
            if (lpalavra[i].endsWith(",") || lpalavra[i].endsWith(".")) {
                where[i] = lpalavra[i].substring(lpalavra[i].length() - 1);
            } else {
                where[i] = "";
            }

            if (lpalavra[i].startsWith("*")) {
                skip = true;
            } else if (Character.isUpperCase(lpalavra[i].charAt(0))) {
                hasUpper = true;
            } else {
                hasUpper = false;
                skip = false;
            }

            if (skip) {
                separated[i] = lpalavra[i];
            } else if (hasUpper) {
                separated[i] = s.syllabs(lpalavra[i].replace(".", "").replace(",", "").toLowerCase());
                separated[i] = Character.toUpperCase(separated[i].charAt(0)) + separated[i].substring(1);
            } else {
                separated[i] = s.syllabs(lpalavra[i].replace(".", "").replace(",", "").toLowerCase());
            }
        }

        return String.join(" ", separated);
    }
}