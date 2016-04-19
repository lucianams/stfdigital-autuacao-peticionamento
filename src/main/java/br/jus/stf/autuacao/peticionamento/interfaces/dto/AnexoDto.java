package br.jus.stf.autuacao.peticionamento.interfaces.dto;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
public class AnexoDto {
	
	private Long tipo;
	
	private Long documento;
	
	public AnexoDto() {
		
	}
	
	public AnexoDto(Long tipo, Long documento) {
		this.tipo = tipo;
		this.documento = documento;
	}
	
	public Long getTipo() {
		return tipo;
	}
	
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	
	public Long getDocumento() {
		return documento;
	}
	
	public void setDocumento(Long documento) {
		this.documento = documento;
	}
	
}
