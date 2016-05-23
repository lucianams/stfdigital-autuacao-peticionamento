import ElementFinder = protractor.ElementFinder;

export class LoginPage {
    
    private usernameInput: ElementFinder = element(by.model('vm.form.usuario'));
    private passwordInput: ElementFinder = element(by.model('vm.form.senha'));
    private submitButton: ElementFinder = element(by.css('button[type="submit"]'));

	constructor() {
		
	}
    
    public open(): void {
        browser.get('/login');
    }
    
    public setCredenciais(username, password): void {
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.submitButton.click();
    }
}