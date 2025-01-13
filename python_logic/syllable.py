import re

# Definir grupos de caracteres
consoantes_fortes = 'bcdfjkpqtvxç'
consoantes_fracas = 'glmnrsz'
vogais_fortes = 'aeáéóàèòãẽõâêôäëíúìùĩũîû'
vogais_fracas = 'iouïöü'

consoantes_fortes = consoantes_fortes + consoantes_fortes.upper()
consoantes_fracas = consoantes_fracas + consoantes_fracas.upper()
vogais_fortes = vogais_fortes + vogais_fortes.upper()
vogais_fracas = vogais_fracas + vogais_fracas.upper()
dígrafos = ['nh', 'lh', 'ch', 'gu', 'qu'] 
vogais = vogais_fortes + vogais_fracas

def aplicar_regras(texto, divisor="-"):
    # Regra 1: Adicionar o divisor antes de qualquer consoante forte
    padrao_forte = rf"([{consoantes_fortes}])"
    texto = re.sub(padrao_forte, rf"{divisor}\1", texto)

    # Regra 2: Adicionar o divisor antes de qualquer consoante fraca se seguida por uma vogal e não precedida por uma consoante forte
    padrao_fraca = rf"(?<![{consoantes_fortes}])([{consoantes_fracas}])(?=[{vogais}])"
    texto = re.sub(padrao_fraca, rf"{divisor}\1", texto)

    # Regra 3: Adicionar o divisor antes de cada dígrafo
    for dígrafo in dígrafos:
        padrao_dígrafo = rf"({dígrafo})"
        texto = re.sub(padrao_dígrafo, rf"{divisor}\1", texto)

    # Regra 4: Adicionar o divisor entre cada hiato (vogal fraca seguida de vogal forte)
    padrao_vogal_fraca_forte = rf"([{vogais}])([{vogais_fortes}])"
    texto = re.sub(padrao_vogal_fraca_forte, rf"\1{divisor}\2", texto)

    # Regra 5: Adicionar o divisor entre letras repetidas
    padrao_letras_repetidas = rf"(\w)(\1)"
    texto = re.sub(padrao_letras_repetidas, r"\1" + divisor + r"\2", texto)

    # Exceção 1: Prevenir a adição do divisor antes de 'x' se seguido por uma consoante fraca
    padrao_x_fraco = rf"(?<!{re.escape(divisor)})(x)(?=[{consoantes_fracas}])"
    texto = re.sub(padrao_x_fraco, r"\1", texto)

    # Exceção 2: 'gu' e 'qu' esperam uma vogal após eles
    padrao_gu_qu_vogal = rf"(?<=(gu|qu)){re.escape(divisor)}([{vogais_fortes}])"
    texto = re.sub(padrao_gu_qu_vogal, r"\2", texto)

    # Exceção 3: Encontro de 3 vogais fracas seguidas
    padrao_tres_fracas = rf"([{re.escape(vogais_fracas)}])([{re.escape(vogais_fracas)}])([{re.escape(vogais_fracas)}])"
    texto = re.sub(padrao_tres_fracas, r"\1" + divisor + r"\2" + r"\3", texto)


    return texto

def limpar_texto(texto, divisor="-"):
    # Regra de Limpeza 1: Se houver um divisor antes e depois de uma consoante forte, remover o primeiro
    padrao_divisor_duplo = rf"{re.escape(divisor)}([{consoantes_fortes}]){re.escape(divisor)}"
    texto = re.sub(padrao_divisor_duplo, r"\1" + divisor, texto)

    # Regra de Limpeza 2: Remover múltiplos divisores consecutivos
    texto = re.sub(rf"{re.escape(divisor)}+", divisor, texto)

    # Regra de Limpeza 3: Remover divisores no início do texto
    texto = texto.lstrip(divisor)

    # Regra de Limpeza 4: Remover espaços incorretos ao redor dos divisores
    texto = texto.replace(f" {divisor}", " ")
    texto = texto.replace(f"{divisor} ", " ")

    return texto

def separar_palavra(texto, divisor="-"):
    texto_modificado = aplicar_regras(texto, divisor)
    return limpar_texto(texto_modificado, divisor)

if __name__ == "__main__":
    import tonic
    while True:

        # Exemplo de uso
        texto = input("Digite uma palavra: ")
        print()
        texto_separado = separar_palavra(texto)
        print(f"Texto original: {texto}")
        print(f"Texto separado: {texto_separado}")
        print(f"Sílaba tônica: '{texto}': {tonic.tonic(texto_separado.split('-'))}")
        print()