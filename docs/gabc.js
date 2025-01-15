/* testing exsurge */
var ctxt = new exsurge.ChantContext();
ctxt.lyricTextFont = "'Crimson Text', serif";
ctxt.lyricTextSize *= 1.2;
ctxt.dropCapTextFont = ctxt.lyricTextFont;
ctxt.annotationTextFont = ctxt.lyricTextFont;
var score;
var gabcSource = document.getElementById('gabcSource');
var chantContainer = document.getElementById('chant-container');

var updateChant = function () {
	if (score) {
		exsurge.Gabc.updateMappingsFromSource(ctxt, score.mappings, gabcSource.value);
		score.updateNotations(ctxt);
	} else {
		mappings = exsurge.Gabc.createMappingsFromSource(ctxt, gabcSource.value);
		score = new exsurge.ChantScore(ctxt, mappings, true);
		score.annotation = new exsurge.Annotation(ctxt, "%V%");
	}
	layoutChant();
}
var layoutChant = function () {
	// perform layout on the chant
	score.performLayoutAsync(ctxt, function () {
		score.layoutChantLines(ctxt, chantContainer.clientWidth, function () {
			// render the score to svg code
			chantContainer.innerHTML = score.createSvg(ctxt);
		});
	});
}