'use strict';

var gulp = require('gulp');
var conf = require('./conf');
var del = require('del');
var runSequence = require('run-sequence');

gulp.task('clean', function() {
    return del(conf.paths.dist);
});

gulp.task('build', function(done) {
	runSequence('clean', 'scripts', done);
});