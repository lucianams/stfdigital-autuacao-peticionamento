namespace support {
    export function printBrowserLogs() {
        browser.manage().logs().get("browser").then(function(browserLogs) {
            browserLogs.forEach(function(log){
                console.log(log.message);
            });
        });
    }

    export function scrollToElement(element: protractor.ElementFinder): webdriver.promise.Promise<{}> {
        let scrollIntoView = function() {
            arguments[0].scrollIntoView();
        };
        return browser.wait(browser.executeScript(scrollIntoView, element.getWebElement()));
    }
}

export = support;