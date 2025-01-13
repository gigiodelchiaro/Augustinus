/*
    function separateWord(text, divider = '-') => string
    function tonic([syllables]) => number
    */
    function processText() {
        const textInput = document.getElementById('text').value;
        const resultOutput = document.getElementById('result');
        const removeNumbers = document.getElementById('remove-numbers').checked;
        const addTonics = document.getElementById('add-tonics').checked;

        let text = textInput;
        if (removeNumbers) {
            text = text.replace(/\d/gm, '');
        }
        if (addTonics) {
            let words = text.split(" ");
            let result = "";
            for (let word of words) {
                let separated = separateWord(word, "-");
                let syllables = separated.split("-");
                let tonic = tonic(syllables);
                tonic = syllables.length - tonic;
                syllables[tonic] = "*" + syllables[tonic] + "*";

                result += syllables.join("-") + " ";
            }
            resultOutput.value = result;
            return;
        }
        const processedText = separateWord(text);
        resultOutput.value = processedText;
    }