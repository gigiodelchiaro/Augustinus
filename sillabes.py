#• As vogais que representam hiatos:
#
#ci-ú-me, du-e-lo, lu-a, ru-í-do, vi-ú-va...
#
#• Os encontros consonantais que são “separáveis”, levando-se em consideração a regra da soletração. Eles aparecem no interior dos vocábulos:
#
#ab-sur-do, ad-vér-bio, af-to-so, cor-rup-ção, téc-ni-ca...
#
#O que não podemos separar?
#• Letras que formam ditongos e tritongos:

#
#á-gua, di-nhei-ro, i-dei-a, joi-a, jau-la...
#
#a-ve-ri-guou, ex-tor-quiu, quais, sa-guão, Uruguai...
#
#• Os encontros consonantais que são “inseparáveis”, tendo em vista a regra da soletração. Normalmente, esse tipo de encontrado consonantal é composto de uma consoante mais “l” ou “r”:
#
#cla-re-za, Bra-sil, em-pre-sa, pa-les-tra, psi-có-lo-go...



text = input("Texto a separar: ")
text = text.replace("rr","r-r")
text = text.replace("ss","s-s")
text = text.replace("sc","s-c")
text = text.replace("sç","s-ç")
text = text.replace("xc","x-c")
text = text.replace("ch","-ch")
text = text.replace("lh","-lh")
text = text.replace("nh","-nh")
text = text.replace("gu","-gu")
text = text.replace("qu","-qu")
text = text.replace(" -"," ")
text = text.replace("- "," ")
text = text.replace("--","-")
print(text)
input("")