(function () {
    angular
        .module("MooviApp")
        .controller("NavbarController", NavbarController);
    
    function NavbarController($scope) {
        var vm = this;
        $scope.typeRole = localStorage.getItem("userType");
        console.log($scope.typeRole);
        $scope.foo = null;

        $scope.getFooUndef = function(foo){
            return ( typeof foo == null );
        };

        vm.resetUserType = resetUserType;
        function resetUserType() {
            localStorage.removeItem("userType");
            window.location.href = "#";
            location.reload(true);
        }
    }
})();