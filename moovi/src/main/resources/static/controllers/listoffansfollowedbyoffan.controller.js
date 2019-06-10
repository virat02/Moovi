(function () {
    angular
        .module("MooviApp")
        .controller("FansFollowedOfFanController", FansFollowedOfFanController);

    function FansFollowedOfFanController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/follow/fan/";
        var url2 = "/followedby";

        $scope.usT = localStorage.getItem("userType");
        $scope.fanname = localStorage.getItem("username");
        $scope.fanname1 = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT === 'admin') {
                $http
                    .get(localpath + url1 + localStorage.getItem("DirectedUserName") + url2)
                    .then(function (response) {
                        $scope.allFansHeading = "All Fans Who Follow " + localStorage.getItem("DirectedUserName");
                        $scope.fans = response.data;
                        console.log(response);
                    })
            }
            else {

                $http
                    .get(localpath + url1 + localStorage.getItem('username') + url2)
                    .then(function (response) {
                        $scope.allFansHeading = "All Fans Who Follow You";
                        $scope.fans = response.data;
                        console.log(response);
                    })
            }

        });

        var unfollowURL = "api/delete/following/fanfollowing/";
        var url3 = "/fanfollowed/";
        var url4;

        if ($scope.usT === 'admin') {
            url4 = $scope.fanname1;
        }
        else {
            url4 = $scope.fanname;
        }

        vm.unfollowFan = unfollowFan;

        function unfollowFan(fanUsername) {
            $http
                .post(localpath+unfollowURL+fanUsername+url3+url4)
                .then(function (response) {
                    $scope.allFansHeading = "All Fans Who Follow You";
                    $scope.fans = response.data;
                    alert("Fan unfollowed");
                    location.reload(true);
                })
        }

    }
})();