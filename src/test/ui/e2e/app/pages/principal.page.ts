import ElementFinder = protractor.ElementFinder;

export class PrincipalPage {
    
    private linkIniciarProcesso: ElementFinder = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
	private linkNovoPeticionamentoAdvogado: ElementFinder = element(by.css('div[ui-sref="app.novo-processo.peticionamento"]'));
    
    public iniciarProcesso() : void {
        this.linkIniciarProcesso.click();
    }
    
    public iniciarPeticionamentoAdvogado() : void {
    	this.linkNovoPeticionamentoAdvogado.click();
    	browser.sleep(3000);
    }
}