import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StressVowel {
	String palavra;
	String vogalTonica;
	int indexVogalTonica;
	ArrayList<String> composto = new ArrayList<String>();
	ArrayList<String> tonicaComposto = new ArrayList<String>();
	ArrayList<Integer> numTonicaComposto = new ArrayList<Integer>();

	private void calling(String var1) {
		this.palavra = var1;
		this.vogalTonica = "";
		this.indexVogalTonica = 0;
		this.composto = new ArrayList<String>();
		this.tonicaComposto = new ArrayList<String>();
		this.numTonicaComposto = new ArrayList<Integer>();
		String[] var2 = this.palavra.split("-");
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String var5 = var2[var4];
			this.composto.add(var5);
		}
	}
	public int findStress(String var1) {
		this.calling(var1);
		String var2 = null;
		String var3 = null;
		String var4 = "";
		ArrayList<String> var5 = new ArrayList<String>();
		ArrayList<String> var6 = new ArrayList<String>();
		int var8 = 0;
		boolean var9 = false;
		var9 = false;
		int var10;
		if (!this.palavra.contains("<s>") && !this.palavra.contains("</s>")) {
			var3 = "";
			var2 = "";

			for (var10 = 0; var10 < this.composto.size(); ++var10) {
				var3 = var3 + "#" + (String) this.composto.get(var10) + "#";
				var2 = var2 + "#" + (String) this.composto.get(var10) + "######";
			}
		}

		if (var3.contains("##")) {
			for (var10 = 0; var10 < this.composto.size(); ++var10) {
				var5.add("#" + (String) this.composto.get(var10) + "######");
				var6.add("#" + (String) this.composto.get(var10) + "#");
			}
		} else {
			var5.add("#" + (String) this.composto.get(0) + "######");
			var6.add("#" + (String) this.composto.get(0) + "#");
		}

		try {
			for (var10 = 0; var10 < var6.size(); ++var10) {
				var2 = (String) var5.get(var10);
				var3 = (String) var6.get(var10);
				var9 = false;

				while (!var9) {
					Pattern var11 = Pattern.compile("([áéíóúâêôàè])");
					Matcher var12 = var11.matcher(var3);
					if (var12.find()) {
						var9 = true;
						var4 = var3.substring(var12.start(), var12.start() + 1);
						var8 = var12.start();
						break;
					}

					Pattern var13 = Pattern.compile("([^#][rlzxn][#])");
					Matcher var14 = var13.matcher(var3);
					int var74;
					if (var14.find()) {
						var74 = var14.start();
						if (!var3.substring(var74, var74 + 1).contains("a")
								&& !var3.substring(var74, var74 + 1).contains("e")
								&& !var3.substring(var74, var74 + 1).contains("i")
								&& !var3.substring(var74, var74 + 1).contains("o")
								&& !var3.substring(var74, var74 + 1).contains("u")) {
							var4 = "nf";
							break;
						}

						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						var8 = var14.start();
						break;
					}

					Pattern var15 = Pattern.compile("([ãõ])");
					Matcher var16 = var15.matcher(var3);
					if (var16.find()) {
						var74 = var16.start();
						var8 = var16.start();
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var17 = Pattern.compile("(([#][aeo][#])|([#][aeiou][^aeiou][#]))");
					Matcher var18 = var17.matcher(var3);
					if (var18.find()) {
						var74 = var18.start();
						var8 = var74 + 1;
						var9 = true;
						var4 = var3.substring(var74, var74 + 2);
						break;
					}

					Pattern var19 = Pattern.compile("(([#][^aeiou][aeiou][#])|([#][^aeiou][aeiou][^aeiou][#]))");
					Matcher var20 = var19.matcher(var3);
					if (var20.find()) {
						var74 = var20.start();
						var8 = var74 + 2;
						var9 = true;
						var4 = var3.substring(var74 + 2, var74 + 3);
						break;
					}

					Pattern var21 = Pattern
							.compile("([#]([^aeiou][^aeiou][aeiou][^aeiou]|[^aeiou][^aeiou][aeiou])[#])");
					Matcher var22 = var21.matcher(var3);
					if (var22.find()) {
						var74 = var22.start();
						var8 = var74 + 3;
						var9 = true;
						var4 = var3.substring(var74 + 3, var74 + 4);
						break;
					}

					Pattern var23 = Pattern.compile("([iou]m#)");
					Matcher var24 = var23.matcher(var3);
					if (var24.find()) {
						var74 = var24.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var25 = Pattern.compile("([iou]ns#)");
					Matcher var26 = var25.matcher(var3);
					if (var26.find()) {
						var74 = var26.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var27 = Pattern.compile("[qg][u][i]#");
					Matcher var28 = var27.matcher(var3);
					if (var28.find()) {
						var74 = var28.start();
						var8 = var74 + 2;
						var9 = true;
						var4 = var3.substring(var74 + 2, var74 + 3);
						break;
					}

					Pattern var29 = Pattern.compile("[qg][u][i]s#");
					Matcher var30 = var29.matcher(var3);
					if (var30.find()) {
						var74 = var30.start();
						var8 = var74 + 2;
						var9 = true;
						var4 = var3.substring(var74 + 2, var74 + 3);
						break;
					}

					Pattern var31 = Pattern.compile("[aeio][iu]#");
					Matcher var32 = var31.matcher(var3);
					if (var32.find()) {
						var74 = var32.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var33 = Pattern.compile("[^aeio#][iu]#");
					Matcher var34 = var33.matcher(var3);
					if (var34.find()) {
						var74 = var34.start();
						var8 = var74 + 1;
						var9 = true;
						var4 = var3.substring(var74, var74 + 2);
						break;
					}

					Pattern var35 = Pattern.compile("[aeiou][iu]s#");
					Matcher var36 = var35.matcher(var3);
					if (var36.find()) {
						var74 = var36.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var37 = Pattern.compile("[^aeiou][iu]s#");
					Matcher var38 = var37.matcher(var3);
					if (var38.find()) {
						var74 = var38.start();
						var8 = var74 + 1;
						var9 = true;
						var4 = var3.substring(var74, var74 + 2);
						break;
					}

					Pattern var39 = Pattern.compile("[p][o][r][q][u][e][#]");
					Matcher var40 = var39.matcher(var3);
					if (var40.find()) {
						var74 = var40.start();
						var8 = var74 + 5;
						var9 = true;
						var4 = var3.substring(var74 + 5, var74 + 6);
						break;
					}

					Pattern var41 = Pattern.compile("[aeiou][qg]ue#");
					Matcher var42 = var41.matcher(var3);
					if (var42.find()) {
						var74 = var42.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var43 = Pattern.compile("[aeiou][^aeiou][qg]ue#");
					Matcher var44 = var43.matcher(var3);
					if (var44.find()) {
						var74 = var44.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var45 = Pattern.compile("[aeiou][gq]ue[sm]#");
					Matcher var46 = var45.matcher(var3);
					if (var46.find()) {
						var74 = var46.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var47 = Pattern.compile("[aeiou][^aeiou][gq]ue[sm]#");
					Matcher var48 = var47.matcher(var3);
					if (var48.find()) {
						var74 = var48.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var49 = Pattern.compile("[aeiou][iu][aeiou][s#]");
					Matcher var50 = var49.matcher(var3);
					if (var50.find()) {
						var74 = var50.start();
						var8 = var74;
						var9 = true;
						var4 = var3.substring(var74, var74 + 1);
						break;
					}

					Pattern var51 = Pattern.compile("([^#][^#][iu][^aeiou][aeiou][#])");
					Matcher var52 = var51.matcher(var3);
					Pattern var53;
					Matcher var54;
					Pattern var55;
					Matcher var56;
					if (var52.find()) {
						var74 = var52.start();
						var53 = Pattern.compile("([qg][u])");
						var54 = var53.matcher(var3.substring(var74, var74 + 2));
						if (var54.find()) {
							var8 = var74 + 2;
							var9 = true;
							var4 = var3.substring(var74 + 2, var74 + 3);
							break;
						}

						var55 = Pattern.compile("([a-z][aeiou])");
						var56 = var55.matcher(var3.substring(var74, var74 + 2));
						if (var56.find()) {
							var8 = var74 + 1;
							var9 = true;
							var4 = var3.substring(var74 + 1, var74 + 2);
							break;
						}
					}

					var53 = Pattern.compile("([^#][^#][iu][^aeiou][aeiou][s][#])");
					var54 = var53.matcher(var3);
					Pattern var57;
					Matcher var58;
					if (var54.find()) {
						var74 = var54.start();
						var55 = Pattern.compile("([qg][u])");
						var56 = var55.matcher(var3.substring(var74, var74 + 2));
						if (var56.find()) {
							var8 = var74 + 2;
							var9 = true;
							var4 = var3.substring(var74 + 2, var74 + 3);
							break;
						}

						var57 = Pattern.compile("([a-z][aeiou])");
						var58 = var57.matcher(var3.substring(var74, var74 + 2));
						if (var58.find()) {
							var8 = var74 + 1;
							var9 = true;
							var4 = var3.substring(var74 + 1, var74 + 2);
							break;
						}
					}

					var55 = Pattern.compile("([aeiou][iu][n][^aeiou][aeo][#])");
					var56 = var55.matcher(var3);
					if (var56.find()) {
						var74 = var56.start();
						var8 = var74 + 1;
						var9 = true;
						var4 = var3.substring(var74 + 1, var74 + 2);
						break;
					}

					var57 = Pattern.compile("[^qg][aeiou][iu][^aeiou]");
					var58 = var57.matcher(var3);
					if (var58.find() && !var3.equals("#feijoada#") && !var3.equals("#caipirinha#")
							&& !var3.equals("#caipirinhas#") && !var3.equals("#saudade#")
							&& !var3.equals("#saudades#")) {
						var74 = var58.start();
						var8 = var74 + 1;
						var9 = true;
						var4 = var3.substring(var74 + 1, var74 + 2);
						break;
					}

					Pattern var59 = Pattern.compile("[q][u][e][m][#]");
					Matcher var60 = var59.matcher(var3);
					if (var60.find()) {
						var74 = var60.start();
						var8 = var74 + 2;
						var9 = true;
						var4 = var3.substring(var74 + 2, var74 + 3);
						break;
					}

					Pattern var61 = Pattern.compile("[q][u][e][#]");
					Matcher var62 = var61.matcher(var3);
					if (var62.find()) {
						var74 = var62.start();
						var8 = var74 + 2;
						var9 = true;
						var4 = var3.substring(var74 + 2, var74 + 3);
						break;
					}

					if (!var9) {
						Pattern var63 = Pattern.compile("([aeiou][aeiou])#");
						Matcher var64 = var63.matcher(var3);
						if (var64.find()) {
							var74 = var64.start();
							var8 = var74;
							var9 = true;
							var4 = var3.substring(var74, var74 + 1);
						} else {
							Pattern var65 = Pattern.compile("(([aeiou][^aeiou][aeiou])|([aeiou][aeiou][^aeiou]))#");
							Matcher var66 = var65.matcher(var3);
							if (var66.find()) {
								var74 = var66.start();
								var8 = var74;
								var9 = true;
								var4 = var3.substring(var74, var74 + 1);
							} else {
								Pattern var67 = Pattern.compile(
										"(([aeiou][^aeiou][^aeiou][aeiou])|([aeiou][^aeiou][aeiou][^aeiou]))#");
								Matcher var68 = var67.matcher(var3);
								if (var68.find()) {
									var74 = var68.start();
									var8 = var74;
									var9 = true;
									var4 = var3.substring(var74, var74 + 1);
								} else {
									Pattern var69 = Pattern.compile(
											"(([aeiou][^aeiou][^aeiou][aeiou][^aeiou])|([aeiou][^aeiou][^aeiou][^aeiou][aeiou])|([aeiou][^aeiou][aeiou][^aeiou][^aeiou]))#");
									Matcher var70 = var69.matcher(var3);
									if (var70.find()) {
										var74 = var70.start();
										var8 = var74;
										var9 = true;
										var4 = var3.substring(var74, var74 + 1);
									} else {
										Pattern var71 = Pattern.compile(
												"(([aeiou][^aeiou][^aeiou][^aeiou][aeiou][^aeiou])|([aeiou][^aeiou][^aeiou][^aeiou][^aeiou][aeiou]))[s#]");
										Matcher var72 = var71.matcher(var3);
										if (var72.find()) {
											var74 = var72.start();
											var8 = var74;
											var9 = true;
											var4 = var3.substring(var74, var74 + 1);
										} else {
											System.err.printf("[VOW] %s stress vowel not found!\n", var3);
											var4 = "nf";
										}
									}
								}
							}
						}
						break;
					}
				}

				if (var10 != var6.size() - 1) {
					this.tonicaComposto.add(var4);
					this.numTonicaComposto.add(var8);
					this.indexVogalTonica = this.indexVogalTonica + ((String) this.composto.get(var10)).length() + 1;
					var4 = "";
					var8 = 0;
					var9 = false;
				} else {
					this.tonicaComposto.add(var4);
					this.numTonicaComposto.add(var8);
					this.indexVogalTonica += var8;
					this.vogalTonica = var4;
				}
			}
		} catch (Exception var73) {
			var73.printStackTrace(System.err);
			System.err.printf("[VOW] Untreatable error at word %s, skipping word, dumping index\n", this.palavra);
			return this.indexVogalTonica;
		}
		if (var1.length() == 1) {
			this.indexVogalTonica = 1;
		}
		return this.indexVogalTonica;
	}
}