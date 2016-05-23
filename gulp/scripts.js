'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')();

function buildScripts()
{
    return gulp.src(path.join(conf.paths.src, '/main/resources/public/**/*.js'))
        .pipe($.jshint())
        .pipe($.jshint.reporter('jshint-stylish'))
        .pipe($.size())
};

gulp.task('scripts', ['compile-ts'], function ()
{
    return buildScripts();
});