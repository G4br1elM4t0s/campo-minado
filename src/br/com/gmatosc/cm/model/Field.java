package br.com.gmatosc.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.gmatosc.cm.exception.ExplosaoException;

public class Field {
	//Attributes
	private final int linha;
	private final int coluna;
	
	private boolean aberto; //padrão que assume false;
	private boolean minado;
	private boolean marcado;
	
	private List<Field> vizinhos = new ArrayList<>();
	
	
	
	Field(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adionarVizinho(Field vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		
		//diferença da linha e da coluna;
		int deltaLinha = Math.abs(linha-vizinho.linha);
		int deltaColuna = Math.abs(coluna- vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		//cenarios em que tenho vizinhos;
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);	
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
		
	}
	
	//proteje para não abrir!
	void alternarMarcacao() {
		if(!aberto){
			marcado = !marcado;
		}
	}
	
	
	boolean abrir() {
		
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v-> v.abrir()); //consumer; metódo recursiva ou seja para cada vez que esse metdo for chamado ele se auto chamará
			}
			
			return true;
		}else {
			return false;
		}
		
	}
	
	
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v-> v.minado); //predicate
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isMarcado() {
		
		return marcado;
	}
	
	
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhanca()>0) {
			return Long.toString(minasNaVizinhanca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}
	
	
	
}
