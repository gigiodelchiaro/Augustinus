
if __name__ == '__main__':
	import sys

	try:
		inputText = sys.argv[1]
		clef = sys.argv[2]
		note = sys.argv[3].casefold()

	except IndexError:
		print("Usage: text, clef, note, line break, respiration")
		sys.exit(0)
	

def syllable(text):
	import os
	

	
	lpalavra = text.split()
	where = []
	separated = []

	os.environ['CLASSPATH'] = "./sources/fb_nlplib.jar"
 
 
	from jnius import autoclass
	class FalaBrasilNLP:
		def __init__(self):
			self.jClass = autoclass('ufpa.util.PyUse')()
			self.fb_getsyl = self.jClass.useSyll
    
	fb_nlp = FalaBrasilNLP()

	for i in lpalavra:
		where.append(i[-1]) if i[-1] == ',' or i[-1] == '.' else where.append('')
  
		if i[0].isupper():
			hasUpper = True
		else:
			hasUpper = False
		if hasUpper:
			separated.append(fb_nlp.fb_getsyl(i.replace(".", "").replace(",", "").casefold()).capitalize())
		else:
			separated.append(fb_nlp.fb_getsyl(i.replace(".", "").replace(",", "").casefold()))


	return " ".join(list(map(lambda s, w: s + w, separated, where)))

text = f'({clef}){syllable(inputText)} '

toReplace = {
    '-': f'-({note})',
    ' ': f'({note}) ', 
}

for i, j in toReplace.items():
    text = text.replace(i, j)

print(text)
input("Closing the program...")
