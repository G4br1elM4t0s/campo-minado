package br.com.gmatosc.cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.gmatosc.cm.exception.ExplosaoException;
import br.com.gmatosc.cm.exception.SairException;
import br.com.gmatosc.cm.model.Board;

public class ConsoleBoard {
	private Board board;
	private Scanner input = new Scanner(System.in);
	public ConsoleBoard(Board board) {
		this.board = board;
		
		executarjogo();
	}

	private void executarjogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				cicloDoJogo();
				
				
				System.out.println("Outro game? (S/n) ");
				String resposta = input.nextLine();
				
				if("n".equals(resposta)) {
					continuar = false;
				}else {
					board.reniciar();
				}
			}
			
		}catch(SairException e){
			System.out.println("Bye!");
		}finally {
			input.close();
		}
		
	}

	private void cicloDoJogo() {
		try {
			
			while(!board.objetivoAlcancado()) {
				System.out.println(board.toString());
				
				String digitado = capturarValorDigitado("Digite (x,y): ");
				//O ponto trim serve para tirar os espaços em branco
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
					.map(e -> Integer.parseInt(e.trim()))
					.iterator();
				
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar ");
				
				if("1".equals(digitado)) {
					board.abrir(xy.next(), xy.next());
				}else if("2".equals(digitado)) {
					board.marcar(xy.next(),xy.next());
				}
				
				
				
				
			}
			
			System.out.println("GG Você ganhou!");
			System.out.println(board.toString());
		}catch (ExplosaoException e) {
			System.out.println(board.toString());
			System.out.println("Você perdeu!");
		}
		
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = input.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
	
}
