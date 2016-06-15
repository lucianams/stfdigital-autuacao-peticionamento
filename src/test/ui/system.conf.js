System.config({
	transpiler: null,
	defaultJSExtensions: true,
	map: {
		'systemjs': './node_modules/systemjs/dist/system.src.js',
		'system-polyfills': './node_modules/systemjs/dist/system-polyfills.src.js'
	},
	bundles: {
		'recebimento/preparar-oficio-devolucao': ['recebimento/preparar-oficio-devolucao/*']
	},
	strictImportSequence: true
});