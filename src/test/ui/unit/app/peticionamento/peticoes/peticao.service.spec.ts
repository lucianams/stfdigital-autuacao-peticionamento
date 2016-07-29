import {PeticaoService, PeticionarCommand} from "peticionamento/peticoes/peticao.service";
import "peticionamento/peticoes/peticao.service";
import 'peticionamento/peticoes/peticao.module';
import Properties = app.support.constants.Properties;
import cmd = app.support.command;

describe("Teste do serviço de petição", () => {

    let $httpBackend : ng.IHttpBackendService;
    let peticaoService : PeticaoService;
	let properties;
	let handler;

    beforeEach(angular.mock.module('app.core', 'app.support', 'app.peticionamento.peticoes'));

    beforeEach(inject(['$httpBackend', 'commandService', 'app.peticionamento.peticoes.PeticaoService', 'properties', (_$httpBackend_ : ng.IHttpBackendService, commandService : cmd.CommandService, _peticaoService_ : PeticaoService, _properties_: Properties) => {
        $httpBackend = _$httpBackend_;
        peticaoService =  _peticaoService_;
       	properties = _properties_;
    }]));
    
    beforeEach(() => {
        handler = {
            success: () => {},
            error: () => {}
		}; 
 		spyOn(handler, 'success').and.callThrough();
		spyOn(handler, 'error').and.callThrough();
    });

    it("Deveria chamar o serviço rest de envio de petição por advogado", () => {
        let command: PeticionarCommand = new PeticionarCommand();
    	
        $httpBackend.expectPOST(properties.apiUrl + '/peticionamento/api/peticoes', command).respond(200, "");
        peticaoService.enviarPeticaoAdvogado(command).then(handler.success, handler.error);
        $httpBackend.flush();

        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });

    
});