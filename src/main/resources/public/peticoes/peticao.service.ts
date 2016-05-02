import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import peticionamento from "./peticao.module";

/*
export interface IAnexo {
    documento: number,
    tipo: number
}
*/
export interface IPeticao {
    classeId: string,
    poloAtivo: string[],
    poloPassivo: string[],
    pecas: Array<Peca>
}

export class Documento {
    constructor(public numero: number, public tipo: number) {}
}

export class Peticao implements IPeticao {
    constructor(public classeId: string, public poloAtivo: Array<string>, public poloPassivo: Array<string>, public pecas: Array<Peca>) {}
}

export class Classe {
    public sigla: string;
    public nome: string;
    
    constructor(sigla: string, nome: string) {
        this.sigla = sigla;
        this.nome = nome;
    }
}

export class TipoPeca{
    public id: number;
    public descricao: string;
    
    constructor(id: number, descricao: string) {
        this.id = id;
        this.descricao = descricao;   
    }
}

export class Peca {
    public arquivo: any;
    public documentoTemporario : any;
    public tipo: TipoPeca;
    public isUploadConcluido: boolean;
    
    constructor(arquivo: any, documentoTemporario: any, tipo: TipoPeca, isUploadConcluido) {
        this.arquivo = arquivo;
        this.documentoTemporario = documentoTemporario;
        this.tipo = tipo;
        this.isUploadConcluido = isUploadConcluido;
    }
}

export class PeticaoService {

    //Endereço do serviço de peticionamento.
    private static apiPeticionamento: string = '/peticionamento/api/peticoes';
    
    //Endereço do serviço de de classes.
    private static url: string = "/peticionamento/api/classes";

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public enviarPeticao(peticao: IPeticao): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + PeticaoService.apiPeticionamento, peticao);
    }
    
    public listarTipoPecas(): Array<TipoPeca> {
        let tiposPecas = new Array<TipoPeca>();
        tiposPecas.push(new TipoPeca(1, "Petição Inicial"));
        tiposPecas.push(new TipoPeca(2, "Ato Coator"));
        tiposPecas.push(new TipoPeca(3, "Documentos Comprobatórios"));
        
        return tiposPecas;
    }
    
    /* Recupera as classes processiais */    
    public listarClasses() : Array<Classe>{
        //return this.httpService.get(this.properties.url);
        
        let classes: Array<Classe> = new Array<Classe>();
        classes.push(new Classe("ADI", "Ação Direta de Inconstitucionalidade"));
        classes.push(new Classe("RE", "Recurso Extraordinário"));
        
        return classes;
    }
}

peticionamento.service('app.novo-processo.peticionamento.PeticaoService', PeticaoService);

export default peticionamento;