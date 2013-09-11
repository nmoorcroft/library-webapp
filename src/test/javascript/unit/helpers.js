beforeEach(function() {
  this.addMatchers({
    // we need to use toEqualData because the Resource has extra
    // properties which make simple .toEqual not work.
    toEqualData : function(expect) {
      return angular.equals(expect, this.actual);
    }
  });
});
