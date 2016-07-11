declare var $;
declare var angular;

/**
 * É necessário mockar o messagesService devido a uma incompatibilidade do $mdToast com o protractor, pelo fato de
 * ele utilizar o $timeout e isso fazer com que o protractor aguarde o término do mesmo, o que inviabiliza a verificação
 * das mensagens e a continuação do teste sem aguardar a mensagem desaparecer.
 * 
 * https://github.com/angular/protractor/issues/169
 * https://github.com/angular/material/issues/8357
 * https://github.com/angular/material/pull/2731
 * https://github.com/angular/material/issues/8357
 */
function messagingModuleDefineFunction() {

	let mockMessagingModule = angular.module('e2e.mocks.messaging', ['app.support.messaging']);

	let mockMessagesService = ['$delegate', '$q', '$interval', ($delegate, $q, $interval) => {
		$delegate.success = (message: string) => {
			let el = $('<md-toast md-theme="success-toast" class="ng-scope _md _md-bottom _md-right md-success-toast-theme"><div class="md-toast-content"><span flex="" class="md-toast-text ng-binding flex" role="alert" aria-relevant="all" aria-atomic="true">' + message + '</span></div></md-toast>');
			$('body').prepend(el);
			$interval(() => {
				el.remove();
  			}, 3000, 1);
		};

		$delegate.error = (message: string) => {
			let el = $('<md-toast md-theme="error-toast" class="ng-scope _md _md-bottom _md-right md-error-toast-theme"><div class="md-toast-content"><span flex="" class="md-toast-text ng-binding flex" role="alert" aria-relevant="all" aria-atomic="true">' + message + '</span></div></md-toast>');
			$('body').prepend(el);
			$interval(() => {
				el.remove();
  			}, 3000, 1);
		};

		return $delegate;
	}];

	mockMessagingModule.config(['$provide', ($provide) => {
		$provide.decorator('messagesService', mockMessagesService);
	}]);

	mockMessagingModule.run(['messagesService', (messagesService) => {
		// É necessário injetar esse serviço para que o decorator acima seja executado corretamente.
	}]);
}

export default messagingModuleDefineFunction;