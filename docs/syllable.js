// Function to load JSON rules file
async function loadRules(filePath) {
    const response = await fetch(filePath);
    return await response.json();
}

// Function to prepare patterns by substituting placeholders
function preparePatterns(rules) {
    const vogais = rules.vogais_fortes + rules.vogais_fracas;
    const digrafos = rules.digrafos;

    rules.padroes.forEach(pattern => {
        // Replace placeholders in regex
        pattern.regex = pattern.regex
            .replace("{consoantes_fortes}", rules.consoantes_fortes)
            .replace("{consoantes_fracas}", rules.consoantes_fracas)
            .replace("{vogais}",vogais)
            .replace("{vogais_fortes}", rules.vogais_fortes)
            .replace("{digrafos}", digrafos)
            .replace("{divisor}", rules.divisor);

        // Replace placeholders in replacement string
        pattern.replace = pattern.replace
            .replace("{divisor}", rules.divisor)
            .replace("{\\1}", "$1")
            .replace("{\\2}", "$2")
            .replace("{\\3}", "$3")

    });
    return rules;
}

// Function to apply rules to the input text
function applyRules(text, rules) {
    rules.padroes.forEach(pattern => {
        const regex = new RegExp(pattern.regex, 'g');
        text = text.replace(regex, pattern.replace);
    });
    return text;
}
