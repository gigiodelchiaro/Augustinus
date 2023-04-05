import java.util.Scanner;
public class Main {
    static Syllabificator s = new Syllabificator();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputText, clef, note;
        
        if (args.length >= 3) {
            inputText = args[0];
            clef = args[1].toLowerCase();
            note = args[2].toLowerCase();
        } else {
            System.out.print("Text: ");
            inputText = scanner.nextLine();
            System.out.print("Clef: ");
            clef = scanner.nextLine().toLowerCase();
            System.out.print("Note: ");
            note = scanner.nextLine().toLowerCase();
        }

        String text = "(" + clef + ")" + syllable(inputText) + " ";

        String[] toReplace = {"-", "-(" + note + ")", " ", "(" + note + ") "};

        for (int i = 0; i < toReplace.length; i += 2) {
            text = text.replace(toReplace[i], toReplace[i + 1]);
        }

        System.out.println(text);
        System.out.println("Closing the program...");
        scanner.nextLine();
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