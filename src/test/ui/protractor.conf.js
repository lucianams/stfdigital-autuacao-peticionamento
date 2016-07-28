'use strict';

var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
var SpecReporter = require('jasmine-spec-reporter');

var conf = require('./../../../gulp/conf');
var path = require('path');

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

  baseUrl: 'https://docker:8443',

  // Spec patterns are relative to the current working directory when
  // protractor is called.
  specs: [path.join(conf.paths.e2e, 'build/**/{pattern}.scenario.js')],

  // Options to be passed to Jasmine-node.
  jasmineNodeOpts: {
    includeStackTrace: true,
	  showColors: true,
	  defaultTimeoutInterval: 40000,
	  print: function() {}
  },
  
  onPrepare: function() {
    browser.driver.manage().window().maximize();
    
    var prepare = require('./e2e/build/shared/prepare/main');
    prepare();

    return browser.getCapabilities().then(function() {
  	  jasmine.getEnv().addReporter(new SpecReporter({displayStacktrace: 'all'}));
  	  jasmine.getEnv().addReporter(new Jasmine2HtmlReporter({
  		  savePath : path.join(conf.paths.e2e, 'results/'),
  		  screenshotsFolder: 'screenshots',
  		  takeScreenshots: true,
  		  takeScreenshotsOnlyOnFailures: true
      }));
	  });
  }
};
