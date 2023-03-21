if __name__ == '__main__':
    text_raw = input("Text: ")
    clef = input("Clef (C1-C5): ")
    note = input("Main Note (a-m): ").casefold()
    line_break = input("Line Break (blank for no): ")
    respiration = input("Respiraton (blank for no): ")

noteList = ['a','b','c','d','e','f','g','h','i','j','k','l','m']
note_down = noteList[noteList.index(note) - 1] if note != 'a' else 'a'
text = f'({clef}){text_raw} '
replace_dict = {
    '. ': '.', 
    ' ': f'({note})', 
    '-': f'-({note})', 
    ',': f',({note})', 
    '<': f'({note_down})', 
    '>': f'({note_down}.)(,)', 
    '@': f'({note}{note_down}.)', 
    f'({note}.)(,)({note_down}.)(,)': f'({note_down}).(,)', 
    f'({note}).({note_down}).': f'({note_down}).', 
    f'({note})({note_down}).': f'({note_down}).', 
    f'({note_down}),({note_down}).': f',({note_down}).', 
    f'({note}),({note}).': f',({note}).', 
    f'(,) ({note})': '(,)', 
    f'(,) ({note_down})': '(,)', 
    f'({note_down}),({note}).': f',({note_down}).', 
    f'({note_down}).({note}).': f'.({note_down}).', 
    f'({note})({note}{note_down}).': f'({note}{note_down}).', 
    f'({note})({note}).': f'({note}).'
}



for i, j in replace_dict.items():
    text = text.replace(i, j)


print(text)
