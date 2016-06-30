'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

function isOnlyChange(event)
{
    return event.type === 'changed';
}

gulp.task('watch', ['build'], function ()
{
    gulp.watch([path.join(conf.paths.app, '**/*.*')], ['scripts:dev']);
});
