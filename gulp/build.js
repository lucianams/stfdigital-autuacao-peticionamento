'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')({
    pattern: ['gulp-*', 'uglify-save-license', 'del']
});

gulp.task('html', ['inject'], function ()
{

    var htmlFilter = $.filter('*.html', {restore: true});
    var jsFilter = $.filter('**/*.js', {restore: true});
    var cssFilter = $.filter('**/*.css', {restore: true});
    var assets;
    
    return gulp.src('bundle.conf.js')
    .pipe($.bundleAssets())
    //.pipe($.rev())
//    .pipe(jsFilter)
//    .pipe($.sourcemaps.init())
//    .pipe($.ngAnnotate())
//    .pipe($.uglify({preserveComments: $.uglifySaveLicense})).on('error', conf.errorHandler('Uglify'))
//    .pipe($.sourcemaps.write('maps'))
//    .pipe(jsFilter.restore)
//    .pipe($.revReplace())
    .pipe(gulp.dest(path.join(conf.paths.dist, '/')))
    /*.pipe($.size({
        title    : path.join(conf.paths.dist, '/'),
        showFiles: true
    }))*/;

});

gulp.task('other', function ()
{
//    var fileFilter = $.filter(function (file)
//    {
//        return file.stat.isFile();
//    });
//
//    return gulp.src([
//            path.join(conf.paths.src, '/**/*'),
//            path.join('!' + conf.paths.src, '/**/*.{html,css,js,scss}')
//        ])
//        .pipe(fileFilter)
//        .pipe(gulp.dest(path.join(conf.paths.dist, '/')));
	return;
});

gulp.task('clean', ['clean-ts'], function ()
{
    return $.del([path.join(conf.paths.dist, '/'), path.join(conf.paths.tmp, '/')]);
});

gulp.task('build', ['html', 'other']);