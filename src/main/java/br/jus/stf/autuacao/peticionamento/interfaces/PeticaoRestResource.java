package br.jus.stf.autuacao.peticionamento.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.peticionamento.application.PeticionamentoApplicationService;
import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarCommand;

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

    @RequestMapping(method = RequestMethod.POST)
    public void peticionar(@RequestBody @Valid PeticionarCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
        }
        
        peticionarCommandHandler.handle(command);
    }

}
