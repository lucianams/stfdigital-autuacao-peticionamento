package br.jus.stf.autuacao.peticionamento.application.commands;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
public class CadastrarAnexoCommand {
	
	private Long tipoDocumentoId;
	
	private String documentoId;
	
	public CadastrarAnexoCommand() {
		// Construtor default
	}
	
	/**
	 * @param tipoDocumentoId
	 * @param documentoId
	 */
	public CadastrarAnexoCommand(Long tipoDocumentoId, String documentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
		this.documentoId = documentoId;
	}
	
	public Long getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	
	public void setTipo(Long tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	
	public String getDocumentoId() {
		return documentoId;
	}
	
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}
	
}
