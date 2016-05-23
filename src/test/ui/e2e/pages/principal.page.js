"use strict";
var PrincipalPage = (function () {
    function PrincipalPage() {
        this.linkIniciarProcesso = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
        this.linkNovoPeticionamentoAdvogado = element(by.css('div[ui-sref="app.novo-processo.peticionamento"]'));
    }
    PrincipalPage.prototype.iniciarProcesso = function () {
        this.linkIniciarProcesso.click();
    };
    PrincipalPage.prototype.iniciarPeticionamentoAdvogado = function () {
        this.linkNovoPeticionamentoAdvogado.click();
        browser.sleep(3000);
    };
    return PrincipalPage;
}());
exports.PrincipalPage = PrincipalPage;
