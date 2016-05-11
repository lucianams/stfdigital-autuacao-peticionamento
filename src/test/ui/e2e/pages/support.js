/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.09.2015
 */
/*jshint undef:false */
(function() {
	'use strict';


	var Utils = function () {
		var util = this;
		
		/**
		 * Selecionar um dado item dentro de uma combo select2. 
		 * 
		 * <p>O código abaixo suporta carregamento remoto das opções. Ele também trata diversas "issues" 
		 * envolvendo o 'select2' e o protractor para realização testes E2E.
		 * 
		 * @param {string} id o seletor CSS do container (div) select2
		 * @param {string} query o termo de consulta pela opção dentro da combo do select2
		 */
		util.select = function(id, query) {
		    // O elemento 'a' dentro do select2 vai receber um evento 'mousedown'
		    var selector = id + ' a.select2-choice';
		    
		    // Localizador para as opções do select2
		    var options = element.all(by.css('.select2-results-dept-0'));

		    // O select2 não ativa no click e o protractor não tem um método mousedown no 'ElementFinder'.
		    browser.driver.executeScript('$(arguments["0"]).mousedown();', (selector));

		    if(query){
		        browser.driver.switchTo().activeElement().sendKeys(query);
		        // O select2 pode ter que carregar as opções remotametamente, então nós temos que confirmar que todas
		        // as requisições pendentes foram resolvidas depois de digitar a consulta
		        browser.driver.wait(function() {
		            return browser.driver.executeScript('return $.active === 0;');
		        }, 2000);
		    }

		    // Garantindo que todas as opções foram renderizadas
		    browser.driver.wait(function(){
		        return options.count().then(function(count){
		            return 0 < count;
		        });
		    }, 2000);

		    options.first().click();
		};
		
		/**
		 * Selecionar um dado item dentro de uma combo select2Multiplo. 
		 * 
		 * <p>O código abaixo suporta carregamento remoto das opções. Ele também trata diversas "issues" 
		 * envolvendo o 'select2' e o protractor para realização testes E2E.
		 * 
		 * @param {string} id o seletor CSS do container (div) select2
		 * @param {string} query o termo de consulta pela opção dentro da combo do select2
		 */
		util.selectMultiple = function(id, query) {
		    // O elemento 'a' dentro do select2 vai receber um evento 'mousedown'
		    var selector = id + ' a.select2-choice';
		    
		    // Localizador para as opções do select2
		    var options = element.all(by.css('.select2-results-dept-0'));

		    // O select2 não ativa no click e o protractor não tem um método mousedown no 'ElementFinder'.
		    //browser.driver.executeScript('$(arguments["0"]).click();', (selector));
		    var input = element(by.css(id+' .select2-input'));
		    input.click();

		    if(query){
		        browser.driver.switchTo().activeElement().sendKeys(query);
		        // O select2 pode ter que carregar as opções remotametamente, então nós temos que confirmar que todas
		        // as requisições pendentes foram resolvidas depois de digitar a consulta
		        browser.driver.wait(function() {
		            return browser.driver.executeScript('return $.active === 0;');
		        }, 2000);
		    }

		    // Garantindo que todas as opções foram renderizadas
		    browser.driver.wait(function(){
		        return options.count().then(function(count){
		            return 0 < count;
		        });
		    }, 2000);

		    options.first().click();
		};
		
		util.substituirValor = function(el, valor) {
			return el.clear().then(function() {
				return el.sendKeys(valor);
			});
		};
		
	};

	module.exports = Utils;
	
})();
