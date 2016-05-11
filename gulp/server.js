'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var browserSync = require('browser-sync');
var browserSyncSpa = require('browser-sync-spa');

var util = require('util');

function browserSyncInit()
{   
    var proxy = {
		target: "http://docker:8080",
		ws: true
	};

    browserSync.instance = browserSync.init({
        startPath: '/',
        proxy : proxy,
        open : false
    });
}

browserSync.use(browserSyncSpa({
    selector: '[ng-app]'// Only needed for angular apps
}));

gulp.task('serve:e2e', ['compile-e2e-ts'], function ()
{
    browserSyncInit();
});