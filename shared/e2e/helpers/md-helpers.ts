
class MdHelpers {
	selectOptionWithText(select: protractor.ElementFinder, text: string): void {
        select.click();
        this.waitOptionsRendered(select);
        this.selectOption(select, text);
    }

    multipleSelectOptionsWithText(select: protractor.ElementFinder, texts: string[]): void {
        select.click();
        this.waitOptionsRendered(select);
        for (let text of texts) {
            this.selectOption(select, text);
        }
		browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
    }

    private selectOption(select: protractor.ElementFinder, text): void {
        select.getAttribute('aria-owns').then((selectContainerId) => {
            element(by.id(selectContainerId)).all(by.css('md-option')).all(by.cssContainingText('div', text)).get(0).click();
        });
    }

    private waitOptionsRendered(select: protractor.ElementFinder): void {
        browser.wait(() => {
            return select.getAttribute('aria-owns').then((selectContainerId) => {
                return element(by.id(selectContainerId)).isDisplayed();
            });
        });
    }
}

let mdHelpers = new MdHelpers();

export = mdHelpers;