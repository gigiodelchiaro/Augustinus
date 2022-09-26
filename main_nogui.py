text_raw = input("Qual vai ser o texto da música? ")
clef = input("Qual vai ser a clave? (c1, c2, c3, c4, c5) ")
note = input("Qual vai ser a nota principal? (a-m) ")

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



line_break = input("Quer quebra de linha depois de todos os pontos finais? (S/N) ")
respiration = input("Quer adicionar respiração depois das vírgulas? (S/N) ")

text_clef = "("+ clef +")" + text_raw

text_dot_fix = text_clef.replace(". ",".")
text_comma_fix = text_dot_fix.replace(", ",",")


text_space = text_comma_fix.replace(" ",f"({note}) ")
text_hyphen = text_space.replace("-",f"-({note})")



if line_break == "s":
    text_dot = text_hyphen.replace(".",f".({note_down}.) (::Z)")
else:
    text_dot = text_hyphen.replace(".",f".({note_down}.) (::)")


if respiration == "s":
    text_comma = text_dot.replace(",",f",({note_down}.) (,)")
else:
    text_comma = text_dot.replace(",",f",({note_down}.)")

text_down = text_comma.replace("*",f"({note_down})")

text_down_fix = text_down.replace(f"({note})({note_down})",f"({note_down})")
text_up_fix = text_down_fix.replace(f"({note_down})({note})",f"({note_down})")
text_down_comma_fix = text_up_fix.replace(f"({note_down}),({note_down}.)",f",({note_down} +.)")
print(text_down_comma_fix)

quit = input("Deseja sair do programa? (S/N) ")


#Final 1
#Por(g) Cris-(h)to,(h) nos-(h)so(h) Se-(h)nhor(hg.). F
#Ó* Deus, ao par-ti-ci-par-mos da a-le-gri-a da sal-va-ção que en-cheu de jú-bi-lo São Ma-teus*, re-*ce-ben-do o Sal-va-dor em su-a ca-sa, con-*ce-dei se-ja-mos sem-pre re-fei-tos à me-sa da-que-le que vei-o cha-mar à sal-va-ção não os jus-tos*, mas* os pe-*ca-*do-res.
