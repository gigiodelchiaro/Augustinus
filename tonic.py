import re

strong_accents = 'áéíóúàèìòùâêîôûÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛ'
weak_accents = 'ãẽĩõũÃẼĨÕŨ'

def tonic(word_separated):
    for i, syllable in enumerate(reversed(word_separated), start=1):
        if any(char in strong_accents for char in syllable):
            return i
    for i, syllable in enumerate(reversed(word_separated), start=1):
        if any(char in weak_accents for char in syllable):
            return i
    
    last_syllable = word_separated[-1]
    if re.search(r'(i(s)?|u|z|im|us|r|l|x|n|um(s)?|ps|om|on(s)?)$', last_syllable):
        return 1
    return 2
