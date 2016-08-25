package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import java.util.List;

/**
 * @author Rafael Alencar
 *
 */
public class PeticaoDto {

	private Long protocolo;

	private String classe;

	private List<EnvolvidoDto> envolvidos;
	
	private String sigilo;
	
	private String numero;

	/**
	 * @param protocolo
	 * @param classe
	 * @param envolvidos
	 * @param sigilo
	 * @param numero
	 */
	public PeticaoDto(Long protocolo, String classe, List<EnvolvidoDto> envolvidos, String sigilo, String numero) {
		this.protocolo = protocolo;
		this.classe = classe;
		this.envolvidos = envolvidos;
		this.sigilo = sigilo;
		this.numero = numero;
	}

	public Long getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Long protocolo) {
		this.protocolo = protocolo;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public List<EnvolvidoDto> getEnvolvidos() {
		return envolvidos;
	}
	
	public void setEnvolvidos(List<EnvolvidoDto> envolvidos) {
		this.envolvidos = envolvidos;
	}

	public String getSigilo() {
		return sigilo;
	}

	public void setSigilo(String sigilo) {
		this.sigilo = sigilo;
	}

	public String getNumero() {
		return numero;
	}
	
}
