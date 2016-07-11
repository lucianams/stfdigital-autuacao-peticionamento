import ElementFinder = protractor.ElementFinder;

import waitHelpers from '../helpers/wait';

import helpers = require('protractor-helpers');

export class PrincipalPage {
    
    private linkIniciarProcesso: ElementFinder = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
	private linkNovaPeticaoFisica: ElementFinder = element(by.css('div[ui-sref="app.novo-processo.recebimento-peticao-fisica"]'));
	private linkPreautuacao: ElementFinder = element(by.css('div[ui-sref="app.novo-processo.recebimento-preautuacao"]'));
	private linkDevolucao: ElementFinder = element(by.css('div[ui-sref="app.novo-processo.recebimento-devolucao"]'));

    private linkUserMenu: ElementFinder = element(by.id('user-menu'));
	private linkSair: ElementFinder = element(by.css('[ng-click="vm.logout()"]'));

    public iniciarProcesso(): void {
    	this.linkIniciarProcesso.click();
    }
    
    public escolherProcesso(state: string) : void {
    	element(by.css('div[ui-sref="' + state + '"]')).click();
    }
    
    public aguardarUrl(url: string): void {
    	waitHelpers.waitForUrl(url);
    }
    
    public iniciarPreautuacao() : void {
    	this.linkPreautuacao.click();
    	browser.sleep(2000);
    }
    
    public iniciarDevolucao(): void {
    	this.linkDevolucao.click();
    	browser.sleep(2000);
    }

    public logout() : void {
    	this.linkUserMenu.click();
    	this.linkSair.click();
    	browser.sleep(1000);
    }

    public aguardarMensagemSucesso(timeout: number = 10000): void {
        let mensagemElement: ElementFinder = element(by.css('[md-theme="success-toast"]'));
        if (timeout) {
            helpers.waitForElement(mensagemElement, timeout);
        } else {
            helpers.waitForElement(mensagemElement);
        }
    }
}