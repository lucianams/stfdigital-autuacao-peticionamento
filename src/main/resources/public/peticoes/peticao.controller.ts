import IStateService = angular.ui.IStateService;
import {PeticaoService, IAnexo, IPeticao, Anexo, Peticao} from "./peticao.service";
import peticionamento from "./peticao.module";

export class PeticaoController {

    public basicForm: Object = {};
    public formWizard: Object = {};
    public states: Object[] = PeticaoController.mockClasses();

    static $inject = ['$state', 'app.novo-processo.peticionamento.PeticaoService'];

    constructor(private $state: IStateService,
                private peticaoService: PeticaoService) { }

    public sendForm(): void {
        this.peticaoService.peticionar(PeticaoController.mockPeticao())
            .then(() => {
                this.formWizard = {};
                this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
            });
    }

    private static mockClasses(): Object[] {
        return ('ADI ADO')
            .split(' ')
            .map(state => { return {abbrev: state}; });
    }

    private static mockPeticao(): IPeticao {
        
        var anexo : IAnexo = new Anexo(1,1);
        return new Peticao("ADI", ["João da Silva"], ["Maria da Silva"], [anexo]);
    }
}

peticionamento.controller('app.novo-processo.peticionamento.PeticaoController', PeticaoController);

export default peticionamento;