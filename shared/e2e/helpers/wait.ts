
class WaitHelpers {
    public waitForUrl(url: string) {
        return browser.wait(function() {
            return browser.getCurrentUrl().then(function(currentUrl) {
                return currentUrl.lastIndexOf(url) !== -1;
            });
        });
    }
}

let waitHelpers = new WaitHelpers();

export default waitHelpers;