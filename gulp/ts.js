'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');
var custom = require('./custom');

var $ = require('gulp-load-plugins')({
		pattern: ['gulp-*', 'del']
});

var createTsProjectForDefinition = function() {
    return $.typescript.createProject(path.join(conf.paths.src, 'tsconfig.json'), {
    	declaration: true
    });
};
var allTypeScript = path.join(conf.paths.app, '**/*.ts');
var libraryTypeScript = path.join(conf.paths.src, 'typings/main/**/*.d.ts');

var tsProjectE2E = $.typescript.createProject(path.join(conf.paths.e2e, 'tsconfig.json'));
var allTypeScriptE2E = path.join(conf.paths.e2e, 'app/**/*.ts');
var libraryTypeScriptE2E = path.join(conf.paths.e2e, 'typings/main/**/*.d.ts');
var tsOutputPathE2E = path.join(conf.paths.e2e, 'build');

var tsProjectUnit = $.typescript.createProject(path.join(conf.paths.unit, 'tsconfig.json'));
var allTypeScriptUnit = path.join(conf.paths.unit, 'app/**/*.ts');
var libraryTypeScriptUnit = path.join(conf.paths.unit, 'typings/main/**/*.d.ts');
var tsOutputPathUnit = path.join(conf.paths.unit, 'build');
var tsDefOutputPath = path.join(conf.paths.unit, 'node_modules', custom.project);

/**
 * Install all typings files
 */
gulp.task('install-typings', function() {
    return gulp.src('typings.json', {cwd : conf.paths.src})
        .pipe($.typings());
});

/**
 * Install all e2e typings files
 */
gulp.task('install-typings:e2e', function() {
    return gulp.src('typings.json', {cwd : conf.paths.e2e})
        .pipe($.typings());
});

/**
 * Install all unit typings files
 */
gulp.task('install-typings:unit', ['generate-definitions'], function() {
    return gulp.src('typings.json', {cwd : conf.paths.unit})
        .pipe($.typings());
});

/**
 * Lint all custom TypeScript files.
 */
gulp.task('ts-lint', ['install-typings'], function() {
    return gulp.src(allTypeScript)
    			.pipe($.tslint())
    			.pipe($.tslint.report('prose'));
});

/**
 * Lint all custom TypeScript files.
 */
gulp.task('ts-lint:e2e', ['install-typings:e2e'], function() {
    return gulp.src(allTypeScriptE2E)
    			.pipe($.tslint())
    			.pipe($.tslint.report('prose'));
});

/**
 * Lint all custom TypeScript files.
 */
gulp.task('ts-lint:unit', ['install-typings:unit'], function() {
    return gulp.src(allTypeScriptUnit)
    			.pipe($.tslint())
    			.pipe($.tslint.report('prose'));
});

gulp.task('generate-definitions', function() {
	return gulp.src([allTypeScript, libraryTypeScript])
    	.pipe($.typescript(createTsProjectForDefinition()))
    	.dts
    	.pipe($.destClean(tsDefOutputPath))
    	.pipe(gulp.dest(tsDefOutputPath));
});

/**
 * Compile TypeScript and include references to library and app .d.ts files.
 */
gulp.task('compile-ts:e2e', ['ts-lint:e2e'], function() {
    return gulp.src([allTypeScriptE2E, libraryTypeScriptE2E])
        .pipe($.typescript(tsProjectE2E))
        .pipe($.ngAnnotate())
        .pipe(gulp.dest(tsOutputPathE2E));
});

gulp.task('clean-ts:unit', function() {
    return $.del(tsOutputPathUnit);
});

/**
 * Compile TypeScript and include references to library and app .d.ts files.
 */
gulp.task('compile-ts:unit', ['ts-lint:unit'], function() {
    return gulp.src([allTypeScriptUnit, libraryTypeScriptUnit])
        .pipe($.sourcemaps.init())
        .pipe($.typescript(tsProjectUnit))
        .pipe($.ngAnnotate())
        .pipe($.sourcemaps.write('.'))
        .pipe(gulp.dest(tsOutputPathUnit));
});