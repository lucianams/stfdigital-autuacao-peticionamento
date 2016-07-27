import mockMessagingModule from '../mocks/messaging';

function prepare() {
    console.info('Preparando ambiente para o teste');
    addMockModules();
    extendProtractor();
}

function addMockModules() {
    browser.addMockModule('e2e.mocks.messaging', mockMessagingModule);
}

function extendProtractor() {
    extendWebElementClick();
}

/**
 * Extende o click do WebElement para fazer o scroll
 * automático para ele, caso o elemento não esteja visível.
 */
function extendWebElementClick() {
    (function(click) {
        protractor.WebElement.prototype.click = function() {
            var scrollIntoView = function() {
                arguments[0].scrollIntoView();
            };
            return this.isDisplayed().then((isVisible) => {
                if (isVisible) {
                    return click.call(this);
                } else {
                    return browser.executeScript(scrollIntoView, this).then(() => {
                        return click.call(this);
                    });
                }
            });
        };
    }(protractor.WebElement.prototype.click));
}

export = prepare;