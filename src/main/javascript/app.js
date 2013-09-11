angular.module('libraryApp', [ 'library.services', 'library.controllers', 'library.filters', 'library.directives' ])

.config(function($routeProvider) {
  $routeProvider.when('/login', {
    controller : 'loginCtrl',
    templateUrl : 'partials/login.html'

  }).when('/books', {
    controller : 'bookListCtrl',
    templateUrl : 'partials/book-list.html'

  }).when('/books/new', {
    controller : 'bookDetailCtrl',
    templateUrl : 'partials/book-detail.html'

  }).when('/books/:bookId', {
    controller : 'bookDetailCtrl',
    templateUrl : 'partials/book-detail.html'

  }).when('/signup', {
    controller : 'signUpCtrl',
    templateUrl : 'partials/signup.html'
      
  }).otherwise({
    redirectTo : '/books'
  });

})

/**
 * Configure HTTP interceptors
 */
.config(function($httpProvider) {
	
	/**
	 * Error interceptor, catch all for unhandled http errors
	 * displays global error dialog from index.html
	 */
    function errorInterceptor($q, $log) {
        function success(response) {
            return response;
        }
        function error(response) {
            if (_.contains([ 400, 420, 404, 415, 500 ], response.status)) {
                $('#system-error-dialog').modal().on('hidden.bs.modal', function() {
                    window.location = '#/books';
                });
            }
            return $q.reject(response);
        }
        return function(promise) {
            return promise.then(success, error);
        };
    }

    /**
     * Auth Interceptor, redirects to login screen when 403
     * returned from server
     */
    function authInterceptor($q, $log, $location) {
        function success(response) {
            return response;
        }
        function error(response) {
            if (response.status == 403) {
                $location.path('/login');
            }
            return $q.reject(response);
        }
        return function(promise) {
            return promise.then(success, error);
        };
    }

    $httpProvider.responseInterceptors.push(errorInterceptor);
    $httpProvider.responseInterceptors.push(authInterceptor);

});


/**
 *  initialise other modules 
 */
angular.module('library.services', [ 'ngResource' ]);
angular.module('library.directives', []);
angular.module('library.controllers', []);
angular.module('library.filters', []);



