import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Syllabificator s = new Syllabificator();
    static char[] charPunc = {',','.','?','!',':',';'};
    public static class Word{
        String original;
        String separated;

        public Word(String value){
            this.original = value;
            boolean Upper;
            boolean skip = false;
            char punctuation = 0;
            
            if (value.startsWith("*")){skip = true;}
            for (char c : charPunc) {
                if (value.charAt(value.length() - 1) == c) {
                    punctuation = value.charAt(value.length() -1);
                } 
            }
            Upper = Character.isUpperCase(value.charAt(0));
            if (skip) {separated = value;}
            else if (Upper){separated = s.syllabs(value.replaceAll("[,.?!:;]", "").toLowerCase()) + punctuation;
            separated = separated.substring(0,1).toUpperCase() + separated.substring(1);}
            else{separated = s.syllabs(value.replaceAll("[,.?!:;]", "").toLowerCase()) + punctuation;}     
        }   
    }
    public static String func (String inputText, String clef, String note) {
        checkSkip(inputText);
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
    public static void checkSkip(String text){
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
    }
    public static String syllable(String text) {
        ArrayList<String> separatedList = new ArrayList<String>();
        for (String s : text.split(" ")) {
            Word wordlist = new Word(s);
            separatedList.add(wordlist.separated);
        }
        return String.join(" ", separatedList);
    }
}

