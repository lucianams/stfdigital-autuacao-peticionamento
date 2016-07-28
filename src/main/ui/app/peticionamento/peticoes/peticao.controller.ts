import IStateService = angular.ui.IStateService;
import IDialogService = angular.material.IDialogService;
import ICookiesService = angular.cookies.ICookiesService;
import IWindowService = angular.IWindowService;
import {PeticaoService, PeticionarCommand, Classe, Anexo, AnexoDto, TipoAnexo, Preferencia, Sigilo} from "./peticao.service";
import peticionamento from "./peticao.module";

export class PeticaoController {
    
	public cmd: PeticionarCommand = new PeticionarCommand();
	
    public preferencias: Array<Preferencia> = new Array<Preferencia>();
    
    public classe: Classe;
    public partePoloAtivo: string;
    public partePoloPassivo: string;
    
    public anexos: Array<Anexo> = new Array<Anexo>();
    
    private uploader: any;
    private configurarSelect2: any;
    
    public path = {id: 'novo-processo.peticionamento', translation:'Peticionamento', uisref: 'app.novo-processo.peticionamento', parent: 'novo-processo'};
        
    static $inject = ["classes", "tiposAnexo", "sigilos", "$window", "$state", "$cookies", "properties", "app.peticionamento.peticoes.PeticaoService", "FileUploader", "messagesService"];
        
    /** @ngInject **/
    constructor(public classes: Array<Classe>, public tiposAnexo: Array<TipoAnexo>, public sigilos: Array<Sigilo>, private $window: IWindowService, private $state: IStateService, private $cookies: ICookiesService, 
        private properties, private peticaoService: PeticaoService, FileUploader: any, private messagesService: app.support.messaging.MessagesService) { 
        
        this.uploader = new FileUploader({
            url: properties.url + ":" + properties.port + "/documents/api/documentos/upload/assinado",
            headers : {"X-XSRF-TOKEN": $cookies.get('access_token'), "Authorization":  'Bearer ' + $cookies.get('access_token')},
            formData: [{name: "file"}],
            filters: [{
		    	name: "arquivos-pdf",
		    	fn: (file) => {
		    		if (file.type === "application/pdf") {
			    		return true;
		    		} else {
		    			this.exibirMensagem("Não foi possível anexar o arquivo " + file.name + ". <br />Apenas documentos *.pdf são aceitos.");
		    			return false;
		    		}
		    	}
		    }, {
		    	name: "tamanho-maximo",
		    	fn: (file) => {
		    		if (file.size / 1024 / 1024 > 10) {
		    			this.exibirMensagem("Não foi possível anexar o arquivo " + file.name + ". <br />O tamanho do arquivo excede 10mb.");
		    			return false;
		    		}
		    		return true;
                }
		    }] 
        });
        
        this.uploader.onAfterAddingFile = (arquivo) => {
            let anexo = new Anexo(arquivo, null, null, false);
            arquivo.anexo = anexo;
            this.anexos.push(anexo);
            arquivo.upload();
        };
        
        this.uploader.onSuccessItem = (arquivo, response, status) => {
            arquivo.anexo.documentoTemporario = response;
            arquivo.anexo.isExcluirServidor = true;
            this.anexosMudaram();
        };
        
        this.uploader.onErrorItem = (arquivo, response, status) => {
        	/* O status 0 provavelmente foi porque a conexão foi resetada por ultrapassar o tamanho máximo de 10 MB no backend. */
            if (status === 0) {
        		this.exibirMensagem('Não foi possível anexar o arquivo "' + arquivo.file.name + '". O tamanho do arquivo excede 10mb.');
        	} else {
                if (status === 500){
                    this.exibirMensagem("Não foi possível anexar o arquivo " + arquivo.file.name + ".");
                } else {
                    this.exibirMensagem(response.errors[0].message);
                }
            }
        	
            this.removerAnexo(arquivo);
        };
        
        this.uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });
        
        this.configurarSelect2 = (idx) => {
			return {
				dropdownCssClass: 'select2-resultado-tipo-peca-' + idx,
				containerCss: {
					'min-width': '100%'
				},
				formatSelection: (item) => {
					var originalText = item.text;
			        return "<div data-original-title='" + originalText + "'>" + originalText + "</div>";
				},
				formatResult: function(item) {
					return item.text;
				}
			};
		};
    }

    public classeSelecionada(): void {
    	this.cmd.classeId = this.classe.sigla;
        this.preferencias = this.classe.preferencias;
   }
    
    //remove uma peças da petição.
    private removerAnexo(anexo: any): void {
        if (anexo.isExcluirServidor){
            this.peticaoService.excluirDocumentoTemporarioAssinado([anexo.documentoTemporario]);
        }
        
        let indice = this.anexos.indexOf(anexo);
        this.anexos.splice(indice, 1);
        this.anexosMudaram();
    }
    
    private limparAnexos() : void {
        let arquivosTemporarios = [];
        let numeroArquivos = this.anexos.length;
        
        for(let i = 0; i < this.anexos.length; i++){
            arquivosTemporarios.push(this.anexos[i].documentoTemporario);
        }
        
        this.peticaoService.excluirDocumentoTemporarioAssinado(arquivosTemporarios);
        
        this.anexos.splice(0);
        this.uploader.clearQueue();
        this.uploader.progress = 0;
	}
			
    private visualizarAnexo(anexo: Anexo): void {
        var file = new Blob([anexo.arquivo._file], {type: 'application/pdf'});
        var fileURL = window.URL.createObjectURL(file);
        this.$window.open(fileURL);
    }
    
    private exibirMensagem(mensagem: string) {
    	this.messagesService.error(mensagem);
    }
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.cmd.poloAtivo.length; i++){
            if (this.cmd.poloAtivo[i] == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                this.exibirMensagem("A parte informada já foi adicionada ao polo ativo.");
                return;
            }
        }
        
        this.cmd.poloAtivo.push(this.partePoloAtivo.toUpperCase());
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
        this.cmd.poloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.cmd.poloPassivo.length; i++){
            if (this.cmd.poloPassivo[i] == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                this.exibirMensagem("A parte informada já foi adicionada ao polo passivo.");
                return;
            }
        }
        
        this.cmd.poloPassivo.push(this.partePoloPassivo.toUpperCase());
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
        this.cmd.poloPassivo.splice(indice);
    }
    
    private anexosMudaram(): void {
        let anexos: Array<AnexoDto> = new Array<AnexoDto>();
        
        anexos = this.anexos.map<AnexoDto>((anexo) => {
        	return new AnexoDto(anexo.tipo ? anexo.tipo.id : null, anexo.documentoTemporario);
        });
        
        this.cmd.anexos = anexos;
    }
    
    public peticionar(): ng.IPromise<any> {        
        let erro = "";
        
        for(let i = 0; i < this.anexos.length; i++){            
            if (this.anexos[i].tipo == null){
                erro = "Por favor, classifique todas os anexos da sua petição.";
                break;
            }
        }
        
        if (erro != ""){
            this.exibirMensagem(erro);
            return;    
        } 
                
//        let peticao: IPeticao = this.criarPeticao();
        return this.peticaoService.enviarPeticao(this.cmd).then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
        });
    }
    
//    private criarPeticao(): IPeticao {
//        return new Peticao(this.classe.sigla, this.partesPoloAtivo, this.partesPoloPassivo, this.anexos, this.preferenciasSelecionadas.map((p) => p.id));
//    }
}

peticionamento.controller('app.peticionamento.peticoes.PeticaoController', PeticaoController);

export default peticionamento;