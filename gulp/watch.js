'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

function isOnlyChange(event)
{
    return event.type === 'changed';
}

gulp.task('watch', function ()
{
    gulp.watch([path.join(conf.paths.src, '**/*.ts')], ['compile-ts']);
});