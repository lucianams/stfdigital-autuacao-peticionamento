'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');
var argv = require('yargs').argv;

var karma = require('karma');
var runSequence = require('run-sequence');
var remapIstanbul = require('remap-istanbul/lib/gulpRemapIstanbul');

var $ = require('gulp-load-plugins')();

var pathSrcJs = [
    path.join(conf.paths.dist, '**/*.js')
];

function runTests(singleRun, done, reportCoverage)
{
    var preprocessors = {};
    if (singleRun) {
        pathSrcJs.forEach(function (path)
        {
            preprocessors[path] = ['coverage'];
        });
    }

    var localConfig = {
        configFile   : path.resolve(path.join(conf.paths.test, '/karma.conf.js')),
        singleRun    : singleRun,
        autoWatch    : !singleRun,
        preprocessors: preprocessors
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
    runSequence(['bower:install:unit', 'clean-typings:unit'], ['compile-ts:unit', 'scripts:dev'], function() {
        runTests(true, done)
    });
});

gulp.task('test:unit:coverage', function(done) {
    return runSequence('test:unit', 'remap-istanbul', done);
});

gulp.task('tdd', function (done)
{
    runSequence(['bower:install:unit', 'clean-typings:unit'], ['clean-and-watch-tests', 'watch'], function() {
        runTests(false, done);
    });
});

gulp.task('clean-and-watch-tests', function(done) {
	runSequence('clean-ts:unit', 'watch-unit', done);
});

gulp.task('watch-unit', ['compile-ts:unit'], function() {
    gulp.watch(path.join(conf.paths.unit, 'app/**/*.ts'), ['compile-ts:unit']);
});

gulp.task('remap-istanbul', function () {
    return gulp.src(path.join(conf.paths.unit, 'coverage/js/coverage.json'))
        .pipe(remapIstanbul({
            reports: {
                'json': path.join(conf.paths.unit, 'coverage/ts/coverage.json'),
                'html': path.join(conf.paths.unit, 'coverage/ts/html'),
                'lcovonly': path.join(conf.paths.unit, 'coverage/ts/lcov.info')
            },
            exclude: 'src/main/ui/app/bundle.js'
        }));
});

gulp.task('publish-unit-coverage', function() {
    return gulp.src(path.join(conf.paths.unit, 'coverage/ts/lcov.info'))
        .pipe($.coveralls());
});
