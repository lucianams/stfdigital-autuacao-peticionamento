import {PeticaoController, ChildScope} from "../peticao.controller";
import {PeticionarOrgaoCommand, PeticaoService, OrgaoPeticionador} from "../peticao.service";
import peticionamento from "../peticao.module";

export class PeticaoOrgaoController {

    public static $inject = ["$scope", "app.peticionamento.peticoes.PeticaoService", "orgaos"];

    constructor(private $scope: ChildScope, private peticaoService: PeticaoService, public orgaos: OrgaoPeticionador[]) {
        this.$scope.$parent.child = this.$scope;

        this.$scope.cmd = this.instanciarCommand();
        this.$scope.idAcao = this.getIdAcao();
        this.$scope.enviarPeticao = (command) => this.enviarPeticao(command);
    }

    private instanciarCommand(): PeticionarOrgaoCommand {
        return new PeticionarOrgaoCommand();
    }

    private getIdAcao(): string {
        return 'peticionar-orgao';
    }

    private enviarPeticao(command: PeticionarOrgaoCommand): ng.IPromise<any> {
        return this.peticaoService.enviarPeticaoOrgao(command);
    }

    public cmd(): PeticionarOrgaoCommand {
        return this.$scope.cmd;
    }

}

peticionamento.controller('app.peticionamento.peticoes.PeticaoOrgaoController', PeticaoOrgaoController);

export default peticionamento;