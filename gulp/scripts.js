'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');
var custom = require('./custom');

var $ = require('gulp-load-plugins')();

var tsProject = $.typescript.createProject(path.join(conf.paths.src, 'tsconfig.json'), {out: 'bundle.js'});

var processTS = function() {
	return gulp.src([path.join(conf.paths.app, '**/*.ts'), 
                     path.join(conf.paths.src, 'typings/main/**/*.d.ts')])
               .pipe($.angularEmbedTemplates({
                 		skipErrors: true, 
                 		minimize: {
                 			empty : true,
                 			spare : true,
                 			quotes: true
                 		}
                 	}))
               .pipe($.sourcemaps.init())
               .pipe($.typescript(tsProject));
}

var scriptsDev = function(js) {
	return js.pipe($.ngAnnotate())
			 .pipe($.sourcemaps.write("maps", {sourceRoot: conf.paths.app})) // sourceRoot com caminho relativo para funcionar com o remap-istanbul. Caminho corresponde ao caminho do github para o coveralls detectar o fonte na cobertura.
			 .pipe($.size())
			 .pipe(gulp.dest(conf.paths.dist));
};

var scripts = function(js) {
	return js.pipe($.ngAnnotate())
			 .pipe($.uglify())
			 .pipe($.sourcemaps.write("maps", {sourceRoot: conf.paths.app})) // sourceRoot com caminho relativo para funcionar com o remap-istanbul. Caminho corresponde ao caminho do github para o coveralls detectar o fonte na cobertura.
			 .pipe($.size())
			 .pipe(gulp.dest(conf.paths.dist));
};

gulp.task('json', function() {
    
    return gulp.src(path.join(conf.paths.app, custom.project, '**/*.json'))
        .pipe($.destClean(conf.paths.dist))
        .pipe(gulp.dest(conf.paths.dist));
});

gulp.task('scripts', ['ts-lint', 'json'], function() {
    return scripts(processTS().js);
});

gulp.task('scripts:dev', ['ts-lint', 'json'], function() {
    return scriptsDev(processTS().js);
});
