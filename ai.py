
if __name__ == '__main__':
    import sys
    if len(sys.argv) < 5:
        print("Usage: text, clef, note, line break, respiration")
        exit(1)
    inputText = sys.argv[1]
    clef = sys.argv[2]
    note = sys.argv[3].casefold()
    lnBreak = bool(sys.argv[4])
    resp = bool(sys.argv[5])


def syllable(text):
	import os
	import re
	lpalavra = text.split()
	where = []


	os.environ['CLASSPATH'] = "./sources/fb_nlplib.jar"

	from jnius import autoclass

	class FalaBrasilNLP:
		def __init__(self):
			self.jClass = autoclass('ufpa.util.PyUse')()
			self.fb_getsyl = self.jClass.useSyll

		def fb_process_input(self, text):
			lpalavra = text.split()
			separated = [self.fb_getsyl(i.replace(".", "").replace(",", "")) for i in lpalavra]
			return separated
	if __name__ == '__main__':
		fb_nlp = FalaBrasilNLP()
		#where = [".,".find(i) if i in ".," else "" for i in lpalavra]

		for i in lpalavra:
			if re.search(r"\.", i):
				where.append(".")
    
			elif re.search(r",", i):
				where.append(",")
    
			else:
				where.append("")
   
		i = i.replace(".", "")
		i = i.replace(",", "")
		separated = [fb_nlp.fb_getsyl(i.replace(".", "").replace(",", "")) for i in lpalavra]
		


		final = list(map(lambda s, w: s + w, separated, where))
  
  
noteList = ['a','b','c','d','e','f','g','h','i','j','k','l','m']
note_down = noteList[noteList.index(note) - 1] if note != 'a' else 'a'
text = f'({clef}){inputText} '

toReplace = {
    '-': f'-({note})',
    ' ': f'({note}) ', 
}

for i, j in toReplace.items():
    text = text.replace(i, j)

print(text)
input("Closing the program...")
