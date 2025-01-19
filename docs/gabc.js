function initializeAndLayoutChant(gabcSourceId, chantContainerId) {
    var ctxt = new exsurge.ChantContext();
    ctxt.lyricTextFont = "'Crimson Text', serif";
    ctxt.lyricTextSize *= 1.2;
    ctxt.dropCapTextFont = ctxt.lyricTextFont;
    ctxt.annotationTextFont = ctxt.lyricTextFont;

    var gabcSource = document.getElementById(gabcSourceId);
    var chantContainer = document.getElementById(chantContainerId);

    if (!gabcSource || !chantContainer) {
        console.error("Invalid element IDs provided.");
        return;
    }

    var score;
    
    function updateChant() {
        if (score) {
            exsurge.Gabc.updateMappingsFromSource(ctxt, score.mappings, gabcSource.value);
            score.updateNotations(ctxt);
        } else {
            var mappings = exsurge.Gabc.createMappingsFromSource(ctxt, gabcSource.value);
            score = new exsurge.ChantScore(ctxt, mappings, true);
            score.annotation = new exsurge.Annotation(ctxt, "%V%");
        }
        layoutChant();
    }

    function layoutChant() {
        if (!score) return;
        
        score.performLayoutAsync(ctxt, function () {
            score.layoutChantLines(ctxt, chantContainer.clientWidth, function () {
                // Render the score to SVG code
                chantContainer.innerHTML = score.createSvg(ctxt);
            });
        });
    }

    updateChant();

    gabcSource.addEventListener('input', updateChant);  // Optional: Update chant on input change
}

function updateAll() {
    const gabcElements = document.getElementsByClassName('gabc-source');
    const chantElements = document.getElementsByClassName('chant-container');
    for (let i = 0; i < gabcElements.length; i++) {
            const gabcElement = gabcElements[i];
            const gabcClasses = Array.from(gabcElement.classList);
            for (let j = 0; j < chantElements.length; j++) {
                const chantElement = chantElements[j];
                const chantClasses = Array.from(chantElement.classList);
                // Check if elements share the same classes (excluding the base classes)
                const gabcOtherClasses = gabcClasses.filter(c => c !== 'gabc-source' && c !== 'gabc');
                const chantOtherClasses = chantClasses.filter(c => c !== 'chant-container' && c !== 'gabc');
                
                if (gabcOtherClasses.length === chantOtherClasses.length &&
                    gabcOtherClasses.every(c => chantOtherClasses.includes(c))) {
                    initializeAndLayoutChant(gabcElement.id, chantElement.id);
                    break;
                }
            }
        }
    
}