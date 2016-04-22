System.register(["./peticao.module"], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var peticao_module_1;
    var Anexo, Peticao, PeticaoService;
    return {
        setters:[
            function (peticao_module_1_1) {
                peticao_module_1 = peticao_module_1_1;
            }],
        execute: function() {
            Anexo = (function () {
                function Anexo(documento, tipo) {
                    this.documento = documento;
                    this.tipo = tipo;
                }
                return Anexo;
            }());
            exports_1("Anexo", Anexo);
            Peticao = (function () {
                function Peticao(classeId, poloAtivo, poloPassivo, anexos) {
                    this.classeId = classeId;
                    this.poloAtivo = poloAtivo;
                    this.poloPassivo = poloPassivo;
                    this.anexos = anexos;
                }
                return Peticao;
            }());
            exports_1("Peticao", Peticao);
            PeticaoService = (function () {
                /** @ngInject **/
                function PeticaoService($http, properties) {
                    this.$http = $http;
                    this.properties = properties;
                }
                PeticaoService.prototype.peticionar = function (peticao) {
                    return this.$http.post(this.properties.url + ":" + this.properties.port + PeticaoService.apiPeticionamento, peticao);
                };
                PeticaoService.apiPeticionamento = '/peticionamento/api/peticoes';
                return PeticaoService;
            }());
            exports_1("PeticaoService", PeticaoService);
            peticao_module_1.default.service('app.novo-processo.peticionamento.PeticaoService', PeticaoService);
            exports_1("default",peticao_module_1.default);
        }
    }
});
//# sourceMappingURL=peticao.service.js.map