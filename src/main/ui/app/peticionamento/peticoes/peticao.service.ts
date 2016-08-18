import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import cmd = app.support.command;
import peticionamento from "./peticao.module";

export class Documento {
    constructor(public numero: number, public tipo: number) {}
}

export interface Preferencia {
    id: number;
    nome: string;
}

export class Sigilo {
    public nome : string;
    public descricao : string;
    
    constructor (nome: string, descricao: string){
        this.nome = nome;
        this.descricao = descricao;
    }
}

export interface Classe {
    sigla: string;
    nome: string;
    preferencias: Array<Preferencia>;
}

export class TipoAnexo {
    public id: number;
    public nome: string;
    
    constructor(id: number, nome: string) {
        this.id = id;
        this.nome = nome;   
    }
}

export class Anexo {
    public arquivo: any;
    public documentoTemporario : any;
    public tipo: TipoAnexo;
    public isUploadConcluido: boolean;
    
    constructor(arquivo: any, documentoTemporario: any, tipo: TipoAnexo, isUploadConcluido) {
        this.arquivo = arquivo;
        this.documentoTemporario = documentoTemporario;
        this.tipo = tipo;
        this.isUploadConcluido = isUploadConcluido;
    }
}

export class DeleteTemporarioCommand {
	public files: Array<string>;

	constructor(files: Array<string>) {
		this.files = files;
	}	
}

export class AnexoDto {
    public tipoDocumentoId: number;
    public documentoId: string;
    
    constructor(tipoDocumentoId: number, documentoId: string){
        this.tipoDocumentoId = tipoDocumentoId;
        this.documentoId = documentoId;
    }
}

export interface OrgaoPeticionador {
    id: number;
    nome: string;
}

export class PeticionarCommand implements cmd.Command {
    /* Identificador da classe processual sugerida pelo peticionador */
    public classeId: string;
	
	/* Identificador do Órgão para o qual o seu representante está peticionando, se for o caso */
    public orgaoId: number;
	
	/* Lista com os envolvidos do polo ativo */
    public poloAtivo: Array<string> = [];
	
	/* Lista com os envolvidos do polo passivo*/
    public poloPassivo: Array<string> = [];

	/* A lista de identificadores dos anexos da petição. */
    public anexos: Array<AnexoDto> = [];
    
	/* O grau de sigilo da petição */
    public sigilo: string;

    /* Tipo do processo da petição */
    public tipoProcesso: string;
	
    public preferencias: Array<number> = [];

    constructor() {
    	this.tipoProcesso = 'ORIGINARIO';
    }
}

export class PeticionarOrgaoCommand extends PeticionarCommand {

}

export class PeticaoService {

    //Endereço do serviço de peticionamento.
    private static apiPeticionamento: string = '/peticionamento/api/peticoes';

    private static apiPeticionamentoOrgao: string = '/peticionamento/api/peticoes/representado';

    private static apiOrgaosPeticionadores: string = '/peticionamento/api/peticoes/orgaos';
    
    //Endereço do serviço de classes peticionáveis.
    private static servicoClassePeticionavel: string = '/peticionamento/api/classes-peticionaveis';
    
    private static servicoTiposAnexo: string = '/peticionamento/api/anexos/tipos';
    
    //Endereço do serviço de de classes.
    private static url: string = "/peticionamento/api/classes";
    
    //Endereço do serviço para salvar um documento temporário.
    private static urlExcluiDocTemporario: string = "/documents/api/documentos/temporarios/delete";

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties: app.support.constants.Properties, private commandService: cmd.CommandService) {
    	commandService.addValidator("peticionar", new ValidadorPeticionamentoAdvogado());
        commandService.addValidator("peticionar-orgao", new ValidadorPeticionamentoOrgao());
    }

    public enviarPeticaoAdvogado(peticionarCommand: PeticionarCommand): IPromise<any> {
        return this.$http.post(this.properties.apiUrl + PeticaoService.apiPeticionamento, peticionarCommand);
    }

    public enviarPeticaoOrgao(peticionarOrgaoCommand: PeticionarOrgaoCommand): IPromise<any> {
        return this.$http.post(this.properties.apiUrl + PeticaoService.apiPeticionamentoOrgao, peticionarOrgaoCommand);
    }

    public excluirDocumentoTemporarioAssinado(arquivosTemporarios: Array<string>): void {
        let cmd: DeleteTemporarioCommand = new DeleteTemporarioCommand(arquivosTemporarios);
        this.$http.post(this.properties.apiUrl + PeticaoService.urlExcluiDocTemporario, cmd);
    }
    
    /* Recupera as classes processiais */    
    public listarClasses() : IPromise<Classe[]>{
 
        return this.$http.get(this.properties.url + ":" + this.properties.port + PeticaoService.servicoClassePeticionavel)
            .then((response: IHttpPromiseCallbackArg<Classe[]>) => { 
                return response.data;
            });
        
    }
    
    public listarTiposAnexo(): IPromise<TipoAnexo[]> {
    	return this.$http.get(this.properties.apiUrl + PeticaoService.servicoTiposAnexo).then((response: IHttpPromiseCallbackArg<TipoAnexo[]>) => {
    		return response.data;
    	});
    }
    
    public listarSigilos() : IPromise<Sigilo[]> {
        return this.$http.get(this.properties.apiUrl 
                + PeticaoService.apiPeticionamento + "/sigilos" )
                    .then((response: IHttpPromiseCallbackArg<Sigilo[]>) => { 
                        return response.data; 
                    });
    }

    public listarOrgaos() : IPromise<OrgaoPeticionador[]> {
        return this.$http.get(this.properties.apiUrl 
                + PeticaoService.apiOrgaosPeticionadores, {params: {verificarPerfil: false}})
                    .then((response: IHttpPromiseCallbackArg<OrgaoPeticionador[]>) => { 
                        return response.data; 
                    });
    }
}

class ValidadorPeticionamentoAdvogado implements cmd.CommandValidator {
    
	constructor() {}
    
    public isValid(command: PeticionarCommand): boolean {
        if (command.poloAtivo.length > 0 &&
        	command.poloPassivo.length > 0 &&
        	command.anexos.length > 0 &&
        	command.classeId &&
        	command.sigilo) {
            return true;
        }
        return false;
    }
    
}

class ValidadorPeticionamentoOrgao implements cmd.CommandValidator {
    
	constructor() {}
    
    public isValid(command: PeticionarOrgaoCommand): boolean {
        if (command.poloAtivo.length > 0 &&
        	command.poloPassivo.length > 0 &&
        	command.anexos.length > 0 &&
        	command.classeId &&
        	command.sigilo &&
            command.orgaoId) {
            return true;
        }
        return false;
    }
    
}

peticionamento.service('app.peticionamento.peticoes.PeticaoService', PeticaoService);

export default peticionamento;