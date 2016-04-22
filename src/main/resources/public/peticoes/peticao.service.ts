import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import peticionamento from "./peticao.module";

export interface IAnexo {
    documento: number,
    tipo: number
}

export interface IPeticao {
    classeId: string,
    poloAtivo: string[],
    poloPassivo: string[],
    anexos: IAnexo[]
}

export class Anexo implements IAnexo {

    constructor(public documento: number,
                public tipo: number) {}
}

export class Peticao implements IPeticao {

    constructor(public classeId: string,
                public poloAtivo: string[],
                public poloPassivo: string[],
                public anexos: IAnexo[]) {}
}

export class PeticaoService {

    private static apiPeticionamento: string = '/peticionamento/api/peticoes';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public peticionar(peticao: IPeticao): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + PeticaoService.apiPeticionamento, peticao);
    }
}

peticionamento.service('app.novo-processo.peticionamento.PeticaoService', PeticaoService);

export default peticionamento;