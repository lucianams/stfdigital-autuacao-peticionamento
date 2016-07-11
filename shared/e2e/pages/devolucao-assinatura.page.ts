import ElementArrayFinder = protractor.ElementArrayFinder;

import helpers = require('protractor-helpers');

export class DevolucaoAssinaturaPage {

	private itens: ElementArrayFinder = element.all(by.repeater('devolucao in vm.devolucoes'));

	public open(): void {
		browser.get('/novo-processo/devolucao-assinatura');
		helpers.waitForElement(element(by.css('[translate="REMESSA.DEVOLUCAO-ASSINATURA.TITULO"]')));
	}

	public selecionarDevolucoes(): void {
		this.itens.all(by.css('[type=checkbox]')).click();
	}

	public assinar(): void {
		element(by.id('btnAssinar')).click();
	}

	public aguardarTerminoAssinatura(): void {
		this.itens.all(by.css('md-progress-linear[value="100"]'));
	}

}