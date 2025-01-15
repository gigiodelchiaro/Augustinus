
var clefs = ["c1", "c2", "c3", "c4", "f2", "f3", "f4", "cb3"];
var notes = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"];

function note_merge(string1, string2) {
    return string1.replace(")", "") + string2.replace("(", "");
}
function processText() {
    const separator = "@";
    definirSeparador(separator);

    const textInput = document.getElementById('text').value;
    const resultOutput = document.getElementById('result');
    const removeNumbers = document.getElementById('remove-numbers').checked;
    const addTonics = document.getElementById('add-tonics').checked;
    const addEnd = document.getElementById('add-end').checked;
    const gabcSource = document.getElementById('gabcSource');

    const model = document.getElementById('model').value;

    let text = textInput;

    const all_symbols = model.match(/\(\S+\)/gm);

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
            tonicNumber = syllables.length - tonicNumber;
            syllables[tonicNumber] = "<b>" + syllables[tonicNumber] + "</b>";
            result += syllables.join(separator)
            if (addEnd) {
                result += separator;
            }
            result += " ";

        }
        final = result;
    }
    else {
        const processedText = separarTexto(text).replaceAll(" ", separator + " ");
        final = processedText
    }
    let all_syllables = final.split(separator);
    const note_pattern = `\\([${notes.join("")}]+\\)`;
    const tonic_pattern = `\\([${notes.join("")}]+r1\\)`;
    const generic_note_pattern = `\\([${notes.join("")}]+r\\)`;
    let gabc = all_symbols[0] + " ";
    let remaining_syllables = all_syllables;
    let hit_generic_note = false;
    let hit_last_note = false;
    let words = final.split(" ");
    let tonic_last_note = tonica(words[words.length - 1].split(separator));
    let generic_note = "";
    let remaining_symbols = all_symbols;
    let notes_before_tonic = [];
    remaining_symbols.shift();
    while (remaining_symbols.length > 0) {
        let symbol = all_symbols[0];
        if (symbol.match(note_pattern)) {
            if (hit_generic_note) {
                notes_before_tonic.push(symbol);
            }
            else {
                gabc += remaining_syllables.shift() + symbol;
            }
            remaining_symbols.shift();
        }
        else if (symbol.match(generic_note_pattern)) {
            if (!hit_generic_note) {
                generic_note = symbol;
                gabc += symbol;
                hit_generic_note = true;
            }
            remaining_symbols.shift();
        }
        else if (symbol.match(tonic_pattern)) {
            remaining_symbols.shift();
            let last_symbols = remaining_symbols.join(" ");
            symbol = symbol.replace("r1", "");
            gabc += remaining_syllables[remaining_syllables.length - tonic_last_note] + symbol;
            let middle_note = last_symbols.match(generic_note_pattern)[0].replace("r", "");
            last_symbols = last_symbols.replace(generic_note_pattern, "");
            last_note = last_symbols.match(note_pattern)[0];


            if (tonic_last_note == 1) {

                gabc = gabc.slice(0, -1);
                gabc += last_note.replace("(", "");
            }
            else if (tonic_last_note == 2) {

                gabc += remaining_syllables[remaining_syllables.length - tonic_last_note + 1] + last_note;
                remaining_syllables.pop();
            }
            else {
                let syllables_to_add = remaining_syllables.slice(tonic_last_note * (-1) + 1);
                for (let i = 0; i < syllables_to_add.length; i++) {
                    gabc += syllables_to_add.shift() + middle_note;
                }
                gabc += syllables_to_add.shift() + last_note;
            }
            remaining_symbols.shift();
            remaining_symbols.shift();
            for (let index = 0; index < tonic_last_note; index++) {
                remaining_syllables.pop();
            }
            last_symbols = remaining_symbols.join(" ");
            gabc += last_symbols;
            hit_last_note = true;
            break;
        }
        else if (!hit_last_note) {
            gabc += symbol;
            remaining_symbols.shift();
        }
        else {
            remaining_symbols.shift();
        }

    }
    note_to_add = generic_note.replace("r", "");
    while (remaining_syllables.length > notes_before_tonic.length) {
        let index = gabc.match(generic_note).index - 1;
        gabc = gabc.slice(0, index) + remaining_syllables.shift() + note_to_add + gabc.slice(index);
    }

    while (remaining_syllables.length > 0) {
        let note = notes_before_tonic.shift();
        if (note == undefined) {
            note = note_to_add;
        }
        let index = gabc.match(generic_note).index - 1;
        gabc = gabc.slice(0, index) + remaining_syllables.shift() + note + gabc.slice(index);
    }
    gabc = gabc.replace(generic_note, "");
    resultOutput.innerHTML = final;
    gabcSource.value = gabc;
    updateChant()
}

