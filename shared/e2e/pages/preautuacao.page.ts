import ElementFinder = protractor.ElementFinder;

import helpers = require('protractor-helpers');

export class PreautuacaoPage {
	
	public open(): void {
		browser.get('/novo-processo/preautuacao/originario');
	}

	public selecionarClasse() : void {
		element(by.id('classes')).click();
		element.all(by.repeater('classe in preautuacao.classes')).get(2).click();
	};
	
	public selecionarPreferencia() : void {
		element(by.id('preferencias')).click();
		element.all(by.repeater('preferencia in preautuacao.preferencias')).get(1).click();
		browser.driver.sleep(500);
		browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
		element(by.id('motivo')).click();
	};
	
	public registrarPreautuacao() : void {
		element(by.id('preautuar-remessa')).click();
	};
	
	public devolver() : void {
		element(by.id('devolver-remessa')).click();
	};
}