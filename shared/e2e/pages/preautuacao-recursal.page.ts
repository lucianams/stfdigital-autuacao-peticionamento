import ElementFinder = protractor.ElementFinder;

export class PreautuacaoRecursalPage {

    public open(): void {
        browser.get('/novo-processo/preautuacao/recursal');
    }

	public selecionarClasse(nome: string) : void {
		let select = element(by.id("cboClasses"));
        select.click();
        /*
        select.all(by.tagName('option')).filter(function(elem, index) {
            return elem.getText().then(function(text) {
                return text === nome;
            });
        }).then(function(filteredElements){
            filteredElements[0].click();
        });
        */
        
        //element(by.id('cboClasses')).click();
        browser.driver.sleep(500);
		element.all(by.repeater('classe in preautuacao.classes')).get(2).click();
	};
	
	public selecionarPreferencia() : void {
		element(by.id('cboPreferencias')).click();
		element.all(by.repeater('preferencia in preautuacao.preferencias')).get(1).click();
		browser.driver.sleep(500);
		browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
		element(by.id('txtMotivo')).click();
	};
	
	public registrarPreautuacao() : void {
		element(by.id('preautuar-recursal')).click();
	};
	
    /*
	public selecionarClasseProcessual(indice: number, descricao: string) {
        let select = element(by.id("tipoAnexoId-" + indice));
        browser.executeScript("arguments[0].scrollIntoView();", select.getWebElement());
        select.click();
        select.all(by.tagName('option')).filter(function(elem, index) {
            return elem.getText().then(function(text) {
                return text === descricao;
            });
        }).then(function(filteredElements){
            filteredElements[0].click();
        });
    }
	*/
}