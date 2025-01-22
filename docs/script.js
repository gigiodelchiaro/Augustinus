// Get the checkbox and advanced elements
const advancedCheckbox = document.getElementById('advanced');
const advancedElements = document.querySelectorAll('.advanced');

// Add an event listener to the checkbox
advancedCheckbox.addEventListener('change', function () {
    // Loop through all advanced elements
    advancedElements.forEach(element => {
        // Toggle visibility based on checkbox state
        element.style.display = this.checked ? 'block' : 'none';
    });
});
function printGabc() {

    document.body.classList.add('is-printing');
    updateAll();
}
function unprintGabc() {

    document.body.classList.remove('is-printing');
    updateAll();
}
function updateModelDisplay() {
    document.getElementById('model').innerHTML = document.getElementById("models").value;
    initializeAndLayoutChant("model", "svg-model");
}
function generateGabcNotation() {
    const syllableSeparator = "@";
    definirSeparador(syllableSeparator);

    const inputText = document.getElementById('text').value;
    const shouldRemoveNumbers = document.getElementById('remove-numbers').checked;
    const gabcOutputElement = document.getElementById('gabc');
    const flexaTemplateElement = document.getElementById('flexa');
    const selectedModelPattern = document.getElementById('model').value;

    const modelSymbols = selectedModelPattern.match(/\([^\(\)]+\)/gm);
    const basicNoteRegex = /[a-m][^\dr]/;
    const tonicNoteRegex = /\([^\s]+r1\)/;
    const genericNoteRegex = /\(([a-z]r\s?)+\)/;
    let text = inputText;
    text = text.replace("+", '+\n');
    text = text.replace('.', '.\n');
    text = text.replace(/\n+/, '\n');
    const textFragments = text.split("\n");
    let gabcOutput = "";
    const endings = [
        "Por nosso Senhor Jesus Cristo, vosso Filho, que é Deus, e convosco vive e reina, na unidade do Espírito Santo, por todos os séculos dos séculos.",
        "Por(g) nos(h)so(h) Se(h)nhor(h) Je(h)sus(h) Cris(h)to,(h) vos(h)so(h) Fi(h)lho,(h) que(h) é(h) Deus,(hg) (:) e(g) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(h) na(h) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(g)ri(g)to(h) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
        "Por Cristo nosso Senhor.",
        "Por(g) Cris(h)to,(h) nos(h)so(g) Se(h)nhor.(hg) (::)",
        "Vós que sois Deus, e vós que viveis e reinais com o Pai, na unidade do Espírito Santo, por todos os séculos dos séculos.",
        "Vós(g) que(h) sois(h) Deus,(h) e(h) vi(h)veis(h) e(h) e(h) rei(h)nais(h) com(h) o(h) Pai,(hg) (:) na(g) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(g)ri(g)to(h) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
        "Ele que é Deus, e conosco vive e reina na unidade do Espírito Santo, por todos os séculos dos séculos.",
        "E(g)le(h) que_é(h) Deus,(h) e(h) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(g) (:) na(g) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(g)ri(g)to(h) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
        "Ele que vive e reina pelos séculos dos séculos.",
        "E(g)le(h) que(h) vi(h)ve(h) e(h) rei(h)na(h) pe(h)los(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los(g) (::)"
    ];
    for (let fragmentIndex = 0; fragmentIndex < textFragments.length; fragmentIndex++) {
        let isEnding = false;
        let currentText = textFragments[fragmentIndex];
        for (let endingIndex = 0; endingIndex < endings.length / 2; endingIndex++) {
            if (currentText.toLowerCase().replaceAll(/\W+/gm, '') == endings[endingIndex].toLowerCase().replaceAll(/\W+/gm, '')) {
                gabcOutput += endings[endingIndex + 1];
                isEnding = true;
                break;
            }
        }
        if (isEnding) {
            continue;
        }
        let processedTextFinal = "";

        if (shouldRemoveNumbers) {
            currentText = currentText.replace(/\d/gm, '');
        }

        const processedText = separarTexto(currentText).replaceAll(" ", syllableSeparator + " ");
        processedTextFinal = processedText;

        let syllablesList = processedTextFinal.split(syllableSeparator);
        let remainingSymbols = modelSymbols;
        let remainingSyllables = syllablesList;
        if (remainingSyllables[remainingSyllables.length - 1] == " +") {
            remainingSyllables.pop();
            remainingSymbols = flexaTemplateElement.value.match(/\([^\(\)]+\)/gm);
        }

        let hasGenericNote = false;
        let hasLastNote = false;

        let wordsArray = processedTextFinal.replace(" +", "").split(" ");
        let tonicSyllablePosition = tonica(wordsArray[wordsArray.length - 1].split(syllableSeparator));
        let genericNotePlaceholder = "";
        let preTonicNotes = [];

        while (remainingSymbols.length > 0) {
            let currentSymbol = remainingSymbols[0];

            if (currentSymbol.match(basicNoteRegex)) {
                if (hasGenericNote) {
                    preTonicNotes.push(currentSymbol);
                } else {
                    gabcOutput += remainingSyllables.shift() + currentSymbol;
                }
                remainingSymbols.shift();
            }
            else if (currentSymbol.match(genericNoteRegex)) {
                if (!hasGenericNote) {
                    genericNotePlaceholder = "(" + currentSymbol.match(/[a-m]/) + ")";
                    gabcOutput += currentSymbol;
                    hasGenericNote = true;
                }
                remainingSymbols.shift();
            }
            else if (currentSymbol.match(tonicNoteRegex)) {
                remainingSymbols.shift();
                let remainingSymbolsString = remainingSymbols.join(" ");
                currentSymbol = currentSymbol.replace("r1", "");

                gabcOutput += remainingSyllables[remainingSyllables.length - tonicSyllablePosition] + currentSymbol;

                let middleNoteSymbol = remainingSymbolsString.match(genericNoteRegex)[0].replace("r", "");
                remainingSymbolsString = remainingSymbolsString.replace(genericNoteRegex, "");
                let finalNoteSymbol = "(" + remainingSymbolsString.match(basicNoteRegex)[0];

                if (tonicSyllablePosition == 1) {
                    gabcOutput = gabcOutput.slice(0, -1);
                    gabcOutput += finalNoteSymbol.replace("(", "");

                } else {
                    let syllablesToInsert = remainingSyllables.slice(tonicSyllablePosition * (-1) + 1);
                    for (let i = 1; i < syllablesToInsert.length; i++) {
                        gabcOutput += syllablesToInsert.shift() + middleNoteSymbol;
                    }
                    gabcOutput += syllablesToInsert.shift() + finalNoteSymbol;
                }

                remainingSymbols.shift();
                remainingSymbols.shift();
                for (let index = 0; index < tonicSyllablePosition; index++) {
                    remainingSyllables.pop();
                }
                remainingSymbolsString = remainingSymbols.join(" ");
                gabcOutput += " " + remainingSymbolsString;
                hasLastNote = true;
                break;
            }
            else if (!hasLastNote) {
                gabcOutput += currentSymbol;
                remainingSymbols.shift();
            } else {
                remainingSymbols.shift();
            }
        }

        while (remainingSyllables.length > preTonicNotes.length) {
            let placeholderIndex = gabcOutput.match(genericNoteRegex).index;
            gabcOutput = gabcOutput.slice(0, placeholderIndex) + remainingSyllables.shift() + genericNotePlaceholder + gabcOutput.slice(placeholderIndex);
        }

        while (remainingSyllables.length > 0) {
            let placeholderIndex = gabcOutput.match(genericNoteRegex).index;
            gabcOutput = gabcOutput.slice(0, placeholderIndex) + remainingSyllables.shift() + preTonicNotes.shift() + gabcOutput.slice(placeholderIndex);
        }
        gabcOutput = gabcOutput.replace(/([a-h])\1/gm, "$1");
        gabcOutput = gabcOutput.replace(genericNoteRegex, "");
        gabcOutput = gabcOutput.replace(genericNoteRegex, "");
    }

    gabcOutputElement.value = gabcOutput;
    initializeAndLayoutChant("gabc", "svg-final");
    initializeAndLayoutChant("model", "svg-model");
    initializeAndLayoutChant("flexa", "svg-flexa");
}