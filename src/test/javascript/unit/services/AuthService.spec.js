describe('authService', function() {

  beforeEach(module('library.services'));

  it('should not be logged in', inject(function(authService) {
    expect(authService.isLoggedIn()).toBe(false);

  }));

  it('should be logged in', inject(function(authService, $http) {
    authService.login({
      email : 'nmo@zuhlke.com',
      name : 'Me'
    }, 'password');
    expect(authService.isLoggedIn()).toBe(true);
    expect(authService.getFullName()).toBe('Me');
    expect($http.defaults.headers.common['Authorization']).toBe('Basic bm1vQHp1aGxrZS5jb206cGFzc3dvcmQ=');

  }));

  it('should be logged out', inject(function(authService, $http) {
    authService.login({
      name : 'Me'
    }, 'password');
    expect(authService.isLoggedIn()).toBe(true);

    authService.logout();
    expect(authService.isLoggedIn()).toBe(false);
    expect($http.defaults.headers.common['Authorization']).toBeUndefined();

  }));

  it('should be admin', inject(function(authService) {
    authService.login({
      name : 'Me',
      role : 'ADMINISTRATOR'
    }, 'password');
    expect(authService.isAdmin()).toBe(true);

  }));

  it('should not be admin', inject(function(authService) {
    authService.login({
      name : 'Me',
      role : 'USER'
    }, 'password');
    expect(authService.isAdmin()).toBe(false);

  }));

});
