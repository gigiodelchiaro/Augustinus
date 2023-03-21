if __name__ == '__main__':
    text_raw = input("Text: ")
    clef = input("Clef (C1-C5): ")
    note = input("Main Note (a-m): ").lower()
    line_break = input("Line Break (blank for no): ")
    respiration = input("Respiraton (blank for no): ")

text_clef = f'({clef}){text_raw} '

fix1 = text_clef.replace(". ",".")

text_space = fix1.replace(" ",f"({note}) ")
text_hyphen = text_space.replace("-",f"-({note})")

noteList = ['a','b','c','d','e','f','g','h','i','j','k','l','m']
if note !='a':
    note_down = noteList[noteList.index(note) - 1] 
else:
    note_down = 'a'


if line_break != "":
    text_dot = text_hyphen.replace(".",f".({note}.) (::Z) ")
else:
    text_dot = text_hyphen.replace(".",f".({note}.) (::) ")


if respiration != "":
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

print(fix14)