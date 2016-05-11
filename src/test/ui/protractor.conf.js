'use strict';

var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
var SpecReporter = require('jasmine-spec-reporter');

// An example configuration file.
exports.config = {
  // The address of a running selenium server.
  // seleniumAddress: 'http://localhost:4444/wd/hub',
  // seleniumServerJar: deprecated, this should be set on
  // node_modules/protractor/config.json

  // Capabilities to be passed to the webdriver instance.
  capabilities: {
	  'browserName': 'chrome'
  },
  
  framework: 'jasmine2',
  
  rootElement: 'html',
  
  seleniumArgs : [
      '-browserTimeout=60' 
  ],

  baseUrl: 'http://localhost:3000',

  // Spec patterns are relative to the current working directory when
  // protractor is called.
  specs: ['src/ui/test/**/*.js'],

  // Options to be passed to Jasmine-node.
  jasmineNodeOpts: {
    includeStackTrace: true,
	  showColors: true,
	  defaultTimeoutInterval: 40000,
	  print: function() {}
  },
  
  onPrepare: function() {
	  browser.driver.manage().window().maximize();
	  return browser.getCapabilities().then(function() {
		  jasmine.getEnv().addReporter(new SpecReporter({displayStacktrace: 'all'}));
		  jasmine.getEnv().addReporter(new Jasmine2HtmlReporter({
			  savePath : 'src/test/ui/e2e/results/',
			  screenshotsFolder: 'screenshots',
			  takeScreenshots: true,
			  takeScreenshotsOnlyOnFailures: true
		  }));
	  });
  }
};
