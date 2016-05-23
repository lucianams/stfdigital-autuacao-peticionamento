"use strict";
var LoginPage = (function () {
    function LoginPage() {
        this.usernameInput = element(by.model('vm.form.usuario'));
        this.passwordInput = element(by.model('vm.form.senha'));
        this.submitButton = element(by.css('button[type="submit"]'));
    }
    LoginPage.prototype.open = function () {
        browser.get('/login');
    };
    LoginPage.prototype.setCredenciais = function (username, password) {
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.submitButton.click();
    };
    return LoginPage;
}());
exports.LoginPage = LoginPage;
