import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Syllabificator {
	StressVowel STV = new StressVowel();
	private String[] palavra;
	private String saida;
	private int comprimento;
	private int nsilaba;
	private int[] silIndices;
	private String delimitador = "-";
	private int indResto;
	private String palavra0;
	private int indexVogalTonica;
	private ArrayList<String> composto = new ArrayList<String>();
	private ArrayList<Integer> tamCompostos = new ArrayList<Integer>();

	private void calling(String var1) {
		this.palavra0 = var1;
		this.palavra = null;
		this.saida = null;
		this.comprimento = -1;
		this.nsilaba = -1;
		this.silIndices = null;
		this.delimitador = "-";
		this.indResto = -1;
		String[] var2 = this.palavra0.split("-");
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String var5 = var2[var4];
			this.composto.add(var5);
			this.tamCompostos.add(var5.length());
		}

	}

	private void updateVars() {
		this.indexVogalTonica = this.STV.indexVogalTonica;
	}

	private int getIndex() {
		return this.indexVogalTonica;
	}

	public  String syllabs(String var1) {
		var1 = var1.replace("ü", "u");
		this.calling(var1);
		this.STV.findStress(var1);
		this.updateVars();
		int var2 = this.getIndex() - 1;
		this.saida = "";
		this.palavra = this.convertString2ArrayString(var1);
		this.indResto = 3;
		this.comprimento = this.palavra.length;
		this.nsilaba = 1;
		this.silIndices = new int[this.comprimento];

		int var4;
		for (var4 = 0; var4 < this.comprimento; ++var4) {
			this.silIndices[var4] = -1;
		}

		try {
			var4 = 3;

			while (var4 < this.comprimento) {
				String var5 = "#";
				String var6 = "#";
				String var7 = "#";
				String var8 = "#";
				String var9 = "#";
				String var10 = "#";
				var5 = this.palavra[var4 - 2];
				var6 = this.palavra[var4 - 1];
				var7 = this.palavra[var4];
				if (var4 < this.comprimento - 3) {
					var8 = this.palavra[var4 + 1];
					var9 = this.palavra[var4 + 2];
					var10 = this.palavra[var4 + 3];
				} else if (var4 < this.comprimento - 2) {
					var8 = this.palavra[var4 + 1];
					var9 = this.palavra[var4 + 2];
				} else if (var4 < this.comprimento - 1) {
					var8 = this.palavra[var4 + 1];
				}

				label964 : {
					this.delimitador = "-";
					if (!var6.equals("u") && !var6.equals("ü")
							&& (var7.equals("a") || var7.equals("á") || var7.equals("ã") || var7.equals("â")
									|| var7.equals("à") || var7.equals("e") || var7.equals("é") || var7.equals("ê")
									|| var7.equals("o") || var7.equals("ó") || var7.equals("õ") || var7.equals("ô"))
							&& (var8.equals("i") || var8.equals("í") || var8.equals("u") || var8.equals("ú")
									|| var8.equals("ü"))
							&& var2 > -1) {
						if ((!var1.substring(var2, var2 + 1).equals(var8) || var4 - 2 != var2) && !var8.equals("í")
								&& !var8.equals("ú")) {
							if (this.saida.equals("")) {
								this.delimitador = "";
							}

							if ((var9.equals("s") || var9.equals("r")) && this.isConsonant(var4 + 3)) {
								this.saida = this.saida + this.delimitador + this.caso05(var4);
								var4 += 2;
							} else {
								if (var9.equals("s") && var10.equals("#")) {
									this.saida = this.saida + this.delimitador + this.caso06(var4);
									break;
								}

								if ((var9.equals("m") || var9.equals("n")) && !var9.equals("h")
										&& this.isVowel(var4 + 2)) {
									this.saida = this.saida + this.delimitador + this.caso01(var4);
									++var4;
								} else {
									this.saida = this.saida + this.delimitador + this.caso04(var4);
									++var4;
								}
							}
						} else {
							if (this.saida.equals("")) {
								this.delimitador = "";
							}

							this.saida = this.saida + this.delimitador + this.caso03(var4);
						}
					} else {
						label995 : {
							if ((!var7.equals("u") && !var7.equals("ü")
									|| !var8.equals("a") && !var8.equals("ã") && !var8.equals("e") && !var8.equals("i")
											&& !var8.equals("o") && !var8.equals("õ")
									|| !var9.equals("e") && !var9.equals("i") && !var9.equals("o") && !var9.equals("u"))
									&& (var7.equals("i") || var7.equals("í") || var7.equals("u") || var7.equals("ú")
											|| var7.equals("ü"))
									&& (var8.equals("a") || var8.equals("á") || var8.equals("ã") || var8.equals("â")
											|| var8.equals("à") || var8.equals("e") || var8.equals("é")
											|| var8.equals("ê") || var8.equals("o") || var8.equals("ó")
											|| var8.equals("õ") || var8.equals("ô"))) {
								if (!var9.equals("#") && (!var9.equals("s") || !var10.equals("#"))) {
									if (this.saida.equals("")) {
										this.delimitador = "";
									}

									this.saida = this.saida + this.delimitador + this.caso03(var4);
									break label995;
								}

								try {
									if (!this.palavra[var2 + 3].equals(var7) && !this.palavra[var2 + 3].equals(var8)) {
										if (this.saida.equals("")) {
											this.delimitador = "";
										}

										this.saida = this.saida + this.delimitador + this.caso06(var4);
										var4 += 2;
										break label995;
									}

									if (this.saida.equals("")) {
										this.delimitador = "";
									}

									this.saida = this.saida + this.delimitador + this.caso01(var4);
									break label995;
								} catch (ArrayIndexOutOfBoundsException var15) {
									;
								}
							}

							if (this.isVowel(var4)) {
								if (var4 == this.indResto) {
									if (!var7.equals("ã") && !var7.equals("õ")
											&& (this.isVowel(var4 + 1) && !this.isSemivowel(var4 + 1)
													|| this.isSemivowel(var4 + 1) && var9.equals("n"))) {
										if (this.saida.equals("")) {
											this.delimitador = "";
										}

										this.saida = this.saida + this.delimitador + this.caso01(var4);
									} else {
										if (this.isConsonant(var4 + 1) && this.isConsonant(var4 + 2)
												&& (this.isOclusive(var4 + 3) || this.isLiquids(var4 + 3))) {
											if (this.saida.equals("")) {
												this.delimitador = "";
											}

											if (this.isOclusive(var4 + 3)) {
												this.saida = this.saida + this.delimitador + this.caso05(var4);
												var4 += 2;
												break label995;
											}

											if (this.isLiquids(var4 + 3)) {
												this.saida = this.saida + this.delimitador + this.caso02(var4);
												++var4;
												break label995;
											}
										}

										if (!this.isSemivowel(var4 + 1) && !var8.equals("s") && !var8.equals("r")
												&& !var8.equals("l") && !this.isNasal(var4 + 1) && !var8.equals("x")
												|| !this.isConsonant(var4 + 2)
												|| var9.equals("s") && var9.equals("h") && var9.equals("r")) {
											if (this.isConsonant(var4 + 1) && this.isConsonant(var4 + 2)
													&& this.isVowel(var4 + 3)) {
												if (this.saida.equals("")) {
													this.delimitador = "";
												}

												if (this.consoanteMuda(var4)) {
													this.saida = this.saida + this.delimitador + this.caso02(var4);
												} else {
													this.saida = this.saida + this.delimitador + this.caso01(var4);
												}
											} else if (!this.isConsonant(var4 + 1)
													|| !this.isVowel(var4 + 2) && !this.isLiquids(var4 + 2)) {
												if (this.isConsonant(var4 + 1) && var9.equals("s")
														&& var10.equals("c")) {
													if (this.saida.equals("")) {
														this.delimitador = "";
													}

													if (!this.palavra[var4 + 4].equals("e")
															&& !this.palavra[var4 + 4].equals("é")
															&& !this.palavra[var4 + 4].equals("ê")
															&& !this.palavra[var4 + 4].equals("i")
															&& !this.palavra[var4 + 4].equals("í")) {
														this.saida = this.saida + this.delimitador + this.caso05(var4);
													} else {
														this.saida = this.saida + this.delimitador + this.caso02(var4);
													}

													var4 += 2;
												} else if (var7.equals("a") && this.isSemivowel(var4 + 1)
														&& var9.equals("a")) {
													if (this.saida.equals("")) {
														this.delimitador = "";
													}

													this.saida = this.saida + this.delimitador + this.caso02(var4);
													++var4;
												} else {
													if (!this.isSemivowel(var4) || !this.isSemivowel(var4 + 1)
															|| !this.isVowel(var4 + 2)) {
														break label964;
													}

													if (this.saida.equals("")) {
														this.delimitador = "";
													}

													this.saida = this.saida + this.delimitador + this.caso02(var4);
													++var4;
												}
											} else {
												if (this.saida.equals("")) {
													this.delimitador = "";
												}

												this.saida = this.saida + this.delimitador + this.caso01(var4);
											}
										} else {
											if (this.saida.equals("")) {
												this.delimitador = "";
											}

											if (var9.equals("s") && var10.equals("#")) {
												this.saida = this.saida + this.delimitador + this.caso06(var4);
												break;
											}

											if (this.consoanteMuda(var4)
													|| this.isVowel(var4 + 1) && var9.equals("s")) {
												this.saida = this.saida + this.delimitador + this.caso05(var4);
												var4 += 2;
											} else if (var8.equals("x") && var9.equals("c")) {
												this.saida = this.saida + this.delimitador + this.caso01(var4);
												var4 += 2;
											} else {
												this.saida = this.saida + this.delimitador + this.caso02(var4);
												++var4;
											}
										}
									}
								} else if (this.isConsonant(var4 - 1) && this.isConsonant(var4 + 1)
										&& this.isVowel(var4 + 2)) {
									if (this.saida.equals("")) {
										this.delimitador = "";
									}

									this.saida = this.saida + this.delimitador + this.caso03(var4);
								} else if (!this.isConsonant(var4 - 1) && !this.isSemivowel(var4 - 1)
										|| !this.isSemivowel(var4 + 1) || !this.isConsonant(var4 + 2)
										|| (var9.equals("m") || var9.equals("n") || var9.equals("r")
												|| var9.equals("s"))
												&& (var10.equals("#") || this.isConsonant(var4 + 3))) {
									if (this.isConsonant(var4 - 1) && this.isSemivowel(var4 + 1) && var9.equals("s")
											&& this.isOclusive(var4 + 3)) {
										if (this.saida.equals("")) {
											this.delimitador = "";
										}

										this.saida = this.saida + this.delimitador + this.caso05(var4);
										var4 += 2;
									} else if (!this.isConsonant(var4 - 1) || !this.isSemivowel(var4 + 1)
											|| !this.isVowel(var4 + 2) && !var9.equals("#")) {
										if (this.isSemivowel(var4 - 1) && this.isConsonant(var4 + 1)
												&& this.isVowel(var4 + 2)) {
											if (this.saida.equals("")) {
												this.delimitador = "";
											}

											this.saida = this.saida + this.delimitador + this.caso03(var4);
										} else if (this.isConsonant(var4 - 1) && this.isSemivowel(var4 + 1)
												&& var9.equals("r") && this.isConsonant(var4 + 3)) {
											if (this.saida.equals("")) {
												this.delimitador = "";
											}

											this.saida = this.saida + this.delimitador + this.caso03(var4);
										} else if ((this.isConsonant(var4 - 1) || (var6.equals("u") || var6.equals("ü"))
												&& (var5.equals("q") || var5.equals("g")))
												&& (this.isLiquids(var4 + 1) || this.isNasal(var4 + 1)
														|| var8.equals("c") || var8.equals("x"))
												&& (this.isVowel(var4 + 3) || var10.equals("h") || var10.equals("l")
														|| var10.equals("r"))) {
											if (this.saida.equals("")) {
												this.delimitador = "";
											}

											if ((var9.equals("h") || (var9.equals("r") || var9.equals("l"))
													&& !this.isNasal(var4 + 1)) && !var8.equals("l")
													&& !var8.equals("r")) {
												this.saida = this.saida + this.delimitador + this.caso01(var4);
											} else if (var9.equals("s") && !this.isVowel(var4 + 3)) {
												this.saida = this.saida + this.delimitador + this.caso05(var4);
												var4 += 2;
											} else {
												this.saida = this.saida + this.delimitador + this.caso04(var4);
												++var4;
											}
										} else if (!this.isConsonant(var4 - 1) || !this.isLiquids(var4 + 1)
												&& !this.isNasal(var4 + 1) && !var8.equals("i")) {
											if (this.isVowel(var4 + 1) && this.isVowel(var4 + 2)) {
												if (this.saida.equals("")) {
													this.delimitador = "";
												}

												this.saida = this.saida + this.delimitador + this.caso01(var4);
											} else if ((this.isOclusive(var4 + 1) || var8.equals("f")
													|| var8.equals("v") || var8.equals("g"))
													&& (this.isLiquids(var4 + 2) || this.isOclusive(var4 + 2))
													&& this.isVowel(var4 + 3)) {
												if (this.saida.equals("")) {
													this.delimitador = "";
												}

												if (this.consoanteMuda(var4)) {
													this.saida = this.saida + this.delimitador + this.caso02(var4);
												} else {
													this.saida = this.saida + this.delimitador + this.caso01(var4);
												}
											} else if (!var7.equals("i") || !this.isConsonant(var4 - 1)
													|| !var8.equals("a") && !var8.equals("o")) {
												if ((var7.equals("ã") || var7.equals("õ")) && this.isConsonant(var4 - 1)
														&& (var8.equals("o") || var8.equals("e"))
														&& (var9.equals("#") || var9.equals("s"))) {
													if (this.saida.equals("")) {
														this.delimitador = "";
													}

													this.saida = this.saida + this.delimitador + this.caso06(var4);
													break;
												}

												if ((this.isConsonant(var4 - 1) || var6.equals("u") && var5.equals("q"))
														&& this.isConsonant(var4 + 1) && this.isConsonant(var4 + 2)) {
													if (this.saida.equals("")) {
														this.delimitador = "";
													}

													if (var8 == var9) {
														this.saida = this.saida + this.delimitador + this.caso01(var4);
														var4 += 2;
													} else if (var8.equals("s") && !var9.equals("s")) {
														this.saida = this.saida + this.delimitador + this.caso02(var4);
														++var4;
													} else if (var9.equals("s") && this.isOclusive(var4 + 3)) {
														this.saida = this.saida + this.delimitador + this.caso05(var4);
														var4 += 2;
													} else {
														this.saida = this.saida + this.delimitador + this.caso02(var4);
														++var4;
													}
												} else if (this.isVowel(var4 + 1) && this.isConsonant(var4 + 2)) {
													if (this.saida.equals("")) {
														this.delimitador = "";
													}

													if (!var8.equals("a") && !var8.equals("e") && !var8.equals("o")
															&& !var8.equals("í") && !var8.equals("ú")
															&& !var8.equals("ó") && !var8.equals("á")
															&& !var8.equals("é")) {
														if (this.isVowel(var4 + 3)) {
															this.saida = this.saida + this.delimitador
																	+ this.caso02(var4);
															++var4;
														} else {
															this.saida = this.saida + this.delimitador
																	+ this.caso01(var4);
														}
													} else if (var7.equals("u") && var6.equals("g")) {
														this.saida = this.saida + this.delimitador + this.caso02(var4);
														++var4;
													} else {
														this.saida = this.saida + this.delimitador + this.caso01(var4);
													}
												} else if ((var8.equals("n") || var8.equals("r") || var8.equals("l"))
														&& !this.isVowel(var4 + 2) && !var9.equals("h")) {
													if (this.saida.equals("")) {
														this.delimitador = "";
													}

													if (var9.equals("s") && this.isVowel(var4 + 3)) {
														this.saida = this.saida + this.delimitador
																+ this.caso05(var4 - 1);
														var4 += 2;
													} else {
														this.saida = this.saida + this.delimitador + this.caso02(var4);
														++var4;
													}
												} else {
													if (var7.equals("e") && this.isConsonant(var4 - 1)
															&& var8.equals("o") && var9.equals("#")) {
														if (this.saida.equals("")) {
															this.delimitador = "";
														}

														this.saida = this.saida + this.delimitador + this.caso06(var4);
														break;
													}

													boolean var13 = false;

													for (int var14 = var4 + 1; var14 < this.comprimento; ++var14) {
														if (this.isVowel(var14)) {
															if (!var7.equals("u")
																	|| !var6.equals("g") && !var6.equals("q")) {
																if (this.saida.equals("")) {
																	this.delimitador = "";
																}

																this.saida = this.saida + this.delimitador
																		+ this.caso01(var4);
																var13 = true;
																break;
															}

															if (this.saida.equals("")) {
																this.delimitador = "";
															}

															this.saida = this.saida + this.delimitador
																	+ this.caso06(var4);
															var4 = this.comprimento;
															var13 = true;
														}
													}

													if (!var13) {
														break label964;
													}
												}
											} else {
												if (this.saida.equals("")) {
													this.delimitador = "";
												}

												if (var9.equals("#")) {
													if (this.palavra[var2 + 1].equals(var7)) {
														this.saida = this.saida + this.delimitador + this.caso01(var4);
													} else {
														this.saida = this.saida + this.delimitador + this.caso04(var4);
														++var4;
													}
												} else {
													this.saida = this.saida + this.delimitador + this.caso01(var4);
												}
											}
										} else {
											if (this.saida.equals("")) {
												this.delimitador = "";
											}

											if (var9.equals("#")) {
												this.saida = this.saida + this.delimitador + this.caso06(var4);
												break;
											}

											if (this.consoanteMuda(var4) && this.isNasal(var4 + 1)) {
												this.saida = this.saida + this.delimitador + this.caso05(var4);
												var4 += 2;
											} else if (!var9.equals("s") && !var9.equals("r")
													&& !this.isNasal(var4 + 2)) {
												this.saida = this.saida + this.delimitador + this.caso02(var4);
												++var4;
											} else {
												this.saida = this.saida + this.delimitador + this.caso05(var4);
												var4 += 2;
											}
										}
									} else {
										if (this.saida.equals("")) {
											this.delimitador = "";
										}

										this.saida = this.saida + this.delimitador + this.caso04(var4);
										++var4;
									}
								} else {
									if (this.saida.equals("")) {
										this.delimitador = "";
									}

									this.saida = this.saida + this.delimitador + this.caso04(var4);
									++var4;
								}
							}
						}
					}

					++var4;
					continue;
				}

				if (this.saida.equals("")) {
					this.delimitador = "";
				}

				this.saida = this.saida + this.delimitador + this.excecao(var4);
				break;
			}
		} catch (Exception var16) {
			var16.printStackTrace(System.err);
			System.err.printf("[SYL] Untreatable error at word %s, skipping word, dumping syllables\n", this.palavra0);
			return this.saida.replace("#", "");
		}

		return this.saida.replace("#", "");
	}

	private String caso01(int var1) {
		String var2 = "";

		int var3;
		for (var3 = 3; var3 <= var1; ++var3) {
			if (this.silIndices[var3] == -1) {
				var2 = var2 + this.palavra[var3];
			}
		}

		for (var3 = 3; var3 <= var1; ++var3) {
			if (this.silIndices[var3] == -1) {
				this.silIndices[var3] = this.nsilaba;
			}
		}

		++this.nsilaba;
		this.indResto = var1 + 1;
		return var2;
	}

	private String caso02(int var1) {
		String var2 = "";

		int var3;
		for (var3 = 3; var3 <= var1 + 1; ++var3) {
			if (this.silIndices[var3] == -1) {
				var2 = var2 + this.palavra[var3];
			}
		}

		for (var3 = 3; var3 <= var1 + 1; ++var3) {
			if (this.silIndices[var3] == -1) {
				this.silIndices[var3] = this.nsilaba;
			}
		}

		++this.nsilaba;
		this.indResto = var1 + 2;
		return var2;
	}

	private String caso03(int var1) {
		String var2 = "";

		int var3;
		for (var3 = 3; var3 <= var1; ++var3) {
			if (this.silIndices[var3] == -1) {
				var2 = var2 + this.palavra[var3];
			}
		}

		for (var3 = 3; var3 <= var1; ++var3) {
			if (this.silIndices[var3] == -1) {
				this.silIndices[var3] = this.nsilaba;
			}
		}

		++this.nsilaba;
		this.indResto = var1 + 1;
		return var2;
	}

	private String caso04(int var1) {
		String var2 = "";

		int var3;
		for (var3 = 3; var3 <= var1 + 1; ++var3) {
			if (this.silIndices[var3] == -1) {
				var2 = var2 + "" + this.palavra[var3];
			}
		}

		for (var3 = 3; var3 <= var1 + 1; ++var3) {
			if (this.silIndices[var3] == -1) {
				this.silIndices[var3] = this.nsilaba;
			}
		}

		++this.nsilaba;
		this.indResto = var1 + 2;
		return var2;
	}

	private String caso05(int var1) {
		String var2 = "";

		int var3;
		for (var3 = 3; var3 <= var1 + 2; ++var3) {
			if (this.silIndices[var3] == -1) {
				var2 = var2 + "" + this.palavra[var3];
			}
		}

		for (var3 = 3; var3 <= var1 + 2; ++var3) {
			if (this.silIndices[var3] == -1) {
				this.silIndices[var3] = this.nsilaba;
			}
		}

		++this.nsilaba;
		this.indResto = var1 + 3;
		return var2;
	}

	private String caso06(int var1) {
		String var2 = "";

		int var3;
		for (var3 = 0; var3 < this.comprimento; ++var3) {
			if (this.silIndices[var3] == -1) {
				var2 = var2 + "" + this.palavra[var3];
			}
		}

		for (var3 = 0; var3 < this.comprimento; ++var3) {
			if (this.silIndices[var3] == -1) {
				this.silIndices[var3] = this.nsilaba;
			}
		}

		++this.nsilaba;
		return var2;
	}

	private String excecao(int var1) {
		String var2 = "";

		int var4;
		for (var4 = 0; var4 < this.comprimento; ++var4) {
			String var3 = this.palavra[var4];
			if (this.silIndices[var4] == -1) {
				var2 = var2 + "" + var3;
			}
		}

		for (var4 = 0; var4 < this.comprimento; ++var4) {
			if (this.silIndices[var4] == -1) {
				this.silIndices[var4] = this.nsilaba;
			}
		}

		++this.nsilaba;
		return var2;
	}

	private String[] convertString2ArrayString(String var1) {
		LinkedList<String> var3 = new LinkedList<String>();
		var1 = "###" + var1 + "#########";

		int var7;
		for (var7 = 3; var7 < var1.length() - 2; ++var7) {
			char var4 = var1.charAt(var7);
			char var5 = '#';
			char var6 = '#';
			if (var7 < var1.length() - 1) {
				var5 = var1.charAt(var7 + 1);
			}

			if (var7 < var1.length() - 2) {
				var6 = var1.charAt(var7 + 2);
			}

			if (var4 != 'p' && var4 != 't' && var4 != 'b' && var4 != 'd') {
				if (var4 != 'f' && var4 != 'v' && var4 != 'z' && var4 != 'j' && var4 != 'x' && var4 != 231) {
					if (var4 == 'm') {
						if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 'l') {
						if (var5 == 'h') {
							if (var7 < var1.length() - 2) {
								var3.addLast(var1.substring(var7, var7 + 2).replace("#", ""));
							}

							++var7;
						} else if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 'r') {
						if (var5 == 'r') {
							if (var7 < var1.length() - 2) {
								var3.addLast(var1.substring(var7, var7 + 2).replace("#", ""));
							}

							++var7;
						} else if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 'c') {
						if (var5 != 'e' && var5 != 'i' && var5 != 'h') {
							if (var7 < var1.length() - 1) {
								var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
							}
						} else if (var5 == 'h') {
							if (var7 < var1.length() - 2) {
								var3.addLast(var1.substring(var7, var7 + 2).replace("#", ""));
							}

							++var7;
						} else if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 's') {
						if (var5 == 's') {
							if (var7 < var1.length() - 2) {
								var3.addLast(var1.substring(var7, var7 + 2).replace("#", ""));
							}

							++var7;
						} else if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 'n') {
						if (var5 == 'h') {
							if (var7 < var1.length() - 2) {
								var3.addLast(var1.substring(var7, var7 + 2).replace("#", ""));
							}

							++var7;
						} else if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 'q') {
						if (var5 != 'u' && var5 != 252) {
							if (var7 < var1.length() - 1) {
								var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
							}
						} else {
							if (var7 < var1.length() - 2) {
								var3.addLast(var1.substring(var7, var7 + 2).replace("#", ""));
							}

							++var7;
							if (var6 != 'e' && var6 == 'i') {
								;
							}
						}
					} else if (var4 == 'g') {
						if (var5 != 'u' && var5 != 252) {
							if (var5 != 'a' && var5 != 'o' && var5 != 'u') {
								if (var5 != 'e' && var5 != 'i') {
									if (var7 < var1.length() - 1) {
										var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
									}
								} else if (var7 < var1.length() - 1) {
									var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
								}
							} else if (var7 < var1.length() - 1) {
								var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
							}
						} else if (var6 != 'e' && var6 != 'i' && var6 != 'a' && var6 != 233) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						} else {
							var3.addLast(var1.substring(var7, var7 + 2).replace("#", ""));
							++var7;
						}
					} else if (var4 == 'h') {
						if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 'k') {
						if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == 'w') {
						if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (this.isVowel(var4 + "")) {
						if (var7 < var1.length() - 1) {
							var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
						}
					} else if (var4 == '#') {
						break;
					}
				} else if (var7 < var1.length() - 1) {
					var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
				}
			} else if (var7 < var1.length() - 1) {
				var3.addLast(var1.substring(var7, var7 + 1).replace("#", ""));
			}
		}

		var7 = 3;
		String[] var2 = new String[var3.size() + 3];
		var2[0] = "#";
		var2[1] = "#";
		var2[2] = "#";

		for (Iterator<String> var8 = var3.iterator(); var8.hasNext(); ++var7) {
			String var9 = (String) var8.next();
			var2[var7] = var9;
		}

		return var2;
	}

	private boolean isVowel(String var1) {
		String[] var2 = new String[]{"a", "e", "i", "u", "o", "á", "é", "í", "ó", "ú", "ã", "õ", "â", "ê", "ô", "à",
				"ü", "y"};
		String[] var3 = var2;
		int var4 = var2.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			String var6 = var3[var5];
			if (var1.equals(var6)) {
				return true;
			}
		}

		return false;
	}

	private boolean isVowel(int var1) {
		if (this.comprimento - var1 < 1) {
			return false;
		} else {
			String[] var2 = new String[]{"a", "e", "i", "u", "o", "á", "é", "í", "ó", "ú", "ã", "õ", "â", "ê", "ô", "à",
					"ü", "y"};
			String var3 = this.palavra[var1];
			String[] var4 = var2;
			int var5 = var2.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				String var7 = var4[var6];
				if (var3.equals(var7)) {
					return true;
				}
			}

			return false;
		}
	}

	private boolean isSemivowel(int var1) {
		if (this.comprimento - var1 < 1) {
			return false;
		} else {
			String[] var2 = new String[]{"i", "u"};
			String var3 = this.palavra[var1];
			String[] var4 = var2;
			int var5 = var2.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				String var7 = var4[var6];
				if (var3.equals(var7)) {
					return true;
				}
			}

			return false;
		}
	}

	private boolean consoanteMuda(int var1) {
		if (!this.isNasal(var1 + 1)) {
			return (this.palavra[var1 + 1].equals("p") || this.palavra[var1 + 1].equals("g")
					|| this.palavra[var1 + 1].equals("b") || this.palavra[var1 + 1].equals("f")
					|| this.palavra[var1 + 1].equals("c") || this.palavra[var1 + 1].equals("t")
					|| this.palavra[var1 + 1].equals("d"))
					&& (!this.palavra[var1 + 2].equals("l") && !this.palavra[var1 + 2].equals("r")
							|| this.palavra[var1 + 2].equals("t"))
					&& !this.isVowel(var1 + 2);
		} else {
			try {
				return (this.palavra[var1 + 2].equals("p") || this.palavra[var1 + 2].equals("g")
						|| this.palavra[var1 + 2].equals("b") || this.palavra[var1 + 2].equals("f")
						|| this.palavra[var1 + 2].equals("c") || this.palavra[var1 + 2].equals("t"))
						&& !this.isVowel(var1 + 3)
						&& (!this.palavra[var1 + 3].equals("l") && !this.palavra[var1 + 3].equals("r")
								|| this.palavra[var1 + 3].equals("t"));
			} catch (ArrayIndexOutOfBoundsException var3) {
				return false;
			}
		}
	}

	private boolean isConsonant(int var1) {
		if (this.comprimento - var1 < 1) {
			return false;
		} else {
			String[] var2 = new String[]{"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t",
					"v", "x", "z", "w", "gu", "qu"};
			String var3 = "#";
			var3 = this.palavra[var1];
			String[] var4 = var2;
			int var5 = var2.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				String var7 = var4[var6];
				if (var3.equals(var7)) {
					return true;
				}
			}

			if (!var3.equals("lh") && !var3.equals("nh")) {
				if (this.isOclusive(var1)) {
					return true;
				} else if (this.isFricative(var1)) {
					return true;
				} else if (this.isLiquids(var1)) {
					return true;
				} else if (this.isNasal(var1)) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}

	private boolean isOclusive(int var1) {
		if (this.comprimento - var1 < 1) {
			return false;
		} else {
			String[] var2 = new String[]{"p", "t", "b", "d"};
			String var3 = "#";
			String var4 = "#";
			var3 = this.palavra[var1];
			if (var1 != this.palavra.length - 1) {
				var4 = this.palavra[var1 + 1];
			}

			String[] var5 = var2;
			int var6 = var2.length;

			for (int var7 = 0; var7 < var6; ++var7) {
				String var8 = var5[var7];
				if (var3.equals(var8)) {
					return true;
				}
			}

			if (!var3.equals("c") || !var4.equals("a") && !var4.equals("o") && !var4.equals("u")) {
				if (!var3.equals("g") || !var4.equals("a") && !var4.equals("o") && !var4.equals("u")) {
					if (!var3.equals("gu") || !var4.equals("e") && !var4.equals("i")) {
						return var3.equals("qu") && (var4.equals("e") || var4.equals("i"));
					} else {
						return true;
					}
				} else {
					return true;
				}
			} else {
				return true;
			}
		}
	}

	private boolean isFricative(int var1) {
		if (this.comprimento - var1 < 1) {
			return false;
		} else {
			String[] var2 = new String[]{"f", "v", "s", "ç", "z", "j", "x"};
			String var3 = "#";
			String var4 = "#";
			var3 = this.palavra[var1];
			if (var1 != this.palavra.length - 1) {
				var4 = this.palavra[var1 + 1];
			}

			String[] var5 = var2;
			int var6 = var2.length;

			for (int var7 = 0; var7 < var6; ++var7) {
				String var8 = var5[var7];
				if (var3.equals(var8)) {
					return true;
				}
			}

			if (var3.equals("ch")) {
				return true;
			} else if (var3.equals("c") && (var4.equals("e") || var4.equals("i"))) {
				return true;
			} else if (var3.equals("ss")) {
				return true;
			} else {
				return var3.equals("g") && (var4.equals("e") || var4.equals("i"));
			}
		}
	}

	private boolean isLiquids(int var1) {
		if (this.comprimento - var1 < 1) {
			return false;
		} else {
			String var2 = this.palavra[var1];
			if (var2.equals("r")) {
				return true;
			} else if (var2.equals("rr")) {
				return true;
			} else {
				return var2.equals("l");
			}
		}
	}

	private boolean isNasal(int var1) {
		if (this.comprimento - var1 < 1) {
			return false;
		} else {
			String var2 = this.palavra[var1];
			String[] var3 = new String[]{"m", "n"};
			String[] var4 = var3;
			int var5 = var3.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				String var7 = var4[var6];
				if (var2.equals(var7)) {
					return true;
				}
			}

			return false;
		}
	}
}