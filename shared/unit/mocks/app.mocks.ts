/**
 * Mocks dos módulos e alguns componentes para os testes unitários.
 * 
 */
namespace app.novoProcesso {
	'use strict';
	
    function config() {

        
    }

    angular
        .module('app.novo-processo', ['ui.router', 'pascalprecht.translate'])
        .config(config);
}

namespace app.documentos {
	'use strict';
	
    function config() {

        
    }

    angular
        .module('app.documentos', [])
        .config(config);
}

namespace app.support {
	'use strict';
	
    function config() { }
    
	let url = 'docker';
	let port = "8765";

	angular.module('app.support.constants', []).constant("properties", {
	    "url": url,
	    "port": port,
	    "apiUrl": url + ":" + port,
	    "development": true
	});
    
    angular
		.module('app.support.command', [])
		.config(config);

    class CommandServiceMock {
    	setValidator(){}
    }
    
    angular
		.module('app.support.command')
		.service('commandService', CommandServiceMock);
    
	angular
	    .module('app.support.messaging', [])
	    .config(config);

	angular
	    .module('app.certification', []);

	angular
	    .module('checklist-model', []);

    angular
    	.module('app.support', ['app.support.command', 'app.support.messaging', 'app.support.constants'])
    	.config(config);
}
