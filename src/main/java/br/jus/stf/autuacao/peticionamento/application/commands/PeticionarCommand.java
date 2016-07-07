package br.jus.stf.autuacao.peticionamento.application.commands;

import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.12.2015
 */
public class PeticionarCommand {

    @NotBlank
    @ApiModelProperty(value = "Identificador da classe processual sugerida pelo peticionador", required=true)
    private String classeId;
    
 	@NotEmpty
	@ApiModelProperty(value = "Lista com as pessoas envolvidas no polo ativo", required=true)
	private List<String> poloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as pessoas envolvidas no polo passivo", required=true)
	private List<String> poloPassivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com os anexos da petição", required=true)
	private List<CadastrarAnexoCommand> anexos;
	
	@NotBlank
	@ApiModelProperty(value = "O grau de sigilo da petição", required=true)
	private String sigilo;
	
	@ApiModelProperty(value = "Preferências da petição")
	private Set<Long> preferencias;
	
	@NotBlank
	@ApiModelProperty(value = "Tipo do processo da petição", required = true)
	private String tipoProcesso;
	
	public PeticionarCommand() {
		// Construtor default
	}
	
	public List<String> getPoloAtivo() {
		return poloAtivo;
	}
	
	public List<String> getPoloPassivo() {
		return poloPassivo;
	}
	
    public String getClasseId() {
        return classeId;
    }
    
    public List<CadastrarAnexoCommand> getAnexos() {
    	return anexos;
    }
    
    public String getSigilo() {
    	return sigilo;
    }
    
    public Set<Long> getPreferencias() {
    	return preferencias;
    }
    
    public String getTipoProcesso() {
    	return tipoProcesso;
    }
    
}
