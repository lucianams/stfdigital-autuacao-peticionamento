import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import {PeticaoService} from "./peticao.service";

/** @ngInject **/
function config($stateProvider: IStateProvider,
                msNavigationServiceProvider: any) {

    $stateProvider.state('app.novo-processo.peticionamento', {
        url : '/peticionamento',
        views : {
            'content@app.autenticado' : {
                templateUrl : 'peticao.tpl.html',
                controller : 'app.peticionamento.peticoes.PeticaoController',
                controllerAs: 'vm'
            }
        },
        resolve: {
        	classes: ['app.peticionamento.peticoes.PeticaoService', (peticaoService: PeticaoService) => {
        		return peticaoService.listarClasses();
        	}],
        	tiposAnexo: ['app.peticionamento.peticoes.PeticaoService', (peticaoService: PeticaoService) => {
        		return peticaoService.listarTiposAnexo();
        	}],
        	sigilos: ['app.peticionamento.peticoes.PeticaoService', (peticaoService: PeticaoService) => {
                return peticaoService.listarSigilos();
            }]
        }
    });
    
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
    $translatePartialLoader.addPart(properties.apiUrl + '/peticionamento/peticoes');
}

let peticionamento: IModule = angular.module('app.peticionamento.peticoes', ['app.support', 'angularFileUpload', 'ngCookies']);
peticionamento.config(config).run(run);

export default peticionamento;