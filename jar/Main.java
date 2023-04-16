import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
public class Main {
    public static String func (String inputText, String clef, String note) {
        // change note type and clef type to save memory
        checkSkip(inputText);
        ArrayList<Word> wordList = syllable(inputText);
        String str = " ";
        for (Word i : wordList) {
            str += i.separated + " ";
        }
        ArrayList<String> strList = new ArrayList<String>(
            Arrays.asList(str.split("[.]")));
        String newStr = "";
        int temp = 0;
        for (int i = 0; i < strList.size() - 1; i++) {

            String s = strList.get(i);
            ArrayList<String> sList = new ArrayList<String>(
            Arrays.asList(s.split(" ")));
            temp += sList.size() - 2;
            int num = wordList.get(temp).syllableCount - wordList.get(temp).stressSyllable;
            if (sList.get(0).contains("-") || sList.get(1).contains("-")){
                s = reverseCount(reverseCount(s, num + 2), num + 1).trim().replaceFirst("-", ">-");
            }

            else {
            s = reverseCount(reverseCount(s, num + 2), num + 1).trim().replaceFirst(" ", "> ");}
            newStr += s + ".  (:)";
        }
        str = newStr;
        String text = "(" + clef + ")" + str.replace(". ", ".") + "(::)";
        String notes[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m"};
        int found = Arrays.asList(notes).indexOf(note);
        String noteDown;
        try {
            noteDown = notes[found - 1];
        } catch (Exception IndexOutOfBounds) {
            noteDown = notes[found];
        }
        String[] finais = {"Por(" + noteDown + ") Cris-(" + note + ")to,(" + note + ") nos-(" + note + ")so(" + noteDown + ") Se-(" + noteDown + ")nhor.(" + note + ".)",
        "Por("+ noteDown + ") Cris-("+ note + ")to("+ note +"), nos-("+ note +")so("+ note +") Se-("+ note +")nhor.("+ note + noteDown + ".)",
        "Por(" + noteDown + ") nos-(" + note + ")so(" + note + ") Se-(" + note + ")nhor(" + note + ") Je-(" + note + ")sus(" + note + ") Cris-(" + note + ")to,(" + note + ") vos-(" + note +     ")so(" + note +     ") Fi-(" + note + ")lho,(" + note +      ") na(" + note +     ") u-(" + note + ")ni-(" + note + ")da-(" + note + ")de(" + note + ") do(" + note + ") Es-(" + note + ")pí-(" + noteDown + ")ri-(" + noteDown + ")to(" + note + ") San-(" + note +           ")to.(" + note +     ".)", 
        "Por(" + noteDown + ") nos-(" + note + ")so(" + note + ") Se-(" + note + ")nhor(" + note + ") Je-(" + note + ")sus(" + note + ") Cris-(" + note + ")to,(" + note + ") vos-(" + noteDown + ")so(" + noteDown + ") Fi-(" + note + ")lho,(" + note + ".) (;) na(" + noteDown + ") u-(" + note + ")ni-(" + note + ")da-(" + note + ")de(" + note + ") do(" + note + ") Es-(" + note + ")pí(" + note +      ")ri(" + noteDown +  ")to(" + note + ") San-("+ note + noteDown + ")to.(" + noteDown + ".)"
        };

        String[] toReplace = { "-", "-(" + note + ")", " ", "(" + note + ") ",">-(" + note + ")", "-(" + noteDown + ")",">(" + note + ") ", "(" + noteDown + ") ","." + "(" + note + ")", "." + "(" + note + ".)", finais[0], finais[1], finais[2], finais[3], "(:)(::)","(::)"
        };
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
    public static ArrayList<Word> syllable(String text) {
        ArrayList<Word> wordList = new ArrayList<Word>();
        for (String s : text.split(" ")) {
            Word w = new Word(s);
            wordList.add(w);
        }
        return wordList;
    }
    public static String reverseCount(String str, int num){
        int count = 0;
        num = Word.countChar(str, ' ') + Word.countChar(str, '-') - num;
        String changed;
        for(int i = 0; i < str.length(); i++){    
            if(str.charAt(i) == '-' || str.charAt(i) == ' '){
                count++;
                if (count == num + 1 && Word.countChar(str, ' ') + Word.countChar(str, '-') > 8){
                    changed = str.substring(0, i) + '>' + str.substring(i, str.length());
                    return changed;
                } 
            }
        }
        return str;
    }
}