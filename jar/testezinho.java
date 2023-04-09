import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testezinho {
    static Syllabificator s = new Syllabificator();
    static char[] charPunc = {',','.','?','!',':',';'};
    public static class Word{
        String original;
        String separated;

        public Word(String value){
            original = value;
            ArrayList<Integer> Upper = new ArrayList<Integer>();
            boolean skip = false;
            char punctuation = 0;
            int nDash;
            if (value.startsWith("*")){skip = true;}
            for (char c : charPunc) {
                if (value.charAt(value.length() - 1) == c) {
                    punctuation = value.charAt(value.length() -1);
                } 
            }
            for (int i = 0; i < value.length(); i++) {
                if (Character.isUpperCase(value.charAt(i))) {
                    Upper.add(i);
                }   
            }

            if (skip) {separated = value;}
            else{separated = (s.syllabs(value.replaceAll("[,.?!:;]", "").toLowerCase()) + punctuation);}
            for (int i = 0; i < Upper.size(); i++) {
                nDash = separated.substring(0, i + 1).length() - separated.substring(0, i + 1).replace("-","").length();
                separated = separated.substring(i + nDash,i + nDash + 1).toUpperCase() + separated.substring(1);
            }
            
            
        }   

    }

    public static void main(String[] args){

        System.out.println(syllable("TesTe? coM *palavras"));

        // System.out.print("Value: " + palavra.value + "\nSeparated: " + palavra.separated + "\nPunctuation: " + palavra.punctuation + "\nHasUpper: " + palavra.hasUpper + "\nSkip: " + palavra.skip);
        // System.out.println(func("Isso sim, (Isso aqui não,)* isso de novo sim, (mas agora não)*", "c4", "f"));
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

