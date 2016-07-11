import ElementFinder = protractor.ElementFinder;

import helpers = require('protractor-helpers');

export class PrepararOficioDevolucaoPage {
	
	public open(): void {
		browser.get('/novo-processo/preparar-oficio-devolucao');
	}

	public selecionarMotivo(): void {
		element(by.id('motivoDevolucao')).click();
		browser.driver.sleep(500);
		element.all(by.repeater('motivoDevolucao in vm.motivosDevolucao')).get(1).click();
	}
	
	public selecionarModelo(): void {
		element(by.id('modeloTexto')).click();
		browser.driver.sleep(500);
		element.all(by.repeater('modelo in vm.modelos')).get(0).click();
	}
	
	public aguardarCarregamentoTags(): void {
		let textoPreenchaTags = element(by.id('textoPreenchaTags'));
		browser.wait(textoPreenchaTags.isDisplayed());
	}
	
	public gerarTexto(): void {
		let botaoGerarTexto = element(by.id('btnGerarTexto'));
		browser.executeScript("arguments[0].scrollIntoView();", botaoGerarTexto.getWebElement());
		botaoGerarTexto.click();
	}
	
	public aguardarTextoCarregado(): void {
		helpers.waitForElement(element(by.css('.editor-directive.edicao-iniciada')), 20000);
	}
	
	public finalizarElaboracao(): void {
		let botaoFinalizarTexto = element(by.id('btnFinalizarTexto'));
		browser.executeScript("arguments[0].scrollIntoView();", botaoFinalizarTexto.getWebElement());
		botaoFinalizarTexto.click();
	}
	
}