namespace mdHelpers {

    export namespace mdSelect {

        export function selectOptionWithText(select: protractor.ElementFinder, text: string): void {
            select.click();
            waitOptionsRendered(select);
            selectOption(select, text);
        }

        export function selectMultipleOptionsWithText(select: protractor.ElementFinder, texts: string[]): void {
            select.click();
            waitOptionsRendered(select);
            for (let text of texts) {
                selectOption(select, text);
            }
            browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
        }

        function waitOptionsRendered(select: protractor.ElementFinder): void {
            browser.wait(() => {
                return select.getAttribute("aria-owns").then((selectContainerId) => {
                    return element(by.id(selectContainerId)).isDisplayed();
                });
            });
        }

        function selectOption(select: protractor.ElementFinder, text: string): void {
            select.getAttribute("aria-owns").then((selectContainerId) => {
                element(by.id(selectContainerId)).all(by.css("md-option"))
                        .all(by.cssContainingText("div", text)).get(0).click();
            });
        }

    }

    export namespace mdAutocomplete {

        export function selectFirstOptionWithText(autocomplete: protractor.ElementFinder, text: string): void {
            autocomplete.click();
            let autocompleteInput = autocomplete.element(by.css("md-autocomplete-wrap input"));
            autocompleteInput.sendKeys(text);
            waitOptionsRendered(autocompleteInput);
            selectFirstOption(autocompleteInput, text);
        }

        function waitOptionsRendered(autocompleteInput: protractor.ElementFinder): void {
            browser.wait(() => {
                return autocompleteInput.getAttribute("aria-owns").then((ulId) => {
                    return element(by.id(ulId)).isDisplayed();
                });
            });
        }

        function selectFirstOption(autocompleteInput: protractor.ElementFinder, text: string): void {
            autocompleteInput.getAttribute("aria-owns").then((ulId) => {
                element(by.id(ulId)).all(by.css("li [md-autocomplete-replace]")).first().click();
            });
        }

    }

}

export = mdHelpers;