declare var angular;

function mockCryptoModuleDefineFunction() {
	class ConverterService {

		base64ToArrayBuffer(base64) {
			var binaryString =  window.atob(base64);
			var len = binaryString.length;
			var bytes = new Uint8Array(len);
			for (var i = 0; i < len; i++)        {
				bytes[i] = binaryString.charCodeAt(i);
			}
			return bytes.buffer;
		}
		
		arrayBufferToBase64(buffer) {
			var binary = '';
			var bytes = new Uint8Array(buffer);
			var len = bytes.byteLength;
			for (var i = 0; i < len; i++) {
				binary += String.fromCharCode(bytes[i]);
			}
			return window.btoa(binary);
		}
		
		hex2ArrayBuffer(str) {
			var len = Math.floor(str.length / 2);
			var ret = new Uint8Array(len);
			for (var i = 0; i < len; i++) {
				ret[i] = parseInt(str.substr(i * 2, 2), 16);
			}
			return ret.buffer;
		}
		
		arrayBuffer2hex(args) {
			var ret = "";
			var arr = new Uint8Array(args);
			for (var i = 0; i < arr.length; i++) {
				ret += (arr[i] < 16 ? "0" : "") + arr[i].toString(16);
			}
			return ret.toLowerCase();
		}

	}

	class Signature {
		constructor(private converterService: ConverterService, private signatureContent) {

		}

		asHex() {
			return this.converterService.arrayBuffer2hex(this.signatureContent);
		}
		
		asUint8Array() {
			return new Uint8Array(10);
		}
	}

	class JsCrypto {

		constructor(private $q, private converterService: ConverterService, private privateKey, private certificate) {

		}

		certificateAsUint8Array() {
			return new Uint8Array(10);
		}
			
		certificateAsHex() {
			return this.certificate;
		}
		
		sign(data) {
			return this.$q((resolve, reject) => {
				window.crypto.subtle.sign({
						name: 'RSASSA-PKCS1-v1_5'
					},
					this.privateKey,
					new DataView(this.converterService.hex2ArrayBuffer(data.data))
				).then((sig) => {
					resolve(new Signature(this.converterService, sig));
				}).catch((err) => {
					reject(err);
				});
			});
		};

	}

	class JsCryptoFactory {

		static $inject = ['$q', 'e2e.mocks.crypto.ConverterService'];

		constructor(private $q, private converterService: ConverterService) {

		}

		create(privateKey, certificate) {
			return this.$q((resolve, reject) => {
				window.crypto.subtle.importKey('pkcs8', new DataView(this.converterService.base64ToArrayBuffer(privateKey)), <Algorithm>{
					name: 'RSASSA-PKCS1-v1_5',
					hash: {name: 'SHA-256'}
				}, true, ['sign']).then((pk) => {
					resolve(new JsCrypto(this.$q, this.converterService, pk, certificate));
				}).catch((err) => {
					reject(err);
				});
			});
		}
	}

	let mockCryptoModule = angular.module('e2e.mocks.crypto', ['app.certification']);

	mockCryptoModule.service('e2e.mocks.crypto.ConverterService', ConverterService);
	mockCryptoModule.service('e2e.mocks.crypto.JsCryptoFactory', JsCryptoFactory);

	let mockCrypto = ['$delegate', '$q', 'e2e.mocks.crypto.JsCryptoFactory', ($delegate, $q, jsCryptoFactory) => {
		let privateKey =  
			'MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDZd17Xkk1nm9Q2' +
			'EJUwS8TiKGO+zZh2pL+qBOQcu7acjHopeZN3Yjur8n1zQIpcLz/m5K0dsNhBsaL8' +
			'YU2jQPIAuHYaX5bJ4hWZgtbmGWVvg14otWJviHA91D0and5ppVDtxmzrQXdx7aWq' +
			'KvPoWJIx8IdBhMNV+Qqz35Dtf4onqEnsLiV8vBqS23XCNeG1tIDl9cC8HYJ+/xqa' +
			'1cgtvYCaDMRHlRpiXzG/0RcomdF21tphff61ZWkZB8uJKQBNLeRy4qfOd5iGiNUw' +
			'NpmOqcO/90gh5JY7EIi/41ggVxk9zkupbt1ck0Tuw3Jo8q9ryoEYLu9fRrsvKvqu' +
			'vwOGkr2lAgMBAAECggEAeGsTR4ep+Q9e6Tkxg3bRZPI8mXE7VadQwpLGxepj6jgX' +
			'hVPfBqKMrGAwe1tnwjgfyHkkK2a7uVTmDZ7L4PSbjS6Xp277Dnt0vnanDO9PLfqu' +
			'bxhRk7UXggU+eLimahWyxJe1xGeR/rI7pAj42tYAzkqEexFLIRYwiP3pQLn5BI+z' +
			'zW6tGT9d/joErffWD5oxO+x312boYwIVdBc4uKRL/CXjLLhTTWVT0bfkylbteDdL' +
			'0mi4AUINEqYl2W+bjJGYKCZR7jr+GDbZifJj4dcQSHP3TmLEh6XwetzA7B36gkTN' +
			'KU1hoIg+vFpbBfy/tBlSAXhScNMa9tPZCMDz9fz4WQKBgQD8HwyNYb64unH825LY' +
			'Nyk3RsU7PIimesEsvDWXQIERdiZm9Kit7NMQXosMmFoTkUbdO6ZQKvgxzhid/0AY' +
			'p0DI7aIa214omemmBaKfN9wXd7C0A18Js5jMIEIxwf4wvgey3g+3uHu4AjbJYKdR' +
			'bvE40rwxrR6Lrw0TSsYJeAa2hwKBgQDcz9YxuFbg00pe3ubXuSY41PH+c7mZgmW9' +
			'hLFl2VJavHFPfkTB3D09pWuGLl2gBVRdo27k//4xfTkExJO5eDTUkgDARc+3ttyL' +
			'TcPrhgEG/V8ddrMglOG0k3WOUHjjTjBjDdyNMtCwNJDlsmOQxcaaETOtI3O10IQN' +
			'BycCDLwJcwKBgQC/ZXLKAeIfqqrOIoKf0YBgjkE8bv/uIlJz+41TfYx+DQuqLSHA' +
			'p4CzdJJqxV3cbPU3Dgl74qL4bFUT3Kp0DMSJxJzDdrVMbUuK1cxWwrk6t4pVnhmP' +
			'69+bVwHpdDPcHOcLSrjz6AFD96pPxig/EdHeKVzk+V+u+v0aIDGnDo9KEQKBgQC8' +
			'XaRoh8u9QSHJardcQbrkcmv51/j1hnKKSL8YllqVwvmaianYAqxPgWUHc1g1ITbG' +
			'Jpx8SBEtYJiag6NNbckLqX1wiF1ZaGodf/ppWchYkR5pwQ+v9LHSXJTFEcr+hX3r' +
			'kjCaV0YFeuI8wIAFSWJRnekKgxq7eCSeBIL3Uau7FwKBgQDfAZSQ13hEFKjeRIma' +
			'OHy9Gi94+6nY1tS+beQnGYLd8FWBWsj5Yfhpy6xQg5MAMYGVbdgwL9E/qOf0Zil6' +
			'SF1ZSUsEyO3DpS6v1CzT3l2Nm4ywmGWi6yX6a1AZWt4P1tqhdEewK0DRXnH7g/zh' +
			'5IzTLQSzotAyoOywnlGvsSk+2w==';
		let certificate = '308205273082030FA003020102020101300D06092A864886F70D01010B0500306B310B3009060355040613024252310C300A060355040A0C0353544631143012060355040B0C0B535446204449474954414C3138303606035504030C2F414320496E7465726D65646961726961203220646120506C617461666F726D6120535446204469676974616C207631301E170D3135313132363231353035355A170D3136313132353231353035355A3055310B3009060355040613024252310C300A060355040A0C0353544631143012060355040B0C0B535446204449474954414C3122302006035504030C194A4F414F2044412053494C56413A353731353332303033383030820122300D06092A864886F70D01010105000382010F003082010A0282010100D9775ED7924D679BD4361095304BC4E22863BECD9876A4BFAA04E41CBBB69C8C7A29799377623BABF27D73408A5C2F3FE6E4AD1DB0D841B1A2FC614DA340F200B8761A5F96C9E2159982D6E619656F835E28B5626F88703DD43D1A9DDE69A550EDC66CEB417771EDA5AA2AF3E8589231F0874184C355F90AB3DF90ED7F8A27A849EC2E257CBC1A92DB75C235E1B5B480E5F5C0BC1D827EFF1A9AD5C82DBD809A0CC447951A625F31BFD1172899D176D6DA617DFEB565691907CB8929004D2DE472E2A7CE77988688D53036998EA9C3BFF74821E4963B1088BFE3582057193DCE4BA96EDD5C9344EEC37268F2AF6BCA81182EEF5F46BB2F2AFAAEBF038692BDA50203010001A381EB3081E8301D0603551D0E041604142429E93FC24912881B8E3E30D7B18DF93D3A1681301F0603551D23041830168014DAF6AAC4C2D09E7786BBFBCC110F4C28B3E26EF730290603551D250422302006082B0601050507030206082B06010505070304060A2B060104018237140202300E0603551D0F0101FF0404030205E0306B0603551D110464306281206A6F616F2E73696C7661407374666469676974616C2E7374662E6A75732E6272A03E0605604C010301A0350433303030303030303035373135333230303338303030303030303030303030303030303030303030303030303030303030303030300D06092A864886F70D01010B05000382020100360C4A5ADD9D3E0BA500DB5EF7F011F4BA0ED35B21A80D3E02D91EFC3CA1FDC2DB73F8C4A4EA8EDBC98DB1BCA69B9CEAA115AFD735227372C16601F43261DF5E96EFA6B2DA796E64E2CAA86D5E82CD7F32AD7212AC9E1237E3BD11C864B543829E7D8C7F67C8068F00B23F3759B13DA6F7AD891DCC943722DE6E13DF7B670CF8F2D8244F776E6610463662FC8F5F4722300B561EF7F72A20B63C7F50318BEE548E9A11FA4C3451F3E7FB012D4A0394EBD1CCDE1226197BBC3BF768E63F5DEE9D82AE9BD6B5DE3EF927E028B056F7232AC32E29ACF31259F93A7C5762BC8BCC6FE11C4FBF09995B3AD2AE65E3F4B169696335CB079BA4B465EFC546E864475322C900F340815BBCEC7F1392063A378CDA1AF2DA9389CEF0DF4C747D8CC9271D0A7BEE42C4D0AF2132BED2F96B23ABB954D3298D4936BFE9A47CFACA31887C3CEBCAEB99443F8BA4136FA93C04B6963FE2F0B4B3296E224FECB82945080B03996EC82F0217346C8D75DA56CAD2B861BFAF763D3C647A157C4221CB3FAE7FA55FC45042675A2991465656C43D5DB2F910B33ED496201638BD67771CD5681ABD628C55A920B22B57E670B13A11CC81591D080712B04F04EDD507AA115057CDF2783AF7B782DC3F857A757A90BFF88D050D907A16ED613DB7041B1D4520BD523D9E44A6880B7030A6CEDA73508366B24F0A53DBD1A36D8253D69F602591C15461D1A7';
		
		let jsCrypto;
		
		let jsCryptoPromise = jsCryptoFactory.create(privateKey, certificate).then((jc) => {
			jsCrypto = jc;
		});
		
		$delegate.use = (backend) => {
			return $q.when(true);
		};
		
		$delegate.getCertificate = (options) => {
			return $q.when({
				encoded: jsCrypto.certificateAsUint8Array(),
				hex: jsCrypto.certificateAsHex()
			});
		};
		
		$delegate.sign = (certificate, data, options) => {
			return $q((resolve, reject) => {
				jsCrypto.sign({'data': data.data}).then((signature) => {
					resolve({
						hex: signature.asHex(),
						value: signature.asUint8Array()
					});
				}, (error) => {
					reject(error);
				});
			});
		};
		
		return $delegate;
	}];

	mockCryptoModule.config(['$provide', ($provide) => {
		$provide.decorator('app.certification.CryptoService', mockCrypto);
	}]);

	mockCryptoModule.run(['app.certification.CryptoService', (cryptoService) => {
		// É necessário injetar esse serviço para que o decorator acima seja executado corretamente.
	}]);
}

export default mockCryptoModuleDefineFunction;