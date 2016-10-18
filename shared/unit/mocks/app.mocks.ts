namespace app.core {
    "use strict";

    angular
            .module("app.core", [
                "ngCookies",
                "pascalprecht.translate",
                "ui.router"
            ]);
}

/**
 * Mocks dos módulos e alguns componentes para os testes unitários.
 * 
 */
namespace app.novoProcesso {
    "use strict";

    function config() {

    }

    angular
            .module("app.novo-processo", ["ui.router", "pascalprecht.translate"])
            .config(config);
}

namespace app.documentos {
    "use strict";

    function config() {

    }

    angular
            .module("app.documentos", [])
            .config(config);
}

namespace app.support {
    "use strict";

    function config() { }

    let url = "docker";
    let port = "8765";

    angular.module("app.support.constants", []).constant("properties", {
        "url": url,
        "port": port,
        "apiUrl": url + ":" + port,
        "development": true
    });

    angular
            .module("app.support.command", [])
            .config(config);

    class CommandServiceMock {
        public addValidator() {}
    }

    angular
            .module("app.support.command")
            .service("commandService", CommandServiceMock);

    class MessagesService {
        public error(message: string): void {

        }

        public success(message: string): void {

        }
    }

    angular
            .module("app.support.messaging", [])
            .service("messagesService", MessagesService)
            .config(config);

    angular
            .module("app.certification", []);

    angular
            .module("checklist-model", []);

    angular
            .module("app.support", ["app.support.command", "app.support.messaging", "app.support.constants"])
            .config(config);
}

angular.module("angularFileUpload", []);

angular.module("ngCookies", []);