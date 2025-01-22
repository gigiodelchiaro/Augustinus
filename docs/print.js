function printGabc() {
    document.body.classList.add('is-printing');
    updateAll();
}
function unprintGabc() {
    document.body.classList.remove('is-printing');
    updateAll();
}