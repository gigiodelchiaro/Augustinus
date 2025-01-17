if (addTonics) {
    let words = text.split(/\s+/gm);
    let result = "";
    for (let word of words) {
        let syllables = separarTexto(word).split(separator);
        let tonicNumber = tonica(syllables);
        tonicNumber = syllables.length - tonicNumber;
        syllables[tonicNumber] = "<b>" + syllables[tonicNumber] + "</b>";
        result += syllables.join(separator)
        if (addEnd) {
            result += separator;
        }
        result += " ";

    }
    final = result;
}