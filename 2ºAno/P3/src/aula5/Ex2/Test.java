//João Gameiro Nº93097
//P3-UA-ECT

package aula5.Ex2;

public class Test {

	public static void main(String[] args) {
		
		//Cores
		Cor amarelo = Cor.Amarelo;
		Cor azul = Cor.Azul;
		Cor branco = Cor.Branco;
		Cor preto = Cor.Preto;
		Cor verde = Cor.Verde;
		Cor vermelho = Cor.Vermelho;
		
		//Tipos
		Tipo inem = Tipo.INEM;
		Tipo bombeiro = Tipo.Bombeiros;
		Tipo pj = Tipo.PJ;
		Tipo psp = Tipo.PSP;
		Tipo gnr = Tipo.GNR;
		
		//Automóvel
		System.out.println("AUTOMÓVEIS");
		Automovel a1 = new Automovel(2012, azul, 4, 55, 327, "ET-45-21", 2000.55, 75.75, false);
		Automovel a2 = new Automovel(2013, amarelo, 4, 60, 330, "15-AU-98", 2004.24, 90.50, true);
		System.out.println(a1);
		System.out.println(a2+"\n");
		
		//AutomoóvelPolicia
		System.out.println("AUTOMÓVEIS DA POLICIA");
		AutomovelPolicia ap1 = new AutomovelPolicia(2000, vermelho, 4, 53, 127, "AF-15-31", 2120.55, 71.74, false, pj, "0");
		AutomovelPolicia ap2 = new AutomovelPolicia(2010, verde, 4, 45, 227, "TR-32-11", 1000.55, 73.71, false, gnr, "1");
		System.out.println(ap1);
		System.out.println(ap2+"\n");
		
		//Bicicleta
		System.out.println("BICICLETAS");
		Bicicleta b1 = new Bicicleta(1999, preto, 2, 275);
		Bicicleta b2 = new Bicicleta(2005, branco, 2, 234);
		System.out.println(b1);
		System.out.println(b2+"\n");
		
		//BicicletaPolicia
		System.out.println("BICICLETAS DA POLICIA");
		BicicletaPolicia bp1 = new BicicletaPolicia(2012, azul, 2, 548, bombeiro, "3");
		BicicletaPolicia bp2 = new BicicletaPolicia(2000, verde, 2, 275, psp, "4");
		System.out.println(bp1);
		System.out.println(bp2+"\n");
		
		//Moto
		System.out.println("MOTOS");
		Moto m1 = new Moto(1995, azul, 2, 66, 213, "BB-56-28", 4561.25, 54.23, true);
		Moto m2 = new Moto(2020, amarelo, 2, 12, 432, "59-BS-84", 4134.15, 43.23, false);
		System.out.println(m1);
		System.out.println(m2+"\n");
		
		
		System.out.println("MOTOS DA POLICIA");
		MotoPolicia mp1 = new MotoPolicia(1999, verde, 2, 70, 123, "PB-16-38", 4132.25, 12.23, true, inem, "4");
		MotoPolicia mp2 = new MotoPolicia(2000, vermelho, 2, 24, 122, "13-AG-14", 1234.85, 45.3, false, pj, "5");
		System.out.println(mp1);
		System.out.println(mp2+"\n");

	}

}
