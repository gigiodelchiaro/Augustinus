import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
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
            char punctuation = '#';
            
            if (value.startsWith("*")){skip = true;}
            for (char c : charPunc) {
                if (value.charAt(value.length() - 1) == c) {
                    punctuation = value.charAt(value.length() -1);
                } 
            }
            Upper = Character.isUpperCase(value.charAt(0));
            if (skip) {separated = value;}
            else if (Upper){separated = (s.syllabs(value.replaceAll("[,.?!:;]", "").toLowerCase()) + punctuation).replace("#","");
            separated = separated.substring(0,1).toUpperCase() + separated.substring(1);}
            else{separated = (s.syllabs(value.replaceAll("[,.?!:;]", "").toLowerCase()) + punctuation).replace("#","");}     
        }   
    }
    public String func (String inputText, String clef, String note) {
        // change note type and clef type to save memory
        checkSkip(inputText);
        String text = "(" + clef + ")" + syllable(inputText) + " ";
        String notes[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m"};
        int found = Arrays.asList(notes).indexOf(note);
        String noteDown;
        if (note == "a"){
            noteDown = note;
        }
        else{noteDown = notes[found - 1];}
        String[] toReplace = {"-rr","r-r","-ss","s-s","-xc","x-c", "-", "-(" + note + ")", " ", "(" + note + ") ",
        "Por(" + note + ") Cris-(" + note + ")to,(" + note + ") nos-(" + note + ")so(" + note + ") Se-(" + note + ")nhor.(" + note + ")",
        "(:) Por("+ noteDown + ") Cris-("+ note + ")to("+ note +"), nos-("+ note +")so("+ note +") Se-("+ note +")nhor.("+ note + noteDown + ".) (::)",
        "Por(" + note + ") nos-(" + note + ")so(" + note + ") Se-(" + note + ")nhor(" + note + ") Je-(" + note + ")sus("+ note + ") Cris-(" + note + ")to,(" + note + ") vos-(" + note + ")so(" + note + ") Fi-(" + note + ")lho,(" + note + ") na(" + note + ") u-(" + note + ")ni-(" + note + ")da-(" + note + ")de(" + note + ") do(" + note + ") Es-(" + note + ")pí-(" + note + ")ri-(" + note + ")to(" + note + ") San-(" + note + ")to.(" + note + ")", 
        "(:) Por(" + noteDown + ") nos-(" + note + ")so(" + note + ") Se-(" + note + ")nhor(" + note + ") Je-(" + note + ")sus(" + note + ") Cris-(" + note + ")to(" + note + "), vos-(" + noteDown + ")so(" + noteDown + ") Fi-(" + note + ")lho,(" + note + ".) (;) na(" + noteDown + ") u-(" + note + ")ni-(" + note + ")da-(" + note + ")de(" + note + ") do(" + note + ") Es-(" + note + ")pí(" + note + ")ri(" + noteDown + ")to(" + note + ") San-("+ note + noteDown + ")to.(" + noteDown + ".) (::)"};
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

