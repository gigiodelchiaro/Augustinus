public class Word{
    static Syllabificator s = new Syllabificator();
    static StressVowel v = new StressVowel();
    static char[] charPunc = {',','.','?','!',':',';'};
    
    String original;
    String separated;
    int syllableCount;
    int stressSyllable;
    public Word(String value){
        this.original = value;
        boolean Upper;
        boolean skip = false;
        int originalHyphenCount;
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
        separated = separated.replace("-rr","r-r").replace("-ss","s-s").replace("-xc","x-c");
        syllableCount = countChar(separated,'-') + 1;
        originalHyphenCount = countChar(original.substring(0, v.findStress(original.replace("-","").replace(",","").replace(".",""))),'-');
        stressSyllable = countChar(separated.substring(0, v.findStress(original.replace("-","").replace(",","").replace(".",""))),'-') + 1 + originalHyphenCount;
        }
        public static int countChar(String str, char c)
        {
            int count = 0;
        
            for(int i=0; i < str.length(); i++)
            {    if(str.charAt(i) == c)
                    count++;
            }
        
            return count;
        }  
    }
    
