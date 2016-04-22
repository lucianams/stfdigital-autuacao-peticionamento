System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var peticionamento;
    /** @ngInject **/
    function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
        $translatePartialLoaderProvider.addPart('http://docker:8765/peticionamento/peticoes');
        $stateProvider.state('app.novo-processo.peticionamento', {
            url: '/novo-processo/peticao',
            views: {
                'content@app.autenticado': {
                    templateUrl: 'http://docker:8765/peticionamento/peticoes/peticao.tpl.html',
                    controller: 'app.novo-processo.peticionamento.PeticaoController',
                    controllerAs: 'vm'
                }
            }
        });
        msNavigationServiceProvider.saveItem('novo-processo.peticionamento', {
            title: 'Peticionamento',
            icon: 'icon-magnify',
            state: 'app.novo-processo.peticionamento',
            translation: 'PETICOES.NOVA',
            weight: 1
        });
    }
    return {
        setters:[],
        execute: function() {
            peticionamento = angular.module('app.novo-processo.peticionamento', ['app.novo-processo', 'classy']);
            peticionamento.config(config);
            exports_1("default",peticionamento);
        }
    }
});
//# sourceMappingURL=peticao.module.js.map