import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import {PeticaoService} from "./peticao.service";

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state('app.novo-processo.peticionamento-peticoes', {
        url : '/peticionamento',
        abstract: true,
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
    }).state('app.novo-processo.peticionamento-peticoes-advogado', {
        parent: 'app.novo-processo.peticionamento-peticoes',
        url: '/advogado',
        views: {
            '@app.novo-processo.peticionamento-peticoes': {
                templateUrl: 'advogado/peticao-advogado.tpl.html',
                controller: 'app.peticionamento.peticoes.PeticaoAdvogadoController'
            }
        }
    }).state('app.novo-processo.peticionamento-peticoes-orgao', {
        parent: 'app.novo-processo.peticionamento-peticoes',
        url: '/orgao',
        views: {
            '@app.novo-processo.peticionamento-peticoes': {
                templateUrl: 'orgao/peticao-orgao.tpl.html',
                controller: 'app.peticionamento.peticoes.PeticaoOrgaoController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            orgaos: ['app.peticionamento.peticoes.PeticaoService', (peticaoService: PeticaoService) => {
                return peticaoService.listarOrgaos();
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