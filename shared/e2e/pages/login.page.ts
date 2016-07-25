import ElementFinder = protractor.ElementFinder;

import helpers = require('protractor-helpers');

export class LoginPage {
    
    private usernameInput: ElementFinder = element(by.model('vm.form.usuario'));
    private passwordInput: ElementFinder = element(by.model('vm.form.senha'));
    private submitButton: ElementFinder = element(by.css('button[type="submit"]'));

	constructor() {
		
	}
    
    public open(): void {
        browser.get('/login');
    }
    
    public login(username, password): void {
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.submitButton.click();
        //helpers.waitForElementToDisappear(this.submitButton);
    }
}