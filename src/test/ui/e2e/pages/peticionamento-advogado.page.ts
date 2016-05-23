import ElementFinder = protractor.ElementFinder;
import path = require("path");

export class PeticionamentoAdvogadoPage {
    
    private caminhoArquivo = browser.params.filesDirPath ? browser.params.filesDirPath : (__dirname + '/../files');
    
    public selecionarClassePeticionavel(): void {
        element(by.id("idClassePeticionavel")).click();
        element.all(by.repeater("classe in vm.classes")).get(0).click();   
    }
    
    public informarEnvolvidoPoloAtivo(nome: string): void {
        element(by.id("txtPartePoloAtivo")).sendKeys(nome, protractor.Key.ENTER);
    }
    
    public informarEnvolvidoPoloPassivo(nome: string): void {
        element(by.id("txtPartePoloPassivo")).sendKeys(nome, protractor.Key.ENTER);
    }
    
    public uploadAnexo(): void {
        
        let nomeArquivo = 'pdf-de-teste-assinado-02.pdf';
        let caminhoAbsoluto =  path.resolve(this.caminhoArquivo, nomeArquivo);
        let fileUpload = element(by.css('input[type="file"]'));

        // Envia o caminho e o arquivo para o input fazer a submissão. Não é necessário clicar no botão
        fileUpload.sendKeys(caminhoAbsoluto);
        browser.waitForAngular();
    }
    
    public selecionarTipoAnexo(indice: number, descricao: string) {

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
    
    public excluirAnexos(): void {
        let btnExcluirAnexos = element(by.id("btnRemoverAnexos")).click();
    }
    
    public excluirEnvolvidoPoloAtivo(indice: number): void{
        element(by.id("lnkRemoverEnvolvidoPoloAtivo-" + indice)).click();
    }
    
    public excluirEnvolvidoPoloPassivo(indice: number): void{
        element(by.id("lnkRemoverEnvolvidoPoloPassivo-" + indice)).click();
    }
    
    public peticionar(): void{
        element(by.id("btnPeticionar")).click();
    }
}