'use strict';

var path = require('path');
var fs = require('fs');
var conf = require('./../../../gulp/conf');

var _ = require('lodash');
var wiredep = require('wiredep');

// List all subdirectories in a directory in Node.js recursively in a synchronous fashion, excluding a folder
function subdirs(dir, exclude, dirList) {
	var files = fs.readdirSync(dir);
	var dirList = dirList || [];
	files.forEach(function(file) {
    if (fs.statSync(dir + '/' + file).isDirectory()) {
      if (file != exclude) {
        dirList.unshift(dir + '/' + file);
      }
      dirList = subdirs(dir + '/' + file, exclude, dirList);
    }
	});
	return dirList;
}

function bundleDirPatterns() {
  return subdirs(conf.paths.app, 'i18n').map(function(dir) {
	  return path.relative(conf.paths.app, dir).replace(/\\/g,"/") + '/*';
  });
}

function listIncludeFiles() {
	var wiredepOptions = _.extend({}, conf.wiredep, {
	    dependencies: true,
	    devDependencies: true
	});

	var patterns = wiredep(wiredepOptions).js.map(function(pathz) {
    return '.' + path.resolve(pathz).replace(path.resolve(conf.paths.root), '').replace(/\\/g,"/");
  });
	
	return patterns;
}

function listFiles() {
  var patterns = listIncludeFiles();
  
  var files = patterns.map(function(pattern) {
    return {
      pattern: pattern,
      included: false
    };
  });
  
  files.push({
    pattern: path.join(conf.paths.dist, '**/*.js'),
    included: false,
    watched: true
  });
  
  files.push({
    pattern: path.join(conf.paths.unit, 'build/**/*.js'),
    included: true,
    watched: true
  });
  
  return files;
}
module.exports = function(config) {
  var configuration = {
    files: listFiles(),

    singleRun: true,
    
    basePath: '../../..',

    autoWatch: false,
    
    autoWatchBatchDelay: 2000,

    logLevel: 'info',

    frameworks: ['systemjs', 'jasmine'],

    browsers : ['PhantomJS'],

    plugins : [
      'karma-systemjs',
      'karma-phantomjs-launcher',
      'karma-chrome-launcher',
      'karma-coverage',
      'karma-jasmine',
	    'karma-html-reporter',
	    'karma-mocha-reporter'
    ],

    coverageReporter: {
      reporters: [{
        type: 'json',
        subdir: '.',
        dir: path.join(conf.paths.unit, 'coverage/js'), 
        file: 'coverage.json'
      }, {
        type : 'html',
        dir : path.join(conf.paths.unit, 'coverage/js')
      }]
    },

    reporters: ['mocha', 'html', 'coverage'],

    htmlReporter : {
		  outputDir : path.join(conf.paths.unit, 'results/html')
	  },
    
    systemjs: {
    	configFile:  path.join(conf.paths.test, 'system.conf.js'),
    	serveFiles: [path.join(conf.paths.dist, '**/*.js'), path.join(conf.paths.dist, 'maps/**/*.js.map'),
                  path.join(conf.paths.unit, 'build/**/*.js.map'), 'node_modules/systemjs/**/*.js', 'node_modules/systemjs/**/*.js.map'],
    	includeFiles: listIncludeFiles(),
      config: {
        bundles: {
          'public/bundle': bundleDirPatterns()
        }
      }
    },

    proxies: {
      '/base/public/': '/base/src/main/resources/public/'
    }
  };

  config.set(configuration);
};
