
var clefs = ["c1", "c2", "c3", "c4", "f2", "f3", "f4", "cb3"];
var notes = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"];


function processText() {
    const separator = document.getElementById('separator').value;
    definirSeparador(separator);

    const textInput = document.getElementById('text').value;
    const resultOutput = document.getElementById('result');
    const removeNumbers = document.getElementById('remove-numbers').checked;
    const addTonics = document.getElementById('add-tonics').checked;
    const addEnd = document.getElementById('add-end').checked;
    const gabcSource = document.getElementById('gabcSource');

    const model = document.getElementById('model').value;
    
    let text = textInput;
    
    const clef = clefs.find(clef => new RegExp(`\\b${clef}\\b`).test(model));
    const note_pattern = `\\([${notes.join("")}]+(r)?(r1)?\\)`;

    const all_notes = model.match(new RegExp(`${note_pattern}`, "g"));
    
    let final = ""
    if (removeNumbers) {
        text = text.replace(/\d/gm, '');
    }
    if (addTonics) {
        let words = text.split(/\s+/gm);
        let result = "";
        for (let word of words) {
            let syllables = separarTexto(word).split(separator);
            let tonicNumber = tonica(syllables);
            tonicNumber = syllables.length - tonicNumber ;
            syllables[tonicNumber] = "<b>" + syllables[tonicNumber] + "</b>";
            
            result += syllables.join(separator)
            if (addEnd) {
                result += separator;
            }
            result += " ";
        }
        final = result;;
    }
    else {
        const processedText = separarTexto(text);
        final = processedText
    }
    resultOutput.innerHTML = final;
    console.log(clef);
    console.log(all_notes);
    gabcSource.value = model;
    updateChant()
}

