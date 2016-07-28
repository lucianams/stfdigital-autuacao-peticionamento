import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;

/** @ngInject **/
function config($stateProvider: IStateProvider,
                msNavigationServiceProvider: any) {

    $stateProvider.state('app.novo-processo.peticionamento', {
        url : '/peticionamento',
        views : {
            'content@app.autenticado' : {
                templateUrl : 'peticao.tpl.html',
                controller : 'app.novo-processo.peticionamento.PeticaoController',
                controllerAs: 'vm'
            }
        }
    });
    
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
    $translatePartialLoader.addPart(properties.apiUrl + '/peticionamento/peticoes');
}

let peticionamento: IModule = angular.module('app.novo-processo.peticionamento', ['app.novo-processo', 'app.support', 'angularFileUpload', 'ngCookies']);
peticionamento.config(config).run(run);

export default peticionamento;