import IStateService = angular.ui.IStateService;
import IDialogService = angular.material.IDialogService;
import ICookiesService = angular.cookies.ICookiesService;
import IWindowService = angular.IWindowService;
import {PeticaoService, IPeticao, Peticao, Classe, Anexo, TipoAnexo, Documento} from "./peticao.service";
import peticionamento from "./peticao.module";

export class PeticaoController {
    
    public basicForm: Object = {};
    public formWizard: Object = {};
    public states: Object[] = null;
    public classes: Array<Classe>;
    public classe: string;
    public partePoloAtivo: string;
    public partePoloPassivo: string;
    public partesPoloAtivo: Array<string> = new Array<string>();
    public partesPoloPassivo: Array<string> = new Array<string>();
    public anexos: Array<Anexo> = new Array<Anexo>();
    public tiposAnexos: Array<TipoAnexo> = new Array<TipoAnexo>();
    private uploader: any;
    private configurarSelect2: any;
        
    static $inject = ["$window", "$mdDialog", "$state", "$cookies", "properties", "app.novo-processo.peticionamento.PeticaoService", "FileUploader"];
        
    /** @ngInject **/
    constructor(private $window: IWindowService, private $mdDialog: IDialogService, private $state: IStateService, private $cookies: ICookiesService, 
        private properties, private peticaoService: PeticaoService, FileUploader: any) { 
        this.anexos = [];
        this.classes = peticaoService.listarClasses();
        this.tiposAnexos = peticaoService.listarTipoPecas();
        
        this.uploader = new FileUploader({
            url: properties.url + ":" + properties.port + "/documents/api/documentos/upload/assinado",
            //headers : {"X-XSRF-TOKEN": $cookies.get("XSRF-TOKEN")},
            formData: [{name: "file"}],
            filters: [{
		    	name: "arquivos-pdf",
		    	fn: (file) => {
		    		if (file.type === "application/pdf") {
			    		return true;
		    		} else {
		    			this.exibirMensagem("Não foi possível anexar o arquivo " + file.name + ". <br />Apenas documentos *.pdf são aceitos.", "Anexar Documento");
		    			return false;
		    		}
		    	}
		    }, {
		    	name: "tamanho-maximo",
		    	fn: (file) => {
		    		if (file.size / 1024 / 1024 > 10) {
		    			this.exibirMensagem("Não foi possível anexar o arquivo " + file.name + ". <br />O tamanho do arquivo excede 10mb.", "Anexar Documento");
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
        
        this.uploader.onSuccessItem = function(arquivo, response, status) {
            arquivo.anexo.documentoTemporario = response;
            arquivo.anexo.isExcluirServidor = true;
        };
        
        this.uploader.onErrorItem = (arquivo, response, status) => {
        	/* O status 0 provavelmente foi porque a conexão foi resetada por ultrapassar o tamanho máximo de 10 MB no backend. */
            if (status === 0) {
        		this.exibirMensagem('Não foi possível anexar o arquivo "' + arquivo.file.name + '". <br />O tamanho do arquivo excede 10mb.', "Anexar Arquivos");
        	} else {
                if (status === 500){
                    this.exibirMensagem("Não foi possível anexar o arquivo " + arquivo.file.name + ".", "Anexar Arquivos");
                } else {
                    this.exibirMensagem(response.errors[0].message, "Anexar Arquivos")
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
        
    //remove uma peças da petição.
    private removerAnexo(anexo: any): void {
        if (anexo.isExcluirServidor){
            this.peticaoService.excluirDocumentoTemporarioAssinado([anexo.documentoTemporario]);
        }
        
        let indice = this.anexos.indexOf(anexo);
        this.anexos.splice(indice, 1);
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
    
    private exibirMensagem(mensagem: string, titulo: string){
        let alert = this.$mdDialog.alert().title(titulo).textContent(mensagem).ok("Fechar");
        this.$mdDialog.show(alert).finally(function() {
            alert = undefined;
        });
    }
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.partesPoloAtivo.length; i++){
            if (this.partesPoloAtivo[i] == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                this.exibirMensagem("A parte informada já foi adicionada ao polo ativo.", "Adicionar Parte");
                return;
            }
        }
        
        this.partesPoloAtivo.push(this.partePoloAtivo.toUpperCase());
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
        this.partesPoloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.partesPoloPassivo.length; i++){
            if (this.partesPoloPassivo[i] == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                this.exibirMensagem("A parte informada já foi adicionada ao polo passivo.", "Adicionar Parte");
                return;
            }
        }
        
        this.partesPoloPassivo.push(this.partePoloPassivo.toUpperCase());
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
        this.partesPoloPassivo.splice(indice);
    }
    
    private isFormValido(): boolean {
        return (this.partesPoloAtivo.length > 0 && this.partesPoloPassivo.length >0 && this.anexos.length > 0 && this.classe != "");  
    }
    
    public peticionar(): void {        
        let erro = "";
        
        for(let i = 0; i < this.anexos.length; i++){            
            if (this.anexos[i].tipo == null){
                erro = "Por favor, classifique todas os anexos da sua petição.";
                break;
            }
        }
        
        if (erro != ""){
            this.exibirMensagem(erro, "Peticionamento");
            return;    
        } 
                
        let peticao: IPeticao = this.criarPeticao();
        this.peticaoService.enviarPeticao(peticao).then(() => {
            this.formWizard = {};
            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
        });
    }
    
    private criarPeticao(): IPeticao {    
        return new Peticao(this.classe, this.partesPoloAtivo, this.partesPoloPassivo, this.anexos);
    }
}

peticionamento.controller('app.novo-processo.peticionamento.PeticaoController', PeticaoController);

export default peticionamento;