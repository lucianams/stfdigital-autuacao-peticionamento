'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');
var argv = require('yargs').argv;

var protractorConfig = require(path.join('..', conf.paths.test, 'protractor.conf'));

var $ = require('gulp-load-plugins')();
var runSequence = require('run-sequence');

// Downloads the selenium webdriver
gulp.task('webdriver-update', $.protractor.webdriver_update);

gulp.task('webdriver-standalone', $.protractor.webdriver_standalone);

var replacePattern = function(source) {
	var pattern = "*"; // Pattern padrão
	if (argv.pattern) {
		pattern = argv.pattern;
	}
	return source.map(function(val) {
		return val.replace("{pattern}", pattern);
	});
};

function runProtractor(done)
{
	protractorConfig.config.specs = replacePattern(protractorConfig.config.specs);
	
	var protractorConfigObject = {
        configFile: path.join(conf.paths.test, '/protractor.conf.js'),
        args      : []
    };
	if (argv.e2eBaseUrl) {
		protractorConfigObject.args.push('--baseUrl');
		protractorConfigObject.args.push(argv.e2eBaseUrl);
	}
	if (argv.e2eSeleniumAddress) {
		protractorConfigObject.args.push('--seleniumAddress');
		protractorConfigObject.args.push(argv.e2eSeleniumAddress);
	}
	if (argv.e2eFilesDirPath) {
		protractorConfigObject.args.push('--params.filesDirPath');
		protractorConfigObject.args.push(argv.e2eFilesDirPath);
	}
	
    gulp.src(protractorConfig.config.specs)
        .pipe($.protractor.protractor(protractorConfigObject))
        .on('error', function (err) {
            // Make sure failed tests cause gulp to exit non-zero
            throw err;
        })
        .on('end', function () {
            done();
        });
}

gulp.task('test:e2e', function(done) {
	runSequence('clean-typings:e2e', 'protractor', done);
});
gulp.task('protractor', ['webdriver-update', 'compile-ts:e2e'], runProtractor);
