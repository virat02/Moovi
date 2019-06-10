(function () {
    angular
        .module("MooviApp")
        .controller("AdminController", AdminController);

    function AdminController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
    }
})();