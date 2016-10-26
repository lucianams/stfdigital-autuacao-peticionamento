import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {PeticionamentoPage} from "./pages/peticionamento.page";

import support = require('./shared/helpers/support');

describe("Peticionamento - Órgão", () => {

    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let peticionamentoPage: PeticionamentoPage = new PeticionamentoPage();
    
    it ('Deveria logar no sistema', () => {
        loginPage.open();
        loginPage.login('representante', '123');
    });
    
    it ('Deveria acessar o novo processo de peticionamento com Representação de Órgão', () => {
        principalPage.iniciarProcessoPorNome('Nova Petição com Representação de Órgão');
    });

    it("Deveria escolher o órgão de representação", () => {
        peticionamentoPage.selecionarOrgaoPeticionador("Superior Tribunal de Justiça");
    });

    it("Deveria preencher as informações básicas da petição", () => {
        peticionamentoPage.selecionarClassePeticionavel("MANDADO DE SEGURANÇA");
        peticionamentoPage.selecionarPreferencias("Réu Preso", "Medida Liminar");
        peticionamentoPage.selecionarSigilo("Público");
    });

    it('Deveria preencher as informações das partes', () => {
        peticionamentoPage.informarEnvolvidoPoloAtivo("Maria");
        peticionamentoPage.informarEnvolvidoPoloPassivo("João");

        peticionamentoPage.excluirEnvolvidoPoloAtivo(0);
        peticionamentoPage.excluirEnvolvidoPoloPassivo(0);

        peticionamentoPage.informarEnvolvidoPoloAtivo("Mariana");
        peticionamentoPage.informarEnvolvidoPoloPassivo("Joana");
    });

    it('Deveria fazer o upload dos anexos', () => {
        peticionamentoPage.uploadAnexo();
        peticionamentoPage.uploadAnexo();

        peticionamentoPage.aguardarUploadConcluido(0);
        peticionamentoPage.aguardarUploadConcluido(1);

        peticionamentoPage.selecionarTipoAnexo(0, "Petição inicial");
        peticionamentoPage.selecionarTipoAnexo(1, "Informação");

        peticionamentoPage.excluirAnexos();

        peticionamentoPage.uploadAnexo();
        peticionamentoPage.aguardarUploadConcluido(0);

        peticionamentoPage.selecionarTipoAnexo(0, "Petição inicial");
    });

    it('Deveria peticionar', () => {
        peticionamentoPage.peticionarOrgao();

        expect(principalPage.exibiuMensagemSucesso()).toBeTruthy();
    });

    it('Deveria fazer o logout do sistema', () => {
		principalPage.logout();
	});
});