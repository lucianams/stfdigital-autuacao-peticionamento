package br.jus.stf.autuacao.peticionamento.application.commands;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.autuacao.peticionamento.interfaces.dto.AnexoDto;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.EnvolvidoDto;

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
    
    @ApiModelProperty(value = "Lista com as preferências da petição.")
	private List<Long> preferencias;
    
    @ApiModelProperty(value = "Identificador do órgão para o qual o seu representante está peticionando, se for o caso", required=false)
    private Long orgaoId;
    
	@NotEmpty
	@ApiModelProperty(value = "Lista com as pessoas envolvidas no polo ativo", required=true)
	private List<EnvolvidoDto> poloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as pessoas envolvidas no polo passivo", required=true)
	private List<EnvolvidoDto> poloPassivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com os anexos da petição", required=true)
	private List<AnexoDto> anexos;
	
	public List<EnvolvidoDto> getPoloAtivo() {
		return poloAtivo;
	}
	
	public List<EnvolvidoDto> getPoloPassivo() {
		return poloPassivo;
	}
	
    public String getClasseId() {
        return classeId;
    }
    
    public List<Long> getPreferencias() {
		return preferencias;
	}
    
    public Long getOrgaoId() {
        return orgaoId;
    }
    
    public List<AnexoDto> getAnexos() {
    	return anexos;
    }
    
}
