describe('app', function() {
  
  var $httpBackend = null;

  beforeEach(module('libraryApp'));

  beforeEach(inject(function(_$httpBackend_) {
    $httpBackend = _$httpBackend_;
  }));

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  it('should show error dialog for 500 response', inject(function($http) {
      
      $httpBackend.when('GET', 'api/books/1').respond(500, {});

      spyOn($.fn, 'modal').andReturn({on:function() {}});
      
      $http.get('api/books/1');

      $httpBackend.flush();
      
      expect($.fn.modal).toHaveBeenCalled();

  }));
  
  it('should show books list for 403', inject(function($location, $http) {
    
      $httpBackend.when('GET', 'api/books/1').respond(403, {});

      spyOn($location, 'path');
      
      $http.get('api/books/1');

      $httpBackend.flush();
      
      expect($location.path).toHaveBeenCalledWith('/books');
    
    
  }));
  
});
