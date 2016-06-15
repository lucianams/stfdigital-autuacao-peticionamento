import {LoginPage} from "./pages/login.page";
import {PrincipalPage} from "./pages/principal.page";
import {PeticionamentoAdvogadoPage} from "./pages/peticionamento-advogado.page";

describe("Peticionamento - Advogado", () => {
    let paginaLogin: LoginPage = new LoginPage();
    let paginaPrincipal: PrincipalPage = new PrincipalPage();
    let paginaPeticionamento: PeticionamentoAdvogadoPage = new PeticionamentoAdvogadoPage();
    
    it("Deveria logar na aplicação.", () => {
       paginaLogin.open();
       paginaLogin.setCredenciais("fulano", "123"); 
    });
    
    it("Deveria acessar a pagina de peticionamento.", () => {
        paginaPrincipal.iniciarProcesso();
        paginaPrincipal.iniciarPeticionamentoAdvogado();
    });
    
    it("Deveria preencher os dados da petição.", () => {
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