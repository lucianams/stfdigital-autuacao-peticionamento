System.register(["./peticao.service", "./peticao.module"], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var peticao_service_1, peticao_module_1;
    var PeticaoController;
    return {
        setters:[
            function (peticao_service_1_1) {
                peticao_service_1 = peticao_service_1_1;
            },
            function (peticao_module_1_1) {
                peticao_module_1 = peticao_module_1_1;
            }],
        execute: function() {
            PeticaoController = (function () {
                function PeticaoController($state, peticaoService) {
                    this.$state = $state;
                    this.peticaoService = peticaoService;
                    this.basicForm = {};
                    this.formWizard = {};
                    this.states = PeticaoController.mockClasses();
                }
                PeticaoController.prototype.sendForm = function () {
                    var _this = this;
                    this.peticaoService.peticionar(PeticaoController.mockPeticao())
                        .then(function () {
                        _this.formWizard = {};
                        _this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
                    });
                };
                PeticaoController.mockClasses = function () {
                    return ('ADI ADO')
                        .split(' ')
                        .map(function (state) { return { abbrev: state }; });
                };
                PeticaoController.mockPeticao = function () {
                    var anexo = new peticao_service_1.Anexo(1, 1);
                    return new peticao_service_1.Peticao("ADI", ["João da Silva"], ["Maria da Silva"], [anexo]);
                };
                PeticaoController.$inject = ['$state', 'app.novo-processo.peticionamento.PeticaoService'];
                return PeticaoController;
            }());
            exports_1("PeticaoController", PeticaoController);
            peticao_module_1.default.controller('app.novo-processo.peticionamento.PeticaoController', PeticaoController);
            exports_1("default",peticao_module_1.default);
        }
    }
});
//# sourceMappingURL=peticao.controller.js.map