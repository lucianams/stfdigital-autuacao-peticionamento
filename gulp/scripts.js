'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')();

gulp.task('scripts', ['ts-lint'], function() {
    return gulp.src(path.join(conf.paths.src, 'bundle.config.js'))
    	.pipe($.bundleAssets())
    	.pipe($.size())
    	.pipe($.destClean(conf.paths.dist))
        .pipe(gulp.dest(conf.paths.dist));
});
