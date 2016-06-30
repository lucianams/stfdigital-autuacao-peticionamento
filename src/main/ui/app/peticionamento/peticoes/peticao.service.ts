import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import peticionamento from "./peticao.module";

/*
export interface IAnexo {
    documento: number,
    tipo: number
}
*/
export interface IPeticao {
    classeId: string,
    poloAtivo: Array<string>,
    poloPassivo: Array<string>,
    anexos: Array<Anexo>
}

export class Documento {
    constructor(public numero: number, public tipo: number) {}
}

export class Peticao implements IPeticao {
    constructor(public classeId: string, public poloAtivo: Array<string>, public poloPassivo: Array<string>, public anexos: Array<Anexo>) {}
}

export class Classe {
    public sigla: string;
    public nome: string;
    
    constructor(sigla: string, nome: string) {
        this.sigla = sigla;
        this.nome = nome;
    }
}

export class TipoAnexo{
    public id: number;
    public descricao: string;
    
    constructor(id: number, descricao: string) {
        this.id = id;
        this.descricao = descricao;   
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

export class AnexoDto{
    public tipoDocumentoId: number;
    public documentoId: string;
    
    constructor(tipoDocumentoId: number, documentoId: string){
        this.tipoDocumentoId = tipoDocumentoId;
        this.documentoId = documentoId;
    }
}

export class PeticionarCommand {
    /* Identificador da classe processual sugerida pelo peticionador */
    private classeId: string;
	
	/* Identificador do Órgão para o qual o seu representante está peticionando, se for o caso */
	private orgaoId: number;
	
	/* Lista com as partes do polo ativo */
	private poloAtivo: Array<string>;
	
	/* Lista com as partes do polo passivo*/
	private poloPassivo: Array<string>;

	/* A lista de identificadores dos anexos da petição. */
	private anexos: Array<AnexoDto>;
    
    constructor(classeId: string, orgaoId: number, poloAtivo: Array<string>, poloPassivo: Array<string>, anexos: Array<AnexoDto>) {
        this.classeId = classeId;
        this.orgaoId = orgaoId;
        this.poloAtivo = poloAtivo;
        this.poloPassivo = poloPassivo;
        this.anexos = anexos;
    }
}

export class PeticaoService {

    //Endereço do serviço de peticionamento.
    private static apiPeticionamento: string = '/peticionamento/api/peticoes';
    
    //Endereço do serviço de classes peticionáveis.
    private static servicoClassePeticionavel: string = '/peticionamento/api/classes-peticionaveis';
    
    //Endereço do serviço de de classes.
    private static url: string = "/peticionamento/api/classes";
    
    //Endereço do serviço para salvar um documento temporário.
    private static urlExcluiDocTemporario: string = "/documents/api/documentos/temporarios/delete";

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public enviarPeticao(peticao: IPeticao): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + PeticaoService.apiPeticionamento, this.criarCommandPeticionamento(peticao));
    }
    
    private criarCommandPeticionamento(peticao: IPeticao): PeticionarCommand {
        let anexos = new Array<AnexoDto>();
        
        for (let i = 0; i < peticao.anexos.length; i++){
            anexos.push(new AnexoDto(peticao.anexos[i].tipo.id, peticao.anexos[i].documentoTemporario));
        }
        
        let cmd = new PeticionarCommand(peticao.classeId, null, peticao.poloAtivo, peticao.poloAtivo, anexos);
        
        return cmd;
    }
    
    public excluirDocumentoTemporarioAssinado(arquivosTemporarios: Array<string>): void {
        let cmd: DeleteTemporarioCommand = new DeleteTemporarioCommand(arquivosTemporarios);
        this.$http.post(this.properties.url + ":" + this.properties.port + PeticaoService.urlExcluiDocTemporario, cmd);
    }
    
    public listarTipoPecas(): Array<TipoAnexo> {
        let tiposPecas = new Array<TipoAnexo>();
        tiposPecas.push(new TipoAnexo(1, "Petição Inicial"));
        tiposPecas.push(new TipoAnexo(2, "Ato Coator"));
        tiposPecas.push(new TipoAnexo(3, "Documentos Comprobatórios"));
        
        return tiposPecas;
    }
    
    /* Recupera as classes processiais */    
    public listarClasses() : IPromise<Classe[]>{
 
        return this.$http.get(this.properties.url + ":" + this.properties.port + PeticaoService.servicoClassePeticionavel)
            .then((response: IHttpPromiseCallbackArg<Classe[]>) => { 
                return response.data;
            });
        
        /*
        let classes: Array<Classe> = new Array<Classe>();
        classes.push(new Classe("ADI", "AÇÃO DIRETA DE INCONSTITUCIONALIDADE"));
        classes.push(new Classe("ACO", "AÇÃO CÍVEL ORIGINÁRIA"));
        classes.push(new Classe("HD", "HABEAS DATA"));
        classes.push(new Classe("MS", "MANDADO DE SEGURANÇA"));
        classes.push(new Classe("AO", "AÇÃO ORIGINÁRIA"));
        classes.push(new Classe("IF", "INTERVENÇÃO FEDERAL"));
        classes.push(new Classe("AR", "AÇÃO RESCISÓRIA"));
        classes.push(new Classe("SS", "SUSPENSÃO DE SEGURANÇA"));
        classes.push(new Classe("CC", "CONFLITO DE COMPETÊNCIA"));
        classes.push(new Classe("ADC", "AÇÃO DECLARATÓRIA DE CONSTITUCIONALIDADE"));
        classes.push(new Classe("AOE", "AÇÃO ORIGINÁRIA ESPECIAL"));
        classes.push(new Classe("AS", "ARGUIÇÃO DE SUSPEIÇÃO"));
        classes.push(new Classe("ADPF", "ARGÜIÇÃO DE DESCUMPRIMENTO DE PRECEITO FUNDAMENTAL"));
        classes.push(new Classe("SL", "SUSPENSÃO DE LIMINAR"));
        classes.push(new Classe("Pet", "PETIÇÃO"));
        classes.push(new Classe("STA", "SUSPENSÃO DE TUTELA ANTECIPADA"));
        classes.push(new Classe("ADO", "AÇÃO DIRETA DE INCONSTITUCIONALIDADE POR OMISSÃO"));
        classes.push(new Classe("PSV", "PROPOSTA DE SÚMULA VINCULANTE"));
        classes.push(new Classe("AImp", "ARGÜIÇÃO DE IMPEDIMENTO"));
        classes.push(new Classe("EL", "EXCEÇÃO DE LITISPENDÊNCIA"));
        classes.push(new Classe("EI", "EXCEÇÃO DE INCOMPETÊNCIA"));
        classes.push(new Classe("HC", "HABEAS CORPUS"));
        classes.push(new Classe("Rcl", "RECLAMAÇÃO"));
        classes.push(new Classe("MI", "MANDADO DE INJUNÇÃO"));
        classes.push(new Classe("AC", "AÇÃO CAUTELAR"));
        classes.push(new Classe("RvC", "REVISÃO CRIMINAL"));
        
        return classes;
        */
    }
}

peticionamento.service('app.novo-processo.peticionamento.PeticaoService', PeticaoService);

export default peticionamento;