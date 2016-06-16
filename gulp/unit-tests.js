'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');
var argv = require('yargs').argv;

var karma = require('karma');
var runSequence = require('run-sequence');

function runTests(singleRun, done)
{
    var localConfig = {
        configFile   : path.resolve(path.join(conf.paths.test, '/karma.conf.js')),
        singleRun    : singleRun,
        autoWatch    : !singleRun
    };

    if (argv.browsers) {
        localConfig.browsers = argv.browsers.split(',');
    }

    var server = new karma.Server(localConfig, function (failCount)
    {
        done(failCount ? new Error("Failed " + failCount + " tests.") : null);
    })
    server.start();
}

gulp.task('test:unit', function (done)
{
    runSequence('bower:install:unit', ['compile-ts:unit', 'scripts'], function() {
        runTests(true, done)
    });
});

gulp.task('tdd', function (done)
{
    runSequence('bower:install:unit', ['clean-and-watch-tests', 'watch'], function() {
        runTests(false, done);
    });
});

gulp.task('clean-and-watch-tests', function(done) {
	runSequence('clean-ts:unit', 'watch-unit', done);
});

gulp.task('watch-unit', ['compile-ts:unit'], function() {
    gulp.watch(path.join(conf.paths.unit, 'app/**/*.ts'), ['compile-ts:unit']);
});