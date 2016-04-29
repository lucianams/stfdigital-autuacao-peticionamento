package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
public class AnexoDto {
	
	private TipoDocumentoId tipo;
	
	private DocumentoId documento;
	
	public AnexoDto() {
		
	}
	
	public AnexoDto(TipoDocumentoId tipo, DocumentoId documento) {
		this.tipo = tipo;
		this.documento = documento;
	}
	
	public TipoDocumentoId getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoDocumentoId tipo) {
		this.tipo = tipo;
	}
	
	public DocumentoId getDocumento() {
		return documento;
	}
	
	public void setDocumento(DocumentoId documento) {
		this.documento = documento;
	}
	
}
