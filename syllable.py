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

def apply_rules(text):
    # Rule 1: Add '-' before any strong consonant
    pattern_strong = rf"([{strong_consonants}])"
    text = re.sub(pattern_strong, r"-\1", text)

    # Rule 2: Add '-' before any weak consonant if followed by a vowel and not preceded by a strong consonant
    pattern_weak = rf"(?<![{strong_consonants}])([{weak_consonants}])(?=[{vowels}])"
    text = re.sub(pattern_weak, r"-\1", text)

    # Rule 3: Add '-' before every digraph
    for digraph in digraphs:
        pattern_digraph = rf"({digraph})"
        text = re.sub(pattern_digraph, r"-\1", text)
    
    # Rule 4: Add '-' between every weak and strong vowel encounter
    pattern_weak_strong_vowel = rf"([{vowels}])([{strong_vowels}])"
    text = re.sub(pattern_weak_strong_vowel, r"\1-\2", text)

    # Exception 1: Prevent adding '-' before 'x' if followed by a weak consonant
    pattern_x_weak = rf"(?<!-)(x)(?=[{weak_consonants}])"
    text = re.sub(pattern_x_weak, r"\1", text)
    # Exception 2: 'gu' and 'qu' expect a vowel after them
    pattern_gu_qu = rf"(gu|qu)-"
    text = re.sub(pattern_gu_qu, r"\1", text)
    
    return text

def clean_up(text):
    # Clean-up Rule 1: If there is a hyphen before and after a strong consonant, remove the first
    pattern_double_hyphen = rf"-([{strong_consonants}])-"
    text = re.sub(pattern_double_hyphen, r"\1-", text)
    # Clean-up Rule 2: Remove multiple hyphens
    text = re.sub(r"-+", "-", text)

    # Clean-up Rule 2: Remove leading hyphens
    text = text.lstrip("-")
    text = text.replace(" -", " ")
    text = text.replace("- ", " ")

    

    return text

def separate_word(text):
    modified_text = apply_rules(text)
    return clean_up(modified_text)

if __name__ == "__main__":
    while True:
        # Input text
        text = input("Enter text: ")
        separated_text = separate_word(text)

        print(cleaned_text)
