import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Syllabificator s = new Syllabificator();
    public static void main(String[] args){
        System.out.println(func("Isso sim, *Isso aqui não,* isso de novo sim, *mas agora não*", "c4", "f"));
    }
    public static String func (String inputText, String clef, String note) {

        String text = "(" + clef + ")" + syllable(inputText) + " ";

        String[] toReplace = {"-", "-(" + note + ")", " ", "(" + note + ") "};

        for (int i = 0; i < toReplace.length; i += 2) {
            text = text.replace(toReplace[i], toReplace[i + 1]);
        }

        return text;
    }

    public static String syllable(String text) {
        List<String> matchList = new ArrayList<String>();
    	Pattern regex = Pattern.compile("\\*([^()]*)\\*");
    	Matcher regexMatcher = regex.matcher(text);
        String str = "";
        String str2 = "";
    	while (regexMatcher.find()) {
    	   matchList.add("*" + regexMatcher.group(1));
    	}

    	for(String string:matchList) {
    	    str = "*" + string.replace("*","") + "*";
           
    	}
        String[] skipwords = str.split(" ");
        for(String item:skipwords){
            str2 += "*" + item.replace("*","") + " ";
            str2.trim();
        }
        System.out.println(text.replace(str,str2));
        String[] lpalavra = text.replace(str,str2).split(" ");
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
                separated[i] = lpalavra[i].replace("*", "");
            } else if (hasUpper) {
                separated[i] = s.syllabs(lpalavra[i].replace(".", "").replace(",", "").toLowerCase()) + where[i];
                separated[i] = Character.toUpperCase(separated[i].charAt(0)) + separated[i].substring(1);
            } else {
                separated[i] = s.syllabs(lpalavra[i].replace(".", "").replace(",", "").toLowerCase())+ where[i];
            }
        }

        return String.join(" ", separated);
    }
}