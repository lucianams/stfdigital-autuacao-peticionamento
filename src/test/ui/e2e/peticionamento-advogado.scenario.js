"use strict";
var login_page_1 = require("./pages/login.page");
var principal_page_1 = require("./pages/principal.page");
var peticionamento_advogado_page_1 = require("./pages/peticionamento-advogado.page");
describe("Peticionamento - Advogado", function () {
    var paginaLogin = new login_page_1.LoginPage();
    var paginaPrincipal = new principal_page_1.PrincipalPage();
    var paginaPeticionamento = new peticionamento_advogado_page_1.PeticionamentoAdvogadoPage();
    it("Deveria logar na aplicação.", function () {
        paginaLogin.open();
        paginaLogin.setCredenciais("fulano", "123");
    });
    it("Deveria acessar a pagina de peticionamento.", function () {
        paginaPrincipal.iniciarProcesso();
        paginaPrincipal.iniciarPeticionamentoAdvogado();
    });
    it("Deveria preencher os dados da petição.", function () {
        paginaPeticionamento.selecionarClassePeticionavel();
        paginaPeticionamento.informarEnvolvidoPoloAtivo("Fulano");
        paginaPeticionamento.informarEnvolvidoPoloPassivo("Beltrano");
        paginaPeticionamento.excluirEnvolvidoPoloPassivo(0);
        browser.sleep(1500);
        paginaPeticionamento.excluirEnvolvidoPoloAtivo(0);
        browser.sleep(1500);
        paginaPeticionamento.informarEnvolvidoPoloAtivo("Fulano");
        paginaPeticionamento.informarEnvolvidoPoloPassivo("Beltrano");
        paginaPeticionamento.uploadAnexo();
        browser.sleep(2000);
        paginaPeticionamento.uploadAnexo();
        browser.sleep(2000);
        paginaPeticionamento.selecionarTipoAnexo(0, "Petição Inicial");
        paginaPeticionamento.selecionarTipoAnexo(1, "Ato Coator");
        browser.sleep(2000);
        paginaPeticionamento.excluirAnexos();
        browser.sleep(2000);
        paginaPeticionamento.uploadAnexo();
        browser.sleep(2000);
        paginaPeticionamento.selecionarTipoAnexo(0, "Petição Inicial");
        browser.sleep(2000);
        paginaPeticionamento.peticionar();
        expect(browser.getCurrentUrl()).toMatch(/\/minhas-tarefas/);
    });
});
