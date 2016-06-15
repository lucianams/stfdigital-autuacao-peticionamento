describe('Exemplo Controller', () => {
	
	//beforeEach(angular.mock.module('app.novo-processo.devolucao'));

	beforeEach(inject(($rootScope, $httpBackend: angular.IHttpBackendService, $controller: angular.IControllerService) => {
		var scope = $rootScope.$new();
	}));
	
	it('Deveria testar true', () => {
		expect(true).toEqual(true);
	});
	
});