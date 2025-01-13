import syllable
import tonic

if __name__ == "__main__":
    while True:
        # Example usage
        word = input("Enter a word: ")
        print()
        word_separated = syllable.separate_word(word)
        tonic_number = tonic.tonic(word_separated.split('-'))
        print(f"Original word: {word}")
        print(f"Separated word: {word_separated}")
        print(f"Tonic number for '{word}': {tonic_number}")
        print()