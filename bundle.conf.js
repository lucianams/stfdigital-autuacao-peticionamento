module.exports = {
  bundle: {
    main: {
      scripts: 'src/main/app/**/*.js',
      options: {
          rev: false
      } 	  
    }
  },
  copy: [{
	  src: 'src/main/app/**/*.html',
	  base: 'src/main/app',
	  watch: true
  },
  {
	  src: 'src/main/app/**/*.json',
	  base: 'src/main/app',
	  watch: true
  }]
};