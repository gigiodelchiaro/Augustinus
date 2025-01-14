var rules;
// Wait for the DOM to load
document.addEventListener("DOMContentLoaded", async () => {
    // Load the rules from the JSON file
    const rulesFile = "https://raw.githubusercontent.com/gigiodelchiaro/Augustinus/refs/heads/main/rules.json"; // Update to your JSON file path
    rules = await loadRules(rulesFile);
});

function processText() {
    const textInput = document.getElementById('text').value;
    const resultOutput = document.getElementById('result');
    const removeNumbers = document.getElementById('remove-numbers').checked;
    const addTonics = document.getElementById('add-tonics').checked;

    // Prepare patterns
    preparePatterns(rules);
    
    let text = " " + textInput;
    if (removeNumbers) {
        text = text.replace(/\d/gm, '');
    }
    if (addTonics) {
        let words = text.split(" ");
        let result = "";
        for (let word of words) {
            let separated = applyRules(word, rules)
            let syllables = separated.split("-");
            let tonicNumber = tonic(syllables);
            tonicNumber = syllables.length - tonicNumber;
            syllables[tonicNumber] = "<b>" + syllables[tonicNumber] + "</b>";

            result += syllables.join("-") + " ";
        }
        resultOutput.innerHTML = result;
        return;
    }
    const processedText = separateWord(text);
    resultOutput.innerHTML = processedText;
}

