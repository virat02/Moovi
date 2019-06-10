(function () {
    angular
        .module("MooviApp")
        .controller("TheatresManagedController", TheatresManagedController);

    function TheatresManagedController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/manager/";
        var url2 = "/theatremanaged";

        $scope.usT = localStorage.getItem("userType");
        $scope.theatremanagername = localStorage.getItem("username");
        $scope.theatremanagername1 = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT === 'admin') {
                    $http
                        .get(localpath + url1 + localStorage.getItem("DirectedUserName") + url2)
                        .then(function (response) {
                            $scope.allTheatresHeading = "All Theatres that " + localStorage.getItem("DirectedUserName")+ " manages";
                            $scope.theatres = response.data;
                            console.log(response);
                        })
                }
                else {

                    $http
                        .get(localpath + url1 + localStorage.getItem("username") + url2)
                        .then(function (response) {
                            $scope.allTheatresHeading = "All Theatres You Manage";
                            $scope.theatres = response.data;
                            console.log(response);
                        })
                }

        });

        var unregisterURL = "api/delete/theatremanager/";
        var url3 = "/theatre/";
        var url4;

        if ($scope.usT === 'admin') {
            url4 = $scope.theatremanagername1;
        }
        else {
            url4 = $scope.theatremanagername;
        }

        vm.unRegisterTheatre = unRegisterTheatre;

        function unRegisterTheatre(theatreId) {
            $http
                .post(localpath+unregisterURL+url4+url3+theatreId)
                .then(function (response) {
                    $scope.allTheatresHeading = "All Theatres You manage";
                    $scope.theatres = response.data;
                    alert("Theatre unregistered");
                    location.reload(true);
                })
        }
    }
})();