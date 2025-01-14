import json
import re

def load_rules(file_path):
    """Loads the rules from a JSON file."""
    with open(file_path, 'r', encoding='utf-8') as file:
        return json.load(file)

def prepare_patterns(rules):
    """Prepares patterns by substituting placeholders."""
    vogais = rules["vogais_fortes"] + rules["vogais_fracas"]
    digrafos = "|".join(map(re.escape, rules["digrafos"].split("|")))  # Escape digraphs
    for pattern in rules["padroes"]:
        # Replace placeholders in regex
        pattern["regex"] = pattern["regex"].format(
            consoantes_fortes=re.escape(rules["consoantes_fortes"]),
            consoantes_fracas=re.escape(rules["consoantes_fracas"]),
            vogais=re.escape(vogais),
            vogais_fortes=re.escape(rules["vogais_fortes"]),
            digrafos=digrafos,
            divisor=re.escape(rules["divisor"])
        )
        # Replace placeholders in replacement string
        pattern["replace"] = (
            pattern["replace"]
            .replace("{divisor}", rules["divisor"])
            .replace("{\\1}", r"\1")  # Correct group reference
            .replace("{\\2}", r"\2")  # Correct group reference
            .replace("{\\3}", r"\3")  # Correct group reference
            .replace("{\\w}", r"\w")  # Correct group reference
            .replace("{\\s}", r"\s")  # Correct group reference
        )
    return rules

def apply_rules(text, rules):
    """Applies rules to the given text."""
    for pattern in rules["padroes"]:
        print(pattern["nome"])
        text = re.sub(pattern["regex"], pattern["replace"], text)
    return text

if __name__ == "__main__":
    # Load the rules from the JSON file
    rules_file = "rules.json"  # Update the path to your JSON file
    rules = load_rules(rules_file)

    # Prepare the patterns with placeholders replaced
    rules = prepare_patterns(rules)

    # Input text to process
    input_text = " " + input("Digite o texto: ")

    # Apply the rules to the input text
    output_text = apply_rules(input_text, rules)

    # Display the result
    print("Texto original:", input_text)
    print("Texto processado:", output_text)
