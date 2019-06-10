(function () {
    angular
        .module("MooviApp")
        .controller("FanFollowingController", FanFollowingController);

    function FanFollowingController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/follow/fan/";
        var url2 = "/fansfollowing";

        $scope.usT = localStorage.getItem("userType");
        $scope.fanname = localStorage.getItem("username");
        $scope.fanname1 = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT === 'admin') {
                $http
                    .get(localpath + url1 + localStorage.getItem("DirectedUserName") + url2)
                    .then(function (response) {
                        $scope.allFansHeading = "All Fans " + localStorage.getItem("DirectedUserName") + " Follows";
                        $scope.fans = response.data;
                        console.log(response);
                    })
            }
            else {

                $http
                    .get(localpath + url1 + localStorage.getItem('username') + url2)
                    .then(function (response) {
                        $scope.allFansHeading = "All Fans You Follow";
                        $scope.fans = response.data;
                        console.log(response);
                    })
            }
        });

        var unfollowURL;
        if ($scope.usT === 'admin') {
            unfollowURL = "api/delete/following/fanfollowing/"+$scope.fanname1+"/fanfollowed/";
        }
        else {
            unfollowURL = "api/delete/following/fanfollowing/"+$scope.fanname+"/fanfollowed/";
        }

        vm.unfollowFan = unfollowFan;

        function unfollowFan(fanUsername) {
            $http
                .post(localpath + unfollowURL + fanUsername)
                .then(function (response) {
                    $scope.allFansHeading = "All Fans You Follow";
                    $scope.fans = response.data;
                    alert("Fan unfollowed");
                    location.reload(true);
                })
        }
    }
})();