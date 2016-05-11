"use strict";
var path = require("path");
var PeticionamentoAdvogadoPage = (function () {
    function PeticionamentoAdvogadoPage() {
        this.caminhoArquivo = browser.params.filesDirPath ? browser.params.filesDirPath : (__dirname + '/../files');
    }
    PeticionamentoAdvogadoPage.prototype.selecionarClassePeticionavel = function () {
        element(by.id("idClassePeticionavel")).click();
        element.all(by.repeater("classe in vm.classes")).get(0).click();
    };
    PeticionamentoAdvogadoPage.prototype.informarEnvolvidoPoloAtivo = function (nome) {
        element(by.id("txtPartePoloAtivo")).sendKeys(nome, protractor.Key.ENTER);
    };
    PeticionamentoAdvogadoPage.prototype.informarEnvolvidoPoloPassivo = function (nome) {
        element(by.id("txtPartePoloPassivo")).sendKeys(nome, protractor.Key.ENTER);
    };
    PeticionamentoAdvogadoPage.prototype.uploadAnexo = function () {
        var nomeArquivo = 'pdf-de-teste-assinado-02.pdf';
        var caminhoAbsoluto = path.resolve(this.caminhoArquivo, nomeArquivo);
        var fileUpload = element(by.css('input[type="file"]'));
        fileUpload.sendKeys(caminhoAbsoluto);
        browser.waitForAngular();
    };
    PeticionamentoAdvogadoPage.prototype.selecionarTipoAnexo = function (indice, descricao) {
        var select = element(by.id("tipoAnexoId-" + indice));
        browser.executeScript("arguments[0].scrollIntoView();", select.getWebElement());
        select.click();
        select.all(by.tagName('option')).filter(function (elem, index) {
            return elem.getText().then(function (text) {
                return text === descricao;
            });
        }).then(function (filteredElements) {
            filteredElements[0].click();
        });
    };
    PeticionamentoAdvogadoPage.prototype.excluirAnexos = function () {
        var btnExcluirAnexos = element(by.id("btnRemoverAnexos")).click();
    };
    PeticionamentoAdvogadoPage.prototype.excluirEnvolvidoPoloAtivo = function (indice) {
        element(by.id("lnkRemoverEnvolvidoPoloAtivo-" + indice)).click();
    };
    PeticionamentoAdvogadoPage.prototype.excluirEnvolvidoPoloPassivo = function (indice) {
        element(by.id("lnkRemoverEnvolvidoPoloPassivo-" + indice)).click();
    };
    PeticionamentoAdvogadoPage.prototype.peticionar = function () {
        element(by.id("btnPeticionar")).click();
    };
    return PeticionamentoAdvogadoPage;
}());
exports.PeticionamentoAdvogadoPage = PeticionamentoAdvogadoPage;
