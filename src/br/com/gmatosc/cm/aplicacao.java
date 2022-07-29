package br.com.gmatosc.cm;

import br.com.gmatosc.cm.model.Board;
import br.com.gmatosc.cm.view.ConsoleBoard;

public class aplicacao {
	public static void main(String[] args) {
		Board board = new Board(6,6,6);
		new ConsoleBoard(board);
	}
}
