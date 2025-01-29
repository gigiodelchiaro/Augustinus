const ADVANCED_CHECKBOX = document.getElementById('advanced');
const ADVANCED_ELEMENTS = document.querySelectorAll('.advanced');
const API_POCKET_CHECKBOX = document.getElementById('api-pocket');
const API_POCKET_ELEMENTS = document.querySelectorAll('.api-pocket');

ADVANCED_CHECKBOX.addEventListener('change', function () {
    ADVANCED_ELEMENTS.forEach(element => {
        element.style.display = this.checked ? 'block' : 'none';
    });
});

API_POCKET_CHECKBOX.addEventListener('change', function () {
    API_POCKET_ELEMENTS.forEach(element => {
        element.style.display = this.checked ? 'block' : 'none';
    });
});

async function getPocketTerco() {
    const date = document.getElementById('date').value;
    const base_url = "https://pocket.augustinus.workers.dev";
    const oracaoDoDiaElement = document.getElementById('oracaoDoDia');
    const sobreAsOferendasElement = document.getElementById('sobreAsOferendas');
    const depoisDaComunhaoElement = document.getElementById('depoisDaComunhao');

    const url = `${base_url}?date=${date}`;
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(url, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        const data = await response.json();
        // Update your element assignments like this:
        oracaoDoDiaElement.value = data.results.oracaoDoDia;
        sobreAsOferendasElement.value = data.results.sobreAsOferendas;
        depoisDaComunhaoElement.value = data.results.depoisDaComunhao;


        // return await response.json();
    } catch (error) {
        return console.error('Error:', error);
    }
}

var selected_model;
document.addEventListener('DOMContentLoaded', function () {
    const SELECT = document.getElementById('chant-type');
    const MODELS_JSON = `
{
    "define": [
        {
            "name": "Prefácio tom solene",
            "type": "prefacio",
            "tom": "solene",
            "optional_end": "",
            "optional_start": "O(f) Se(g)nhor(h) es(h)te(h)ja(f) con(g)vos(hg)co.(g) (::) E(f)le_es(g)tá(h) no(h) mei(h)o(f) de(g) nós(hg) (::Z) Co(g)ra(h)ções(i) ao(h) al(gh)to.(gf) (::) O(h) nos(h)so(h) co(g)ra(h)ção(i) es(h)tá(g) em(h) Deus.(gf) (::Z) De(hg)mos(f) gra(fg)ças(h) ao(g) Se(h)nhor(ih) nos(gf)so(gh) Deus.(ghg) (::) É(g) nos( g)so(g) de(h)ver(i) e(h) nos(h)sa(g) sal(h)va(g)ção.(gf) (::Z) ",
            "end": "(::)",
            "start": "(c4) ",
            "default": "(hr hr hr) (gf) (fg) (h) (ghr1) (gr) (g) (::)",
            "patterns": [
                {
                    "symbol": "*",
                    "gabc": "(g) (ir ir ir) (hg) (ghr1) (hr) (h) (:)"
                },
                {
                    "symbol": "+",
                    "gabc": "(g) (hr hr hr) (ir1) (hr) (h) (:)"
                }
            ],
            "find": [
                "Por isso,"
            ],
            "replace": [
                "Por(g) is(fg)so,(g) (;) "
            ]
        },
        {
            "name": "Prefácio tom simples",
            "type": "prefacio",
            "tom": "simples",
            "optional_end": "",
            "optional_start": "O(f) Se(g)nhor(h) es(h)te(h)ja(f) con(g)vos(h)co.(g) (::) E(f)le_es(g)tá(h) no(h) mei(h)o(f) de(g) nós(hg) (::Z) Co(g)ra(h)ções(i) ao(h) al(g)to.(g) (::) O(h) nos(h)so(h) co(g)ra(h)ção(i) es(h)tá(g) em(h) Deus.(gf) (::Z) De(ih)mos(g) gra(h)ças(h) ao(h) Se(h)nhor(h) nos(f)so(g) Deus.(hg) (::) É(g) nos( g)so(g) de(h)ver(i) e(h) nos(h)sa(g) sal(h)va(g)ção.(gf) (::Z)",
            "end": "(::)",
            "start": "(c4) ",
            "default": "(hr hr hr) (f) (g) (hr1) (gr) (g) (::)",
            "patterns": [
                {
                    "symbol": "*",
                    "gabc": "(g) (ir ir ir) (h) (g) (hr1) (hr) (h) (:)"
                },
                {
                    "symbol": "+",
                    "gabc": "(hr hr hr) (ir1) (hr) (h) (:)"
                }
            ],
            "find": [],
            "replace": []
        },
        {
            "name": "Oração tom solene",
            "type": "oracao",
            "tom": "solene",
            "optional_end": "A(g) mém.(gh) (::)",
            "optional_start": "O(h) re(gh) mos:(h) (:)",
            "end": "(::)",
            "start": "(c4) ",
            "default": "(g) (hr hr hr) (g) (g) (hr1) (hr) (h) (::)",
            "patterns": [
                {
                    "symbol": "*",
                    "gabc": "(g) (hr hr hr) (hr1) (gr) (g) (:)"
                },
                {
                    "symbol": "+",
                    "gabc": "(g) (hr hr hr) (hr1) (gr) (g) (:)"
                }
            ],
            "find": [
                "Por nosso Senhor Jesus Cristo, vosso Filho, que é Deus, e convosco vive e reina, na unidade do Espírito Santo, por todos os séculos dos séculos.",
                "Por Cristo nosso Senhor.",
                "Vós que sois Deus, e vós que viveis e reinais com o Pai, na unidade do Espírito Santo, por todos os séculos dos séculos.",
                "Ele que é Deus, e conosco vive e reina na unidade do Espírito Santo, por todos os séculos dos séculos.",
                "Ele que vive e reina pelos séculos dos séculos."
            ],
            "replace": [
                "Por(g) nos(h)so(h) Se(h)nhor(h) Je(h)sus(h) Cris(h)to,(h) vos(h)so(h) Fi(h)lho,(h) que(h) é(h) Deus,(hg) (:) e(g) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(h) na(h) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(g) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
                "Por(g) Cris(h)to,(h) nos(h)so(g) Se(h)nhor.(hg) (::)",
                "Vós(g) que(h) sois(h) Deus,(h) e(h) vi(h)veis(h) e(h) e(h) rei(h)nais(h) com(h) o(h) Pai,(hg) (:) na(g) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(g) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
                "E(g)le(h) que(h) é(h) Deus,(h) e(h) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(g) (:) na(g) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(g) San(h)to,(h) (:) por(g) to(h)dos(h) os(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)",
                "E(g)le(h) que(h) vi(h)ve(h) e(h) rei(h)na(h) pe(h)los(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(g)los.(g) (::)"
            ]
        },
        {
            "name": "Oração tom simples",
            "type": "oracao",
            "tom": "simples",
            "optional_end": "A(h) mém.(h) (::)",
            "optional_start": "O(h) re(h) mos:(h) (:)",
            "end": "(::)",
            "start": "(c4) ",
            "default": "(hr hr hr) (hr1) (hr) (h) (::)",
            "patterns": [
                {
                    "symbol": "*",
                    "gabc": "(hr hr hr) (g) (f) (hr1) (gr) (g) (:)"
                },
                {
                    "symbol": "+",
                    "gabc": "(hr hr hr) (hr1) (gr) (g) (:)"
                }
            ],
            "find": [
                "Por nosso Senhor Jesus Cristo, vosso Filho, que é Deus, e convosco vive e reina, na unidade do Espírito Santo, por todos os séculos dos séculos.",
                "Por Cristo nosso Senhor.",
                "Vós que sois Deus, e vós que viveis e reinais com o Pai, na unidade do Espírito Santo, por todos os séculos dos séculos.",
                "Ele que é Deus, e conosco vive e reina na unidade do Espírito Santo, por todos os séculos dos séculos.",
                "Ele que vive e reina pelos séculos dos séculos."
            ],
            "replace": [
                "Por(h) nos(h)so(h) Se(h)nhor(h) Je(h)sus(h) Cris(h)to,(h) vos(h)so(h) Fi(h)lho,(h) que(h) é(h) Deus,(hf) (:) e(h) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(h) na(h) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(f) San(h)to,(h) (:) por(h) to(h)dos(h) os(h) sé(h)cu(h)los(h) dos(h) sé(h)cu(h)los.(h) (::)",
                "Por(h) Cris(h)to,(h) nos(h)so(h) Se(h)nhor.(h) (::)",
                "Vós(h) que(h) sois(h) Deus,(h) e(h) vi(h)veis(h) e(h) e(h) rei(h)nais(h) com(h) o(h) Pai,(hf) (:) na(h) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(f) San(h)to,(h) (:) por(h) to(h)dos(h) os(h) sé(h)cu(h)los(h) dos(h) sé(h)cu(h)los.(h) (::)",
                "E(h)le(h) que(h) é(h) Deus,(h) e(h) con(h)vos(h)co(h) vi(h)ve(h) e(h) rei(h)na,(f) (:) na(h) u(h)ni(h)da(h)de(h) do(h) Es(h)pí(h)ri(g)to(f) San(h)to,(h) (:) por(h) to(h)dos(h) os(h) sé(h)cu(h)los(h) dos(h) sé(h)cu(h)los.(h) (::)",
                "E(h)le(h) que(h) vi(h)ve(g) e(f) rei(h)na(h) pe(h)los(h) sé(h)cu(h)los(g) dos(h) sé(h)cu(h)los.(h) (::)"
            ]
        },
        {
            "name": "Salmo tom VIII (em breve)",
            "type": "salmo",
            "tom": "VIII",
            "optional_end": "",
            "optional_start": "(g) (h)",
            "end": "(::)",
            "start": "(c4) ",
            "default": "(jr jr jr) (i) (j) (hr1) (gr) (g) (::Z)",
            "patterns": [
                {
                    "symbol": "*",
                    "gabc": "(jr jr jr) (kr1) (jr) (j) (:)"
                },
                {
                    "symbol": "+",
                    "gabc": "(jr jr jr) (jr1) (hr) (h) (:)"
                }
            ],
            "find": [],
            "replace": []
        },
        {
            "name": "Evangelho",
            "type": "evangelho",
            "tom": "",
            "optional_end": "Pa(h)la(h)vra(h) da(h) Sal(f)va(h)ção.(h) (::Z)Gló(h)ri(h)a_a(h) vós,(f) Se(h)nhor.(h) (::Z)",
            "optional_start": "O(h) Se(h)nhor(h) es(h)te(h)ja(h) con(h)vos(h)co.(h) (::Z)E(h)le(h) es(h)tá(h) no(h) meio(h) de(h) nós.(h) (::Z)",
            "end": "(::)",
            "start": "(c4) ",
            "default": "(hr hr hr) (f) (h) (hr1) (hr) (h) (:)",
            "patterns": [
                {
                    "symbol": "*",
                    "gabc": "(hr hr hr) (fghr1) (hr) (h)"
                },
                {
                    "symbol": "?",
                    "gabc": "(g) (gr gr gr) (f) (g) (ghr1) (hr) (h) (:)"
                },
                {
                    "symbol": ":",
                    "gabc": "(hr hr hr) (hr1) (hr) (h)"
                }
            ],
            "find": [],
            "replace": []
        }
    ]
}
    `;

    const DATA = JSON.parse(MODELS_JSON);
    const MODELS = DATA.define;
    models = MODELS;
    MODELS.forEach((model, index) => {
        const option = new Option(model.name, index);
        SELECT.add(option);
    });

    SELECT.addEventListener('change', function () {
        const selectedModel = MODELS[this.value];
        if (selectedModel) {
            selected_model = selectedModel;
        }
    });

    SELECT.dispatchEvent(new Event('change'));
});
function copyGabc() {
    const gabcOutput = document.getElementById('gabc').value;
    navigator.clipboard.writeText(gabcOutput);
}
function generateGabcNotation() {
    const syllableSeparator = "@";
    definirSeparador(syllableSeparator);
    const INPUT_TEXT = document.getElementById('text').value;
    const SHOULD_REMOVE_NUMBERS = document.getElementById('remove-numbers').checked;
    const SHOULD_ADD_OPTIONAL_END = document.getElementById('amen').checked;
    const SHOULD_ADD_OPTIONAL_START = document.getElementById('entoation').checked;
    const GABC_OUTPUT_ELEMENT = document.getElementById('gabc');
    const SELECTED_START_PATTERN = selected_model.start;
    const SELECTED_START_PATTERN_OPTIONAL = selected_model.optional_start;
    const SELECTED_END_PATTERN_OPTIONAL = selected_model.optional_end;
    const SELECTED_DEFAULT_PATTERN = selected_model.default;
    const SELECTED_END_PATTERN =  selected_model.end;
    const MODEL_REPEAT = SELECTED_DEFAULT_PATTERN.split("|");
    const BASIC_NOTE_REGEX = /[a-m][^\dr]/;
    const TONIC_NOTE_REGEX = /r1/;
    const GENERIC_NOTE_REGEX = /\(([a-z]r\s?)+\)/;

    let text = INPUT_TEXT;
    //text = text.replaceAll('-', '- ');
    text = text.replaceAll(' \n', '\n');

    if (selected_model.patterns) {
        for (let pattern of selected_model.patterns) {
            text = text.replaceAll(" " + pattern.symbol, pattern.symbol);
            text = text.replaceAll(pattern.symbol, pattern.symbol + '\n');
        }
    }

    text = text.replaceAll('.', '.\n');
    if (selected_model.tom === "solene" && selected_model.type === "prefacio")
    {
        text = text.replaceAll('Por isso,', 'Por isso,\n');
    }
    text = text.replaceAll(/\n+/gm, '\n');
    const TEXT_FRAGMENTS = text.split("\n");
    let gabcOutput = SELECTED_START_PATTERN;
    if (SHOULD_ADD_OPTIONAL_START) {
        gabcOutput += SELECTED_START_PATTERN_OPTIONAL;
    }

    let modelIndex = 0;
    for (let fragmentIndex = 0; fragmentIndex < TEXT_FRAGMENTS.length - 1; fragmentIndex++) {
        let isEnding = false;
        let currentText = TEXT_FRAGMENTS[fragmentIndex].trim();

        // Loop through all find/replace pairs
        for (let i = 0; i < selected_model.find.length; i++) {
            // Normalize both texts for comparison
            const cleanCurrent = currentText.toLowerCase().replaceAll(/\W+/gm, '');
            const cleanFind = selected_model.find[i].toLowerCase().replaceAll(/\W+/gm, '');

            if (cleanCurrent === cleanFind) {
                gabcOutput += selected_model.replace[i];
                isEnding = true;
                break;
            }
        }

        if (isEnding) {
            continue;
        }
        let processedTextFinal = "";
        if (SHOULD_REMOVE_NUMBERS) {
            currentText = currentText.replace(/\d/gm, '');
        }
        let words = currentText.split(" ");
        let separatedWords = [];
        for (let word of words) {
            separatedWords.push(separarTexto(word));

        }
        processedTextFinal = separatedWords.join(" ")
            .replaceAll(" ", `${syllableSeparator} `)
            .replaceAll(`${syllableSeparator}${syllableSeparator}`, syllableSeparator);
        let syllablesList = processedTextFinal.split(syllableSeparator).filter(s => s.trim() !== "");
        let remainingSyllables = syllablesList;

        let remainingSymbols;
        const LAST_SYLLABLE = syllablesList[syllablesList.length - 1];

        let isSpecialPattern = false;
        for (let i = 0; i < selected_model.patterns.length; i++) {
            let pattern = selected_model.patterns[i];
            if (processedTextFinal.endsWith(pattern.symbol)) {
                remainingSymbols = pattern.gabc.match(/\([^()]+\)/gm) || [];
                isSpecialPattern = true;
                if (selected_model.type != "evangelho") {
                    syllablesList[syllablesList.length - 1] = LAST_SYLLABLE.replace(pattern.symbol, '');
                }
                
                break;
            }
        }
       
        if (!isSpecialPattern) {
            remainingSymbols = MODEL_REPEAT[modelIndex].match(/\([^()]+\)/gm) || [];
            modelIndex = (modelIndex + 1) % MODEL_REPEAT.length;
            shouldAddLastSyllable = false;
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
            if (currentSymbol.match(TONIC_NOTE_REGEX)) {
                remainingSymbols.shift();
                let remainingSymbolsString = remainingSymbols.join(" ");
                currentSymbol = currentSymbol.replace("r1", "");
                gabcOutput += remainingSyllables[remainingSyllables.length - tonicSyllablePosition] + currentSymbol;
                let middleNoteSymbol = remainingSymbolsString.match(GENERIC_NOTE_REGEX)[0].replace("r", "");
                remainingSymbolsString = remainingSymbolsString.replace(GENERIC_NOTE_REGEX, "");
                let finalNoteSymbol = "(" + remainingSymbolsString.match(BASIC_NOTE_REGEX)[0];
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
            else if (currentSymbol.match(GENERIC_NOTE_REGEX)) {
                if (!hasGenericNote) {
                    genericNotePlaceholder = "(" + currentSymbol.match(/[a-m]/) + ")";
                    gabcOutput += currentSymbol;
                    hasGenericNote = true;
                }
                remainingSymbols.shift();
            }

            else if (currentSymbol.match(BASIC_NOTE_REGEX)) {
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
            let placeholderIndex = gabcOutput.match(GENERIC_NOTE_REGEX).index;
            gabcOutput = gabcOutput.slice(0, placeholderIndex) + remainingSyllables.shift() + genericNotePlaceholder + gabcOutput.slice(placeholderIndex);
        }
        while (remainingSyllables.length > 0) {
            let placeholderIndex = gabcOutput.match(GENERIC_NOTE_REGEX).index;

            gabcOutput = gabcOutput.slice(0, placeholderIndex) + remainingSyllables.shift() + preTonicNotes.shift() + gabcOutput.slice(placeholderIndex);
        }
        gabcOutput = gabcOutput.replace(/([a-h])\1/gm, "$1");
        gabcOutput = gabcOutput.replace(GENERIC_NOTE_REGEX, "");
        gabcOutput = gabcOutput.replace(GENERIC_NOTE_REGEX, "");

    }
    if (SHOULD_ADD_OPTIONAL_END) {
        gabcOutput += SELECTED_END_PATTERN_OPTIONAL;
    }
    gabcOutput += SELECTED_END_PATTERN;
    GABC_OUTPUT_ELEMENT.value = gabcOutput;
    initializeAndLayoutChant("gabc", "svg-final");
}