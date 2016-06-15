'use strict';

var gulp = require('gulp');
var $ = require('gulp-load-plugins')();

var conf = require('./conf');

gulp.task('bower:install:unit', function() {
	return $.bower({cwd: conf.paths.unit});
});

gulp.task('bower:prune:unit', ['bower:install:unit'], function() {
	return $.bower({ cwd: conf.paths.unit, cmd: 'prune'});
});
