(function () {
    angular
        .module("MooviApp")
        .controller("FanSearchController", FanSearchController);

    function FanSearchController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url = "/api/fan";
        vm.searchFanByUsername = searchFanByUsername;

        $scope.$on('$viewContentLoaded', function () {
            $scope.myVal = false;
            $http
                .get(url)
                .then(function (value) {
                    $scope.allFansHeading = "All Fans";
                    $scope.fans = value.data;
                    console.log(value);
                })

        });

        $scope.myKeyPress = function (keyEvent, username) {
            if (keyEvent.which === 13)
                searchFanByUsername(username);
        };

        function searchFanByUsername(username) {
            var findByName = "?username=" + username;
            $scope.myVal = true;
            $http
                .get(url + findByName)
                .then(function (response) {
                    console.log(response);
                    $scope.fans = response.data;
                });
        }

        vm.followThisFan = followThisFan;

        function followThisFan(fanUsername) {
            var username = localStorage.getItem("username");
            var followFanURL = localpath + "api/follow/fan1/" + username + "/fan2/" + fanUsername;

            $http
                .post(followFanURL)
                .then(function () {
                alert(username + " followed a fan: " + fanUsername);
                });
        }
    }
})();