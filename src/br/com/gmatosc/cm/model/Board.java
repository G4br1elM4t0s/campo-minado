package br.com.gmatosc.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.gmatosc.cm.exception.ExplosaoException;

public class Board {
	private int linhas;
	private int colunas;
	private int minas;
	
	
	//Cor
	
	
	private final List<Field> fields = new ArrayList<>();

	public Board(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			fields.parallelStream()
			.filter(f -> f.getLinha() == linha && f.getColuna() == coluna)
			.findFirst()
			.ifPresent(f->f.abrir());
		}catch(ExplosaoException e) {
			fields.forEach(f -> f.setAberto(true));
			throw e;
		}
	}
	
	public void marcar(int linha, int coluna) {
		fields.parallelStream()
		.filter(f -> f.getLinha() == linha && f.getColuna() == coluna)
		.findFirst()
		.ifPresent(f->f.alternarMarcacao());
	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				fields.add(new Field(linha,coluna));
			}
		}
		
	}
	
	private void associarVizinhos() {
		for(Field f1: fields) {
			for(Field f2:fields) {
				f1.adionarVizinho(f2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Field> minado = f -> f.isMinado();
		do {
			int aleatorio = (int) (Math.random()* fields.size());
			fields.get(aleatorio).minar();
			minasArmadas = fields.stream().filter(minado).count();
			
			
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return fields.stream().allMatch(f-> f.objetivoAlcancado());
	}
	
	public void reniciar() {
		fields.stream().forEach(f-> f.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("   .");
		for (int coluna = 0; coluna < colunas; coluna++) {
			sb.append("");
			sb.append("'"+coluna+ "'");
			sb.append("");	
		}
		
		sb.append("\n");
		
		
		int i = 0;
		
		for (int linha = 0; linha < linhas; linha++) {
			sb.append("'"+linha+"'");
			sb.append(" ");
			for (int coluna = 0; coluna < colunas; coluna++) {
				sb.append(" ");
				sb.append(fields.get(i));
				sb.append(" ");
				
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
}
