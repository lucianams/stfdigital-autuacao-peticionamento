import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;

/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                msNavigationServiceProvider: any) {

    $translatePartialLoaderProvider.addPart('http://docker:8765/peticionamento/peticoes');

    $stateProvider.state('app.novo-processo.peticionamento', {
        url : '/novo-processo/peticao',
        views : {
            'content@app.autenticado' : {
                templateUrl : 'http://docker:8765/peticionamento/peticoes/peticao.tpl.html',
                controller : 'app.novo-processo.peticionamento.PeticaoController',
                controllerAs: 'vm'
            }
        }
    });
    
    msNavigationServiceProvider.saveItem('novo-processo.peticionamento', {
        title : 'Peticionamento',
        icon : 'icon-magnify',
        state : 'app.novo-processo.peticionamento',
        translation : 'PETICOES.NOVA',
        weight : 1
    });
}

let peticionamento: IModule = angular.module('app.novo-processo.peticionamento', ['app.novo-processo', 'classy', 'angularFileUpload', 'ngCookies']);
peticionamento.config(config);

export default peticionamento;