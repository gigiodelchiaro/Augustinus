import re
import requests
# Define character groups
strong_consonants = 'bcdfgjkpqtvxç'
weak_consonants = 'lmnrsz'
strong_vowels = 'aeáéóàèòãẽõâêôäëöíúìùĩũîû'
weak_vowels = 'iouïü'

strong_consonants = strong_consonants + strong_consonants.upper()
weak_consonants = weak_consonants + weak_consonants.upper()
strong_vowels = strong_vowels + strong_vowels.upper()
weak_vowels = weak_vowels + weak_vowels.upper()

digraphs = ['nh', 'lh', 'ch', 'gu', 'qu'] 
vowels = strong_vowels + weak_vowels

def apply_rules(text, divider="-"):
    # Rule 1: Add the divider before any strong consonant
    pattern_strong = rf"([{strong_consonants}])"
    text = re.sub(pattern_strong, rf"{divider}\1", text)

    # Rule 2: Add the divider before any weak consonant if followed by a vowel and not preceded by a strong consonant
    pattern_weak = rf"(?<![{strong_consonants}])([{weak_consonants}])(?=[{vowels}])"
    text = re.sub(pattern_weak, rf"{divider}\1", text)

    # Rule 3: Add the divider before every digraph
    for digraph in digraphs:
        pattern_digraph = rf"({digraph})"
        text = re.sub(pattern_digraph, rf"{divider}\1", text)

    # Rule 4: Add the divider between every weak and strong vowel encounter
    pattern_weak_strong_vowel = rf"([{vowels}])([{strong_vowels}])"
    text = re.sub(pattern_weak_strong_vowel, rf"\1{divider}\2", text)

    # Exception 1: Prevent adding the divider before 'x' if followed by a weak consonant
    pattern_x_weak = rf"(?<!{re.escape(divider)})(x)(?=[{weak_consonants}])"
    text = re.sub(pattern_x_weak, r"\1", text)

    # Exception 2: 'gu' and 'qu' expect a vowel after them
    pattern_gu_qu = rf"(gu|qu){re.escape(divider)}"
    text = re.sub(pattern_gu_qu, r"\1", text)

    return text

def clean_up(text, divider="-"):
    # Clean-up Rule 1: If there is a divider before and after a strong consonant, remove the first
    pattern_double_divider = rf"{re.escape(divider)}([{strong_consonants}]){re.escape(divider)}"
    text = re.sub(pattern_double_divider, r"\1" + divider, text)

    # Clean-up Rule 2: Remove multiple dividers
    text = re.sub(rf"{re.escape(divider)}+", divider, text)

    # Clean-up Rule 3: Remove leading dividers
    text = text.lstrip(divider)

    # Clean-up Rule 4: Remove misplaced spaces around dividers
    text = text.replace(f" {divider}", " ")
    text = text.replace(f"{divider} ", " ")

    return text

def separate_word(text, divider="-"):
    modified_text = apply_rules(text, divider)
    return clean_up(modified_text, divider)


if __name__ == "__main__":
    while True:
        # Input text
        text = input("Enter text: ")
        separated_text = separate_word(text, "(g)")
        separated_text = separated_text.replace(" ", "(g) ")
    
        print(separated_text)
