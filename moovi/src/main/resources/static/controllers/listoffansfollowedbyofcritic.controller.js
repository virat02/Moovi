(function () {
    angular
        .module("MooviApp")
        .controller("FansFollowedController", FansFollowedController);

    function FansFollowedController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/follow/critic/";
        var url2 = "/fanfollowing";

        $scope.usT = localStorage.getItem("userType");
        $scope.criticname = localStorage.getItem("username");
        $scope.criticname1 = localStorage.getItem("DirectedUserName");

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

        var unfollowURL = "api/delete/unfollow/critic/";
        var url3 = "/fan/";
        var url4;

        if ($scope.usT === 'admin') {
            url4 = $scope.criticname1;
        }
        else {
            url4 = $scope.criticname;
        }

        vm.unfollowFan = unfollowFan;

        function unfollowFan(fanUsername) {
            $http
                .post(unfollowURL+url4+url3+fanUsername)
                .then(function (response) {
                    $scope.allFansHeading = "All Fans Who Follow You";
                    $scope.fans = response.data;
                    alert("Fan unfollowed");
                    location.reload(true);
                })
        }

    }
})();