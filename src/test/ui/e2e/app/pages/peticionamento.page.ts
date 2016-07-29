import ElementFinder = protractor.ElementFinder;
import path = require("path");

import mdHelpers = require('../shared/helpers/md-helpers');
import support = require('../shared/helpers/support');

export class PeticionamentoPage {
    
    private caminhoArquivo = browser.params.filesDirPath ? browser.params.filesDirPath : (__dirname + '/../../files');
    
    public selecionarClassePeticionavel(classe: string): void {
        mdHelpers.mdSelect.selectOptionWithText(element(by.id('idClassePeticionavel')), classe);
    }
    
    public selecionarOrgaoPeticionador(orgao: string): void {
        mdHelpers.mdSelect.selectOptionWithText(element(by.id('orgao')), orgao);
    }

    public selecionarSigilo(sigilo: string): void {
        mdHelpers.mdSelect.selectOptionWithText(element(by.id('sigilo')), sigilo);
    }

	public selecionarPreferencias(...preferencias: string[]): void {
		mdHelpers.mdSelect.selectMultipleOptionsWithText(element(by.id('preferencias')), preferencias);
	}

    public informarEnvolvidoPoloAtivo(nome: string): void {
        element(by.id("txtEnvolvidoPoloAtivo")).sendKeys(nome, protractor.Key.ENTER);
    }
    
    public informarEnvolvidoPoloPassivo(nome: string): void {
        element(by.id("txtEnvolvidoPoloPassivo")).sendKeys(nome, protractor.Key.ENTER);
    }
    
    public scrollToBottom(): void {
        support.scrollToElement(element(by.css('button[command]')));
    }

    public uploadAnexo(): void {
        this.scrollToBottom();
        let nomeArquivo = 'pdf-de-teste-assinado-02.pdf';
        let caminhoAbsoluto =  path.resolve(this.caminhoArquivo, nomeArquivo);
        let fileUpload = element(by.css('input[type="file"]'));

        // Envia o caminho e o arquivo para o input fazer a submissão. Não é necessário clicar no botão
        fileUpload.sendKeys(caminhoAbsoluto);
        browser.waitForAngular();
    }
    
    public aguardarUploadConcluido(index: number, timeout: number = 3000) {
        let uploadedRow = element.all(by.css('.linha-anexo')).get(index);
        browser.wait(uploadedRow.element(by.css('td.coluna-progresso.upload-finished')).isPresent(), timeout);
    };

    public selecionarTipoAnexo(indice: number, descricao: string) {
        let autocomplete = element(by.css(".coluna-tipo-anexo-" + indice)).element(by.css('md-autocomplete'));
        mdHelpers.mdAutocomplete.selectFirstOptionWithText(autocomplete, descricao);
    }
    
    public excluirAnexos(): void {
        let btnExcluirAnexos = element(by.id("btnRemoverAnexos")).click();
    }
    
    public excluirEnvolvidoPoloAtivo(indice: number): void{
        element(by.id("lnkRemoverEnvolvidoPoloAtivo-" + indice)).click();
    }
    
    public excluirEnvolvidoPoloPassivo(indice: number): void{
        element(by.id("lnkRemoverEnvolvidoPoloPassivo-" + indice)).click();
    }
    
    public peticionarAdvogado(): void{
        element(by.id("peticionar")).click();
    }

    public peticionarOrgao(): void{
        element(by.id("peticionar-orgao")).click();
    }
}