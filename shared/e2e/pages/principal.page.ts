import ElementFinder = protractor.ElementFinder;
import ElementArrayFinder = protractor.ElementArrayFinder;

import Promise = webdriver.promise.Promise;

import waitHelpers from '../helpers/wait';

import helpers = require('protractor-helpers');

export class PrincipalPage {
    
    private linkUserMenu: ElementFinder = element(by.id('user-menu'));
	private linkSair: ElementFinder = element(by.css('[ng-click="vm.logout()"]'));
    private linkIniciarProcesso: ElementFinder = element.all(by.css('a[ui-sref="app.novo-processo"]')).first();
    private linkMinhasTarefas: ElementFinder = element(by.css('a[ui-sref="app.tarefas.minhas-tarefas"]'));
    
    private listaTarefas: ElementArrayFinder = element(by.css('.todo-list-pane')).all(by.css('.todo-item'));

    public iniciarProcesso(): void {
    	this.linkIniciarProcesso.click();
    }
    
    public escolherProcesso(state: string) : void {
    	element(by.css('div[ui-sref="' + state + '"]')).click();
    }

    public escolherProcessoPorNome(nome: string) : void {
        element.all(by.css('.processo-list-item')).filter((elem) => {
            return elem.element(by.css('.processo-name')).getText().then((text) => {
                return text === nome;
            });
        }).first().click();
    }

    public iniciarProcessoPorNome(nome: string): void {
        this.iniciarProcesso();
        this.escolherProcessoPorNome(nome);
        this.aguardarSairTelaNovoProcesso();
    }
    
    private aguardarSairTelaNovoProcesso() {
        browser.wait(element(by.id('novo-processo')).isPresent().then((present) => !present));
    }

    public aguardarUrl(url: string): void {
    	waitHelpers.waitForUrl(url);
    }

    public acessarTarefa(descricao: string, informationId: number) {
        this.linkMinhasTarefas.click();
        this.localizarTarefas(descricao, informationId).first().click();
    }

    private localizarTarefas(descricao: string, informationId: number): ElementArrayFinder {
        return this.listaTarefas.filter((el) => {
            let descriptionElement = el.element(by.css('.title')).element(by.css('.task-description'));
            return descriptionElement.getAttribute('data-task-title').then((text) => {
                return descriptionElement.getAttribute('data-task-information-id').then((actualInformationId) => {
                    return text === descricao && parseInt(actualInformationId) === informationId;
                });
            });
        });
    }

    public tarefaPresente(descricao: string, informationId: number): Promise<boolean> {
        return this.localizarTarefas(descricao, informationId).count().then((count) => {
            return count > 0;
        });
    }

    public detectarProximoProtocolo(filterFn: (number) => boolean): Promise<number> {
        this.atualizarTarefas();
        let idsPromisesPromise = element(by.css('.todo-list-pane')).all(by.css('.todo-item')).map((el) => {
            return el.element(by.css('.task-description')).getAttribute('data-task-information-id').then((informationId) => {
                return parseInt(informationId);
            });
        });
        return idsPromisesPromise.then((idsPromises) => {
            return protractor.promise.all(idsPromises).then((ids) => {
                let filteredIds = ids.filter(filterFn);
                if (filteredIds.length > 0) {
                    return Math.max.apply(Math, filteredIds) + 1;
                } else {
                    return 1;
                }
            });
        });
    }

    public atualizarTarefas() {
        this.linkIniciarProcesso.click();
        this.linkMinhasTarefas.click();
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

    public aguardarMensagem(timeout: number = 10000): void {
        helpers.waitForElement(this.mensagemFinder(), timeout);
    }

    public mensagemFinder(): ElementFinder {
        return element(by.css('md-toast')).element(by.css('.md-toast-text'));
    }

    public mensagem(): Promise<string> {
        return this.mensagemFinder().getText();
    }

    public exibiuMensagemSucesso(): Promise<boolean> {
        return element(by.css('md-toast[md-theme="success-toast"]')).isPresent();
    }

    public exibiuMensagemErro(): Promise<boolean> {
        return element(by.css('md-toast[md-theme="error-toast"]')).isPresent();
    }
}