
const advancedCheckbox = document.getElementById('advanced');
const advancedElements = document.querySelectorAll('.advanced');
advancedCheckbox.addEventListener('change', function () {

    advancedElements.forEach(element => {

        element.style.display = this.checked ? 'block' : 'none';
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const select = document.getElementById('chant-type');
    const startElement = document.getElementById('start');
    const flexaTemplateElement = document.getElementById('flexa');
    const asteriscTemplateElement = document.getElementById('asterisc');
    const selectedDefaultPatternElement = document.getElementById('default');
    const selectedStartPatternOptionalElement = document.getElementById('start-opt');
    const selectedEndPatternOptionalElement = document.getElementById('end-opt');
    const modelsJson = `{
    "define":[
        {
            "name": "Salmo tom VIII",
            "type": "salmo",
            "optional_end": "",
            "optional_start": "(g) (h)",
            "start": "(c4) ",
            "default": "(j) (jr jr jr) (i) (j) (hr1) (gr) (g) (::Z)",
            "flexa": "(jr jr jr) (jr1) (hr) (h) (:)",
            "asterisc": "(jr jr jr) (kr1) (jr) (j) (:)"
        },
        {
            "name": "Prefácio tom solene",
            "type": "prefacio",
            "optional_end": "",
            "optional_start": "O(f) Se(g)nhor(h) es(h)te(h)ja(f) con(g)vos(hg)co.(g)(::) E(f)le_es(g)tá(h) no(h) mei(h)o(f) de(g) nós(hg)(::Z) Co(g)ra(h)ções(i) ao(h) al(gh)to.(gf)(::) O(h) nos(h)so(h) co(g)ra(h)ção(i) es(h)tá(g) em(h) Deus.(gf)(::Z) De(hg)mos(f) gra(fg)ças(h) ao(g) Se(h)nhor(ih) nos(gf)so(gh) Deus.(ghg)(::) É(g) nos( g)so(g) de(h)ver(i) e(h) nos(h)sa(g) sal(h)va(g)ção.(gf)(::Z) ",
            "start": "(c4) ",
            "default": "(hr hr hr) (gf) (fg) (h) (ghr1) (gr) (g) (::)",
            "flexa": "(g) (hr hr hr) (ir1) (hr) (h) (:)",
            "asterisc": "(g) (ir ir ir) (hg) (ghr1) (hr) (h) (:)"
        },
        {
            "name": "Prefácio tom simples",
            "type": "prefacio",
            "optional_end": "",
            "optional_start": "O(f) Se(g)nhor(h) es(h)te(h)ja(f) con(g)vos(h)co.(g)(::) E(f)le_es(g)tá(h) no(h) mei(h)o(f) de(g) nós(hg)(::Z) Co(g)ra(h)ções(i) ao(h) al(g)to.(g)(::) O(h) nos(h)so(h) co(g)ra(h)ção(i) es(h)tá(g) em(h) Deus.(gf)(::Z) De(ih)mos(g) gra(h)ças(h) ao(h) Se(h)nhor(h) nos(f)so(g) Deus.(hg)(::) É(g) nos( g)so(g) de(h)ver(i) e(h) nos(h)sa(g) sal(h)va(g)ção.(gf)(::Z)",
            "start": "(c4) ",
            "default": "(hr hr hr) (f) (g) (hr1) (gr) (g) (::)",
            "flexa": "(hr hr hr) (ir1) (hr) (h) (:)",
            "asterisc": "(g) (ir ir ir) (h) (g) (hr1) (hr) (h) (:)"
        },
        {
            "name": "Oração tom solene",
            "type": "oracao",
            "optional_end": "A(g) mém.(gh) (::)",
            "optional_start": "O(h) re(gh) mos:(h) (:)",
            "start": "(c4) ",
            "default": "(g) (hr hr hr) (g) (g) (hr1) (hr) (h) (::)",
            "flexa": "(g) (hr hr hr) (hr1) (gr) (g) (:)",
            "asterisc": "(g) (hr hr hr) (hr1) (gr) (g) (:)"
        }
        
        
    ]
}`;
    
    const data = JSON.parse(modelsJson);
    const models = data.define;

    models.forEach((model, index) => {
        const option = new Option(model.name, index);
        select.add(option);
    });

    select.addEventListener('change', function () {
        const selectedModel = models[this.value];
        if (selectedModel) {
            startElement.value = selectedModel.start;
            flexaTemplateElement.value = selectedModel.flexa;
            asteriscTemplateElement.value = selectedModel.asterisc;
            selectedDefaultPatternElement.value = selectedModel.default;
            selectedStartPatternOptionalElement.value = selectedModel.optional_start;
            selectedEndPatternOptionalElement.value = selectedModel.optional_end;

        }
    });

    select.dispatchEvent(new Event('change'));
});
function copyGabc() {
    const gabcOutput = document.getElementById('gabc').value;
    navigator.clipboard.writeText(gabcOutput);
}
function generateGabcNotation() {
    const syllableSeparator = "@";
    definirSeparador(syllableSeparator);
    const inputText = document.getElementById('text').value;
    const shouldRemoveNumbers = document.getElementById('remove-numbers').checked;
    const shouldAddOptionalEnd = document.getElementById('amen').checked;
    const shouldAddOptionalStart = document.getElementById('entoation').checked;
    const gabcOutputElement = document.getElementById('gabc');
    const flexaTemplate = document.getElementById('flexa').value;
    const asteriscTemplate = document.getElementById('asterisc').value;
    const selectedDefaultPattern = document.getElementById('default').value;
    const selectedStartPattern = document.getElementById('start').value;
    const selectedStartPatternOptional = document.getElementById('start-opt').value;
    const selectedEndPatternOptional = document.getElementById('end-opt').value;
    const modelRepeat = selectedDefaultPattern.split("|");
    const basicNoteRegex = /[a-m][^\dr]/;
    const tonicNoteRegex = /r1/;
    const genericNoteRegex = /\(([a-z]r\s?)+\)/;
    let text = inputText;
    //text = text.replaceAll('-', '- ');
    text = text.replaceAll(' \n', '\n');
    text = text.replaceAll("+", '+\n');
    text = text.replaceAll("*", '*\n');
    text = text.replaceAll('.', '.\n');
    text = text.replaceAll(/\n+/gm, '\n');
    const textFragments = text.split("\n");
    let gabcOutput = selectedStartPattern;
    if(shouldAddOptionalStart){
        gabcOutput += selectedStartPatternOptional;
    }
    const endings = [
        "Por nosso Senhor Jesus Cristo, vosso Filho, que é Deus, e convosco vive e reina, na unidade do Espírito Santo, por todos os séculos dos séculos.",
        "Por(g) nos(h)so(h) Se(h)nhor(h) Je(h)sus(h) Cris(h)to,(h) vos(h)so(h) Fi(h)lho,(h) que(h) é(h) Deus,(hg) (:) e(g) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(h) na(h) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(g) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
        "Por Cristo nosso Senhor.",
        "Por(g) Cris(h)to,(h) nos(h)so(g) Se(h)nhor.(hg) (::)",
        "Vós que sois Deus, e vós que viveis e reinais com o Pai, na unidade do Espírito Santo, por todos os séculos dos séculos.",
        "Vós(g) que(h) sois(h) Deus,(h) e(h) vi(h)veis(h) e(h) e(h) rei(h)nais(h) com(h) o(h) Pai,(hg) (:) na(g) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(g) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
        "Ele que é Deus, e conosco vive e reina na unidade do Espírito Santo, por todos os séculos dos séculos.",
        "E(g)le(h) que_é(h) Deus,(h) e(h) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(g) (:) na(g) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(g) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
        "Ele que vive e reina pelos séculos dos séculos.",
        "E(g)le(h) que(h) vi(h)ve(h) e(h) rei(h)na(h) pe(h)los(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los(g) (::)"
    ];
    let modelIndex = 0;
    for (let fragmentIndex = 0; fragmentIndex < textFragments.length - 1; fragmentIndex++) {
        let isEnding = false;
        let currentText = textFragments[fragmentIndex].trim();
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
        let words = currentText.split(" ");
        let separatedWords = [];
        for (let word of words)
        {
            separatedWords.push(separarTexto(word));

        }
        processedTextFinal = separatedWords.join(" ")
            .replaceAll(" ", `${syllableSeparator} `)
            .replaceAll(`${syllableSeparator}${syllableSeparator}`, syllableSeparator);
        let syllablesList = processedTextFinal.split(syllableSeparator).filter(s => s.trim() !== "");
        let remainingSyllables = syllablesList;
        
        let remainingSymbols;
        const lastSymbol = syllablesList[syllablesList.length - 1];
        if (lastSymbol === " +") {
            syllablesList.pop();
            remainingSymbols = flexaTemplate.match(/\([^()]+\)/gm) || [];
        } else if (lastSymbol === " *") {
            syllablesList.pop();
            remainingSymbols = asteriscTemplate.match(/\([^()]+\)/gm) || [];
        } else {
            remainingSymbols = modelRepeat[modelIndex].match(/\([^()]+\)/gm) || [];
            modelIndex = (modelIndex + 1) % modelRepeat.length;
        }
        processedTextFinal = syllablesList.join(syllableSeparator);
        
        const wordsArray = processedTextFinal
            .split(' ')
            .filter(word => word.trim() !== '');
        let tonicSyllablePosition = -1;
        if (wordsArray.length > 0) {
            const lastWord = wordsArray[wordsArray.length - 1];
            tonicSyllablePosition = tonica(lastWord.split(syllableSeparator));
        }
        let genericNotePlaceholder = "";
        let preTonicNotes = [];
        let hasGenericNote = false;
        let hasLastNote = false;
        while (remainingSymbols.length > 0) {
            let currentSymbol = remainingSymbols[0];
            if (currentSymbol.match(tonicNoteRegex)) {
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
            else if (currentSymbol.match(genericNoteRegex)) {
                if (!hasGenericNote) {
                    genericNotePlaceholder = "(" + currentSymbol.match(/[a-m]/) + ")";
                    gabcOutput += currentSymbol;
                    hasGenericNote = true;
                }
                remainingSymbols.shift();
            }

            else if (currentSymbol.match(basicNoteRegex)) {
                if (hasGenericNote) {
                    preTonicNotes.push(currentSymbol);
                } else {
                    gabcOutput += remainingSyllables.shift() + currentSymbol;
                }
                remainingSymbols.shift();
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
    if (shouldAddOptionalEnd) {
        gabcOutput += selectedEndPatternOptional;
    }
    gabcOutputElement.value = gabcOutput;
    initializeAndLayoutChant("gabc", "svg-final");
}