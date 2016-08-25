package br.jus.stf.autuacao.peticionamento.interfaces;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.peticionamento.application.PeticionamentoApplicationService;
import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarCommand;
import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarOrgaoCommand;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionadorRepository;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.EnvolvidoDto;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.EnvolvidoDtoAssembler;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.OrgaoPeticionadorDto;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.OrgaoPeticionadorDtoAssembler;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.PeticaoDto;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.PeticaoDtoAssembler;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.SigiloDto;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.12.2015
 */
@RestController
@RequestMapping("/api/peticoes")
public class PeticaoRestResource {
    
    @Autowired
    private PeticionamentoApplicationService peticionarCommandHandler;
    
    @Autowired
    private PeticaoRepository peticaoRepository;
    
    @Autowired
    private EnvolvidoDtoAssembler envolvidoDtoAssembler;
    
    @Autowired
    private OrgaoPeticionadorRepository orgaoRepository;
    
    @Autowired
    private OrgaoPeticionadorDtoAssembler orgaoDtoAssembler;
    
    @Autowired
    private PeticaoDtoAssembler peticaoDtoAssembler;

    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST)
    public void peticionar(@RequestBody @Valid PeticionarCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Petição inválida: " + binding.getAllErrors());
        }

        peticionarCommandHandler.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(value="/representado", method = RequestMethod.POST)
    public void peticionar(@RequestBody @Valid PeticionarOrgaoCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Petição inválida: " + binding.getAllErrors());
        }

        peticionarCommandHandler.handle(command);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/envolvidos", method = RequestMethod.GET)
    public List<EnvolvidoDto> consultarEnvolvidosPeticao(@PathVariable Long id) {
    	Peticao peticao = peticaoRepository.findOne(new ProtocoloId(id));
    	
		return peticao.envolvidos().stream().map(envolvidoDtoAssembler::toDto).collect(toList());
    }
    
    /**
     * @param verificarPerfil
     * @return
     */
    @RequestMapping(value="/orgaos", method = RequestMethod.GET)
    public List<OrgaoPeticionadorDto> listarRepresentados(@RequestParam("verificarPerfil") boolean verificarPerfil) {
		List<OrgaoPeticionador> orgaos = orgaoRepository.findOrgaoRepresentados(verificarPerfil);
		
		return orgaos.stream().map(orgaoDtoAssembler::toDto).collect(toList());
	}
 
	/**
	 * @return
	 */
	@RequestMapping(value="/sigilos", method = RequestMethod.GET)
    public List<SigiloDto> consultarSigilos(){
    	return Arrays.asList(Sigilo.values()).stream().map(sigilo -> new SigiloDto(sigilo.toString(), sigilo.descricao())).collect(Collectors.toList());
    }
	
	/**
     * @param id
     * @return
     */
    @RequestMapping(value="/{protocoloId}", method = RequestMethod.GET)
    public PeticaoDto consultarPeticao(@PathVariable("protocoloId") Long id){
    	return  peticaoDtoAssembler.toDto(peticaoRepository.findOne(new ProtocoloId(id)));
    }
    
}
