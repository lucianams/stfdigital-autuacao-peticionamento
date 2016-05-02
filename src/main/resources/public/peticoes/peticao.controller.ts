import IStateService = angular.ui.IStateService;
import IDialogService = angular.material.IDialogService;
import ICookiesService = angular.cookies.ICookiesService;
import IWindowService = angular.IWindowService;
import {PeticaoService, IPeticao, Peticao, Classe, Peca, TipoPeca, Documento} from "./peticao.service";
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
    public pecas: Array<Peca> = new Array<Peca>();
    public tiposPecas: Array<TipoPeca> = new Array<TipoPeca>();
    private uploader: any;
    private configurarSelect2: any;
    
    static $inject = ["$window", "$mdDialog", "$state", "$cookies", "properties", "app.novo-processo.peticionamento.PeticaoService", "FileUploader"];
        
    /** @ngInject **/
    constructor(private $window: IWindowService, private $mdDialog: IDialogService, private $state: IStateService, private $cookies: ICookiesService, 
        private properties, private peticaoService: PeticaoService, FileUploader: any) { 
        this.pecas = [];//new Array<Peca>();
        this.classes = peticaoService.listarClasses();
        
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
            let peca = new Peca(arquivo, null, null, false);
            arquivo.peca = peca;
            this.pecas.push(peca);
			arquivo.upload();
		};
        
        this.uploader.onSuccessItem = function(arquivo, response, status) {
            arquivo.peca.documentoTemporario = response;
            arquivo.peca.isExcluirServidor = true;
        };
        
        this.uploader.onErrorItem = (arquivo, response, status) => {
        	/* O status 0 provavelmente foi porque a conexão foi resetada por ultrapassar o tamanho máximo de 10 MB no backend. */
            if (status === 0) {
        		this.exibirMensagem('Não foi possível anexar o arquivo "' + arquivo.file.name + '". <br />O tamanho do arquivo excede 10mb.', "Anexar Arquivos");
        	} else {
                this.exibirMensagem(response.errors[0].message, "Anexar Arquivos")
            }
        	
            this.removerPeca(arquivo, false);
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
    private removerPeca(arquivo: any, isExcluirServidor: boolean): void {
        if (isExcluirServidor){
            //DocumentoTemporarioService.excluirArquivosTemporarios(arquivoTemporario);
        }
        
        let indice = this.pecas.indexOf(arquivo.peca);
        this.pecas.splice(indice, 1);
    }
    
    private limparPecas() : void {
        var arquivosTemporarios = [];
        
        for(let i = 0; i < this.pecas.length; i++){
            arquivosTemporarios.push(this.pecas[i].documentoTemporario);
        }
        
        //DocumentoTemporarioService.excluirArquivosTemporarios(arquivosTemporarios);
        this.uploader.clearQueue();
        this.uploader.progress = 0;
        //DocumentoTemporarioService.limpar(pecas);
	}
			
    private visualizarPeca(peca: Peca): void {
        var file = new Blob([peca.arquivo._file], {type: 'application/pdf'});
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
    
    private validarForm(): string {
        let erros = "";
        
        if (this.partesPoloAtivo.length === 0) {
            erros = 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>ativo</b>.<br/>';
        }
        
        if (this.partesPoloPassivo.length === 0) {
            erros += 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>passivo</b>.<br/>';
        }
        
        if(this.pecas.length === 0){
            erros += 'Você precisa adicionar <b>pelo menos uma peça</b> na sua petição.<br/>';
        }
        
        for(let i = 0; i < this.pecas.length; i++){
            if (this.pecas[i].tipo == null){
                erros += 'Por favor, classifique todas as peças da sua petição.<br/>';
                break;
            }
        }
        
        return erros;  
    }
    
    public peticionar(): void {
        let erros = this.validarForm();
        let peticao: IPeticao = this.criarPeticao();
        
        if (erros == ""){
            this.peticaoService.enviarPeticao(peticao).then(() => {
                this.formWizard = {};
                this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
            });
        }
    }
    
    private criarPeticao(): IPeticao {    
        return new Peticao(this.classe, this.partesPoloAtivo, this.partesPoloPassivo, this.pecas);
    }
}

peticionamento.controller('app.novo-processo.peticionamento.PeticaoController', PeticaoController);

export default peticionamento;