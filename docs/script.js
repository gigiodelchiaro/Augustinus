
function setModel() {
    document.getElementById('model').innerHTML = document.getElementById("models").value;
    initializeAndLayoutChant("model", "svg-model");
}


function processText() {

    const separator = "@";
    definirSeparador(separator);

    const textInput = document.getElementById('text').value;
    const removeNumbers = document.getElementById('remove-numbers').checked;
    const gabcSource = document.getElementById('gabc');

    const model = document.getElementById('model').value;
    const all_symbols = model.match(/\([^\(\)]+\)/gm);
    const note_pattern = /[a-m][^\dr]/;
    const tonic_pattern = /\([^\s]+r1\)/;
    const generic_note_pattern = /\(([a-z]r\s?)+\)/;
    const fragments = textInput.split("\n");

    

    let gabc = "";
    for (let f = 0; f < fragments.length; f++) {
        let text = fragments[f];
        console.log(text);
        let final = ""

        if (removeNumbers) {
            text = text.replace(/\d/gm, '');
        }

        const processedText = separarTexto(text).replaceAll(" ", separator + " ");
        final = processedText

        let all_syllables = final.split(separator);

        
        let remaining_syllables = all_syllables;

        let hit_generic_note = false;
        let hit_last_note = false;

        let words = final.split(" ");
        let tonic_last_note = tonica(words[words.length - 1].split(separator));

        let generic_note = "";
        let remaining_symbols = all_symbols;
        let notes_before_tonic = [];

        while (remaining_symbols.length > 0) {
            let symbol = remaining_symbols[0];

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
                    generic_note = "(" + symbol.match(/[a-m]/) + ")";
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
                last_note = "(" + last_symbols.match(note_pattern)[0];


                if (tonic_last_note == 1) {

                    gabc = gabc.slice(0, -1);
                    gabc += last_note.replace("(", "");
                }

                else {
                    let syllables_to_add = remaining_syllables.slice(tonic_last_note * (-1) + 1);
                    for (let i = 1; i < syllables_to_add.length; i++) {
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
                //gabc += last_symbols;
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

        while (remaining_syllables.length > notes_before_tonic.length) {
            let index = gabc.match(generic_note_pattern).index;
            gabc = gabc.slice(0, index) + remaining_syllables.shift() + generic_note + gabc.slice(index);
        }

        while (remaining_syllables.length > 0) {
            let index = gabc.match(generic_note_pattern).index;
            gabc = gabc.slice(0, index) + remaining_syllables.shift() + notes_before_tonic.shift() + gabc.slice(index);
        }
        gabc = gabc.replace(generic_note_pattern, "");
        gabc = gabc.replace(generic_note_pattern, "");
    }
    gabcSource.value = gabc;
    initializeAndLayoutChant("gabc", "svg-final");
    initializeAndLayoutChant("model", "svg-model");
}

