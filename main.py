from tkinter import *
from tkinter.ttk import *
import pyperclip
root = Tk()

root.title("Augustinus")
root.geometry("500x650")
root.iconbitmap("sources/augustinus.ico")

clefs = [
	"c1",
	"c2",
	"c3",
	"c4",
	"c5",
	
]
clicked_clefs = StringVar(root)
clicked_clefs.set(clefs[0])


notes = [
	"a",
	"b",
	"c",
	"d",
	"e",
	"f",
	"g",
	"h",
	"i",
	"j",
	"k",
	"l",
	"m",
	
]

clicked_notes = StringVar(root)
clicked_notes.set(notes[0])



qclef = Label(root, text="Qual vai ser a clave?")
qclef.pack()

aclef = OptionMenu(root, clicked_clefs, *clefs)
aclef.pack()

qnote = Label(root, text="Qual vai ser a nota principal?")
qnote.pack()

anote = aclef = OptionMenu(root, clicked_notes, *notes)
anote.pack()

qtext = Label(root, text="Qual vai ser o texto?")
qtext.pack()

global atext
atext = Text(root, width=50, height=15, font="Corbel")
atext.pack()
global clicked_line_break
global clicked_respiration
clicked_line_break = StringVar(root)
clicked_respiration = StringVar(root)


qline_break = Checkbutton(text="Adicionar quebra de linha depois de todos os pontos finais", variable=clicked_line_break, onvalue=True, offvalue=False)
qline_break.pack()
qrespiration = Checkbutton(text="Adicionar respiração depois das vírgulas", variable=clicked_respiration, onvalue=True, offvalue=False)
qrespiration.pack()


def syllable():
	import os
	import re
	text = atext.get("1.0", "end-1c").lower()
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
  
		atext.delete('1.0', END)
		atext.insert(INSERT, final)
   

global text_final
text_final = ""

def generate():

	note = clicked_notes.get()
	position_note = notes.index(note)
	if note == "a":
		note_down = "a"
	else:
		note_down = notes[position_note -1] 

	text_clef = f'({clicked_clefs.get()}){atext.get("1.0", "end-1c")} '

	fix1 = text_clef.replace(". ",".")

	text_space = fix1.replace(" ",f"({note}) ")
	text_hyphen = text_space.replace("-",f"-({note})")

	line_break = clicked_line_break.get()
	respiration = clicked_respiration.get()


	if line_break:
		text_dot = text_hyphen.replace(".",f".({note}.) (::Z) ")
	else:
		text_dot = text_hyphen.replace(".",f".({note}.) (::) ")


	if respiration:
		text_comma = text_dot.replace(",",f",({note}.) (,) ")
	else:
		text_comma = text_dot.replace(",",f",({note})")


	fix2 = text_comma.replace("<",f"({note_down})")
	text_manual_respiration = fix2.replace(">",f"({note_down}.) (,) ")
	text_manual_double = text_manual_respiration.replace("@",f"({note}{note_down}.)")

	fix3 = text_manual_double.replace(f"({note}.) (,) ({note_down}.) (,)",f"({note_down}.) (,)")
	fix4 = fix3.replace(f"({note})({note_down}.)",f"({note_down}.)")

	fix5 = fix4.replace(f"({note})({note_down})",f"({note_down})")
	fix6 = fix5.replace(f"({note_down})({note})",f"({note_down})")

	fix7 = fix6.replace(f"({note_down}),({note_down}.)",f",({note_down}.)")
	fix8 = fix7.replace(f"({note}),({note}.)",f",({note}.)")

	fix9 = fix8.replace(f"(,) ({note})",f"(,)")
	fix10 = fix9.replace(f"(,) ({note_down})",f"(,)")

	fix11 = fix10.replace(f"({note_down}),({note}.)",f",({note_down}.)")
	fix12 = fix11.replace(f"({note_down}).({note}.)",f".({note_down}.)")
	fix13 = fix12.replace(f"({note})({note}{note_down}.)",f"({note}{note_down}.)")

	fix14 = fix13.replace(f"({note})({note})", f"({note})")

	text_final = fix14
	text_copy.configure(state=NORMAL)
	text_copy.delete('1.0', END)
	text_copy.insert(INSERT, text_final)
	text_copy.configure(state=DISABLED)




atext_copy = Label(root, text="Resultado:")
atext_copy.pack()
global text_copy
text_copy = Text(root, width=50 ,height=5, font="Corbel")
text_copy.insert(INSERT, text_final)
text_copy.configure(state=DISABLED)
text_copy.pack()


def copy_select():
	pyperclip.copy(text_copy.get("1.0",'end-1c'))
button_separate = Button(root, text="Separar", command=syllable)
button_separate.pack()
button_generate = Button(root, text="Gerar", command=generate)
button_generate.pack()

button_copy = Button(root,text="Copiar",command=copy_select)
button_copy.pack()

#Final 1
#Por(g) Cris-(h)to,(h) nos-(h)so(h) Se-(h)nhor.(hg.)
root.mainloop()

