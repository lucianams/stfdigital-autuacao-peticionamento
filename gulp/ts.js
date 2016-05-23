'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')({
		pattern: ['gulp-*', 'uglify-save-license', 'del']
});

var tsProject = $.typescript.createProject('tsconfig.json');
var tsProjectE2E = $.typescript.createProject(path.join(conf.paths.test, '/tsconfig.json'));
var allTypeScript = path.join(conf.paths.src, '/**/*.ts');
var libraryTypeScript = 'typings/main/**/*.d.ts';
var libraryTypeScriptE2E = path.join(conf.paths.test, '/typings/main/**/*.d.ts');
var tsOutputPath = path.join(conf.paths.src, '/');
var tsGenFiles = path.join(conf.paths.src, '/**/*.js');
var tsGenMapFiles = path.join(conf.paths.src, '/**/*.js.map');
var allTypeScriptE2E = path.join(conf.paths.e2e, '/**/*.ts');
var tsOutputPathE2E = path.join(conf.paths.e2e, '/');

/**
 * Install all typings files
 */
gulp.task('install-typings',function(){
    return gulp.src('typings.json')
        .pipe($.typings());
});

/**
 * Install all e2e typings files
 */
gulp.task('install-e2e-typings',function(){
    gulp.src(path.join(conf.paths.test, '/typings.json'))
        .pipe($.typings());
});

/**
 * Lint all custom TypeScript files.
 */
gulp.task('ts-lint', function () {
    return gulp.src(allTypeScript)
    			.pipe($.tslint())
    			.pipe($.tslint.report('prose'));
});

/**
 * Compile TypeScript and include references to library and app .d.ts files.
 */
gulp.task('compile-ts', ['ts-lint'], function () {
    return gulp.src([allTypeScript, libraryTypeScript])
        .pipe($.sourcemaps.init())
        .pipe($.typescript(tsProject))
        .pipe($.ngAnnotate())
        .pipe($.sourcemaps.write('.'))
        .pipe(gulp.dest(tsOutputPath));
});

/**
 * Compile TypeScript and include references to library and app .d.ts files.
 */
gulp.task('compile-e2e-ts', ['install-e2e-typings', 'ts-lint'], function () {
    return gulp.src([allTypeScriptE2E, libraryTypeScriptE2E])
        .pipe($.typescript(tsProjectE2E))
        .pipe($.ngAnnotate())
        .pipe(gulp.dest(tsOutputPathE2E));
});

/**
 * Remove all generated JavaScript files from TypeScript compilation.
 */
gulp.task('clean-ts', function (cb) {
	$.del([ tsGenFiles, tsGenMapFiles ]).then(function () {
        cb();
    }, function (reason) {
        cb("Failed to delete files " + reason);
    });
});