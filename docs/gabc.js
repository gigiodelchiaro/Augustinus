 /* testing exsurge */
 var ctxt = new exsurge.ChantContext();
 ctxt.lyricTextFont = "'Crimson Text', serif";
 ctxt.lyricTextSize *= 1.2;
 ctxt.dropCapTextFont = ctxt.lyricTextFont;
 ctxt.annotationTextFont = ctxt.lyricTextFont;
 var score;
 var gabcSource = document.getElementById('gabcSource');
 var chantContainer = document.getElementById('chant-container');
 //
 // to use canvas drawing, you should use the canvas object belonging to the
 // canvas, resizing it as below. The reason for custom resizing is that the
 // canvas drawing takes into consideration screen dpi in order to render
 // the highest possibly quality on lots of different screens.
 // 
 // 
 // document.querySelector('body').appendChild(ctxt.canvas);
 // ctxt.setCanvasSize(1280, 720);
 //
 // To render to the canvas, you can use a standard animation loop, which
 // draws to the canvas at the desired intervals, e.g.:
 // 
 //
 // function animloop() {
 //   requestAnimationFrame(animloop);
 //
 //   if (score)
 //     score.draw(ctxt);
 // };
 //
 var updateChant = function() {
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
 var layoutChant = function() {
   // perform layout on the chant
   score.performLayoutAsync(ctxt, function() {
     score.layoutChantLines(ctxt, chantContainer.clientWidth, function() {
       // render the score to svg code
       chantContainer.innerHTML = score.createSvg(ctxt);
     });
   });
 }