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
