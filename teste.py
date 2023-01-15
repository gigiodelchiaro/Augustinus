
def syllable(palavra):
    import os
    lpalavra = palavra.split()
    count = 0
    global separated
    separated = []


    os.environ['CLASSPATH'] = "./fb_nlplib.jar"

    from jnius import autoclass

    class FalaBrasilNLP:
        def __init__(self):
            self.jClass = autoclass('ufpa.util.PyUse')()

        def fb_getsyl(self, palavra):

            return self.jClass.useSyll(palavra)

    if __name__ == '__main__':

        fb_nlp = FalaBrasilNLP()
        while count < len(lpalavra):
            palavra = lpalavra[count]
            count = count + 1
            separated.append(fb_nlp.fb_getsyl(palavra))


syllable("teste com lista")
junto = " ".join(separated)
print(junto)

