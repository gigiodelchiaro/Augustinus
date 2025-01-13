const strongAccents = 'áéíóúàèìòùâêîôûÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛ';
const weakAccents = 'ãẽĩõũÃẼĨÕŨ';
const ponctuation = '.,:;?!"';

function cleanSyllable(syllable) {
    return syllable.replace(/\.\,\:\;\?\!/gm, '');
}
function tonic(wordSeparated) {
    if (wordSeparated.length == 1) {return 1}
    // Check for strong accents
    for (let i = wordSeparated.length - 1; i >= 0; i--) {
        const syllable = cleanSyllable(wordSeparated[i]);
        if ([...syllable].some(char => strongAccents.includes(char))) {
            return wordSeparated.length - i;
        }
    }

    // Check for weak accents
    for (let i = wordSeparated.length - 1; i >= 0; i--) {
        const syllable = cleanSyllable(wordSeparated[i]);
        if ([...syllable].some(char => weakAccents.includes(char))) {
            return wordSeparated.length - i;
        }
    }

    // Check the last syllable
    const lastSyllable = wordSeparated[wordSeparated.length - 1];
    if (/(i(s)?|u|z|im|us|r|l|x|n|um(s)?|ps|om|on(s)?)(\W+)?$/.test(lastSyllable)) {
        return 1;
    }
    
    return 2;
}
