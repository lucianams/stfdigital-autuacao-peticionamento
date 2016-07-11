import ElementFinder = protractor.ElementFinder;

import helpers = require('protractor-helpers');

export class RecebimentoPage {
	
	public preencherQtdVolumes(quantidade : number) : void {
		element(by.id('qtdVolumes')).sendKeys(quantidade.toString());
	};
	
	public selecionarFormaRecebimento() : void {
		element(by.id('formaRecimento')).click();
		element.all(by.repeater('forma in registro.formasRecebimento')).get(2).click();
	};
	
	public preencherQtdApensos(quantidade : number) : void {
		element(by.id('qtdApensos')).sendKeys(quantidade.toString());
	};
	
	public preencherNumSedex(quantidade : number) : void {
		element(by.id('numeroSedex')).sendKeys(quantidade.toString());
	};
	
	public selecionarTipoRecebimento() : void {
		element(by.id('tipoProcesso')).click();
		browser.driver.sleep(500);
		element.all(by.repeater('tipo in registro.tiposProcessos')).get(0).click();
	};
	
	public registrarPeticao() : void {
		element(by.id('registrar-remessa')).click();
	};
	
}