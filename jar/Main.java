import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Syllabificator s = new Syllabificator();
    public static void main(String[] args){
        System.out.println(func("Isso sim, (Isso aqui não,)* isso de novo sim, (mas agora não)*", "c4", "f"));
    }
    public static String func (String inputText, String clef, String note) {
        String text = "(" + clef + ")" + syllable(inputText) + " ";
        String[] toReplace = {"-", "-(" + note + ")", " ", "(" + note + ") "};
        for (int i = 0; i < toReplace.length; i += 2) {
            text = text.replace(toReplace[i], toReplace[i + 1]);
        }
        String[] lText = text.split(" ");
        for (int i = 0; i < lText.length; i++) {
            if (lText[i].startsWith("*")) {
                lText[i] =lText[i].substring(1, lText[i].length() - 3);
            }
        }

        return String.join(" ", lText);
    }
    public static String syllable(String text) {
        List<String> matchList = new ArrayList<String>();
    	Pattern regex = Pattern.compile("\\(([^()]*)\\)*");
    	Matcher regexMatcher = regex.matcher(text);
        ArrayList<String> str = new ArrayList<String>();
        ArrayList<String> str2 = new ArrayList<String>();
        
    	while (regexMatcher.find()) {
    	   matchList.add(regexMatcher.group(1));
    	}
    	for(String string:matchList) {
            str2.add("(" + string + ")*");
            string = "*" + string.replace(" "," *");
            str.add(string);
    	}
        for (int i = 0; i < str.size(); i++) {
                text = text.replace(str2.get(i),str.get(i).trim());
            }
        String[] lpalavra = text.replace("*(","*").split(" ");
        String[] where = new String[lpalavra.length];
        String[] separated = new String[lpalavra.length];    
        
        for (int i = 0; i < lpalavra.length; i++) {
            if (lpalavra[i].endsWith(",") || lpalavra[i].endsWith(".")) {
                where[i] = lpalavra[i].substring(lpalavra[i].length() - 1);
            } else {
                where[i] = "";
            }
            if (lpalavra[i].startsWith("*")) {
                separated[i] = lpalavra[i].replace(")","");
            } else if (Character.isUpperCase(lpalavra[i].charAt(0))) {
                separated[i] = s.syllabs(lpalavra[i].replace(".", "").replace(",", "").toLowerCase()) + where[i];
                separated[i] = Character.toUpperCase(separated[i].charAt(0)) + separated[i].substring(1);
            } else {
                separated[i] = s.syllabs(lpalavra[i].replace(".", "").replace(",", "").toLowerCase())+ where[i];
            }
        }

        return String.join(" ", separated);
    }
}