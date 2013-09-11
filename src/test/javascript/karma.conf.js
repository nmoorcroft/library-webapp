// Karma configuration
// Generated on Tue Aug 13 2013 14:26:58 GMT+0100 (GMT Daylight Time)

module.exports = function(config) {
	config.set({

		// base path, that will be used to resolve files and exclude
		basePath = '../../../src/main/resources/assets/';

		// frameworks to use
		frameworks : [ 'jasmine' ],

		// list of files / patterns to load in the browser
		files : [  
		 // libraries
		 'js/underscore-min.js',
		 'js/jquery-*.js',
		 'js/jquery.ui.widget.js',
		 'js/jquery.iframe-transport.js',
		 'js/jquery.fileupload.js',
		 'js/angular.min.js',
		 'js/angular-*.js',
		 'js/base64.js',
		 'js/bootstrap.min.js',
		 'js/bootstrap.file-input.js',
		
		 // mocks
		 '../../../../src/test/javascript/lib/angular-mocks.js',
		 
		 // application
		 '../../../../src/main/javascript/**/*.js',
		 
		 // templates
		 'partials/*.html',
		 
		 // tests
		 '../../../../src/test/javascript/unit/**/*.js',

		],

		// list of files to exclude
		exclude : [

		],

		preprocessors : {
			'js/*.js' : 'coverage',
		},

		// test results reporter to use
		// possible values: 'dots', 'progress', 'junit', 'growl', 'coverage'
		reporters : [ 'progress', 'coverage', 'junit' ],

		coverageReporter : {
			type : 'html',
  		  dir : '../../../../target/karma-coverage/'
		},

		junitReporter : {
			  outputFile: '../../../../target/karma-test-results.xml'
		},

		// web server port
		port : 9876,

		// enable / disable colors in the output (reporters and logs)
		colors : true,

		// level of logging
		// possible values: config.LOG_DISABLE || config.LOG_ERROR ||
		// config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
		logLevel : config.LOG_INFO,

		// enable / disable watching file and executing tests whenever any file
		// changes
		autoWatch : true,

		// Start these browsers, currently available:
		// - Chrome
		// - ChromeCanary
		// - Firefox
		// - Opera
		// - Safari (only Mac)
		// - PhantomJS
		// - IE (only Windows)
		browsers : [ 'PhantomJS' ],

		// If browser does not capture in given timeout [ms], kill it
		captureTimeout : 60000,

		// Continuous Integration mode
		// if true, it capture browsers, run tests and exit
		singleRun : false,
		
		//transports : ['flashsocket', 'xhr-polling', 'jsonp-polling']

	});
};



