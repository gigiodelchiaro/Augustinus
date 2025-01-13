// Define character groups
const strongConsonants = 'bcdfjkpqtvxçBCDFJKPQTVCÇ';
const weakConsonants = 'glmnrszGLMNRSZ';
const strongVowels = 'aeáéóàèòãẽõâêôäëöíúìùĩũîûAEÁÉÓÀÈÒÃẼÕÂÊÔÄËÖÍÚÌÙĨŨÎÛ';
const weakVowels = 'iouïüIOUÏÜ';

const digraphs = ['nh', 'lh', 'ch', 'gu', 'qu'];
const vowels = strongVowels + weakVowels;

function applyRules(text, divider = '-') {
    // Rule 1: Add the divider before any strong consonant
    let patternStrong = new RegExp(`([${strongConsonants}])`, 'g');
    text = text.replace(patternStrong, `${divider}$1`);

    // Rule 2: Add the divider before any weak consonant if followed by a vowel and not preceded by a strong consonant
    let patternWeak = new RegExp(`(?<![${strongConsonants}])([${weakConsonants}])(?=[${vowels}])`, 'g');
    text = text.replace(patternWeak, `${divider}$1`);

    // Rule 3: Add the divider before every digraph
    for (let digraph of digraphs) {
        let patternDigraph = new RegExp(`(${digraph})`, 'g');
        text = text.replace(patternDigraph, `${divider}$1`);
    }

    // Rule 4: Add the divider between every weak and strong vowel encounter
    let patternWeakStrongVowel = new RegExp(`([${vowels}])([${strongVowels}])`, 'g');
    text = text.replace(patternWeakStrongVowel, `$1${divider}$2`);

    // Exception 1: Prevent adding the divider before 'x' if followed by a weak consonant
    let patternXWeak = new RegExp(`(?<!${divider})(x)(?=[${weakConsonants}])`, 'g');
    text = text.replace(patternXWeak, '$1');

    // Exception 2: 'gu' and 'qu' expect a vowel after them
    let patternGuQu = new RegExp(`(gu|qu)${divider}`, 'g');
    text = text.replace(patternGuQu, '$1');

    return text;
}

function cleanUp(text, divider = '-') {
    // Clean-up Rule 1: If there is a divider before and after a strong consonant, remove the first
    let patternDoubleDivider = new RegExp(`${divider}([${strongConsonants}])${divider}`, 'g');
    text = text.replace(patternDoubleDivider, `$1${divider}`);

    // Clean-up Rule 2: Remove multiple dividers
    let patternMultipleDividers = new RegExp(`${divider}+`, 'g');
    text = text.replace(patternMultipleDividers, divider);

    // Clean-up Rule 3: Remove leading dividers
    text = text.replace(new RegExp(`^${divider}+`), '');

    // Clean-up Rule 4: Remove misplaced spaces around dividers
    text = text.replace(new RegExp(` ${divider}`, 'g'), ' ');
    text = text.replace(new RegExp(`${divider} `, 'g'), ' ');

    return text;
}

function separateWord(text, divider = '-') {
    let modifiedText = applyRules(text, divider);
    return cleanUp(modifiedText, divider);
}
