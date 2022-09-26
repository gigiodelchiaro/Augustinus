from tkinter import *
import pyperclip
root = Tk()

root.title("Agostinus")
root.geometry("500x800")
#root.iconbitmap("sources/icon.ico")

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

atext = Text(root, width=50, height=15)
atext.pack()
line_break = Checkbutton(text="Adicionar quebra de linha depois de todos os pontos finais", onvalue="y", offvalue="n")
respiration = Checkbutton(text="Adicionar respiração depois das vírgulas", onvalue="y", offvalue="n")
global text_final
text_final = ""
text = atext.get("1.0",'end-1c')
def generate():
    note = clicked_notes.get()

    if note == "a":
        note_down = "a"

    elif note == "b":
        note_down = "a"

    elif note == "c":
        note_down = "b"

    elif note == "d":
        note_down = "c"

    elif note == "e":
        note_down = "d"

    elif note == "f":
        note_down = "e"

    elif note == "g":
        note_down = "f"

    elif note == "h":
        note_down = "g"

    elif note == "i":
        note_down = "h"

    elif note == "j":
        note_down = "i"

    elif note == "k":
        note_down = "j"

    elif note == "l":
        note_down = "k"

    elif note == "m":
        note_down = "l"


    text_clef = "("+ clicked_clefs.get()+")"+ atext.get("1.0",'end-1c')

    text_dot_fix = text_clef.replace(". ",".")
    text_comma_fix = text_dot_fix.replace(", ",",")

    text_space = text_comma_fix.replace(" ",f"({note}) ")
    text_hyphen = text_space.replace("-",f"-({note})")
    if line_break == "y":
        text_dot = text_hyphen.replace(".",f".({note_down}.) (::Z)")
    else:
        text_dot = text_hyphen.replace(".",f".({note_down}.) (::)")


    if respiration == "y":
        text_comma = text_dot.replace(",",f",({note_down}.) (,)")
    else:
        text_comma = text_dot.replace(",",f",({note_down}.)")

    text_down = text_comma.replace("*",f"({note_down})")

    text_down_fix = text_down.replace(f"({note})({note_down})",f"({note_down})")
    text_up_fix = text_down_fix.replace(f"({note_down})({note})",f"({note_down})")
    text_down_comma_fix = text_up_fix.replace(f"({note_down}),({note_down}.)",f",({note_down}.)")
    text_final = text_down_comma_fix
    text_copy.configure(state=NORMAL)
    text_copy.delete('1.0', END)
    text_copy.insert(INSERT, text_final)
    text_copy.configure(state=DISABLED)



atext_copy = Label(root, text="Resultado:")
atext_copy.pack()
global text_copy
text_copy = Text(root, width=50 ,height=5)
text_copy.insert(INSERT, text_final)
text_copy.configure(state=DISABLED)
text_copy.pack()


def copy_select():
    pyperclip.copy(text_copy.get("1.0",'end-1c'))

button_generate = Button(root, text="Gerar", command=generate)
button_generate.pack()

button_copy = Button(root,text="Copiar",command=lambda:copy_select())
button_copy.pack()

#Final 1
#Por(g) Cris-(h)to,(h) nos-(h)so(h) Se-(h)nhor(hg.). F
#Ó* Deus, ao par-ti-ci-par-mos da a-le-gri-a da sal-va-ção que en-cheu de jú-bi-lo São Ma-teus*, re-*ce-ben-do o Sal-va-dor em su-a ca-sa, con-*ce-dei se-ja-mos sem-pre re-fei-tos à me-sa da-que-le que vei-o cha-mar à sal-va-ção não os jus-tos*, mas* os pe-*ca-*do-res.

root.mainloop()