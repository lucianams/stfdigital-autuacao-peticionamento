import {PeticaoController, ChildScope} from "../peticao.controller";
import {PeticionarCommand, PeticaoService} from "../peticao.service";
import peticionamento from "../peticao.module";

export class PeticaoAdvogadoController {

    public static $inject = ["$scope", "app.peticionamento.peticoes.PeticaoService"];

    constructor(private $scope: ChildScope, private peticaoService: PeticaoService) {
        this.$scope.$parent.child = this.$scope;

        this.$scope.cmd = this.instanciarCommand();
        this.$scope.idAcao = this.getIdAcao();
        this.$scope.enviarPeticao = (command) => this.enviarPeticao(command);
    }

    private instanciarCommand(): PeticionarCommand {
        return new PeticionarCommand();
    }

    private getIdAcao(): string {
        return 'peticionar';
    }

    private enviarPeticao(command: PeticionarCommand): ng.IPromise<any> {
        return this.peticaoService.enviarPeticaoAdvogado(command);
    }

}

peticionamento.controller('app.peticionamento.peticoes.PeticaoAdvogadoController', PeticaoAdvogadoController);

export default peticionamento;