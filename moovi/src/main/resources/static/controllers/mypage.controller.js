(function () {
    angular
        .module("MooviApp")
        .controller("MyPageController", MyPageController);

    function MyPageController($scope) {
        var vm = this;

        $scope.uT = localStorage.getItem("userType");
        $scope.f = localStorage.getItem("DirectedUserName");
        $scope.d = localStorage.getItem("DirectedUserType");

        console.log("d:"+$scope.d);
        console.log("f:"+$scope.f);
        console.log("uT:"+$scope.uT);
    }
})();