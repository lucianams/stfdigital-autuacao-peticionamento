/**
 *  This file contains the variables used in other gulp files
 *  which defines tasks
 *  By design, we only put there very generic config values
 *  which are used in several places to keep good readability
 *  of the tasks
 */

var gutil = require('gulp-util');
var path = require('path');
var custom = require('./custom');

/**
 *  The main paths of your project handle these with care
 */
exports.paths = {
    root : '.',
    src : './src/main/ui',
    app : './src/main/ui/app',
    dist: './src/main/resources/public',
    test: './src/test/ui',
    e2e : './src/test/ui/e2e',
    unit: './src/test/ui/unit',
    bin: './bin/public',
    tmp: './.tmp'
};

/**
 *  Wiredep is the lib which inject bower dependencies in your project
 *  Mainly used to inject script tags in the index.html but also used
 *  to inject css preprocessor deps and js files in karma
 */
exports.wiredep = {
    cwd: exports.paths.unit,
};

/**
 *  Common implementation for an error handler of a Gulp plugin
 */
exports.errorHandler = function (title)
{
    'use strict';

    return function (err)
    {
        gutil.log(gutil.colors.red('[' + title + ']'), err.toString());
        this.emit('end');
    };
};