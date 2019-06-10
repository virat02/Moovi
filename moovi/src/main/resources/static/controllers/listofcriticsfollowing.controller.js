(function () {
    angular
        .module("MooviApp")
        .controller("CriticFollowingController", CriticFollowingController);

    function CriticFollowingController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/follow/fan/";
        var url2 = "/criticfollowed";

        $scope.usT = localStorage.getItem("userType");
        $scope.criticname = localStorage.getItem("username");
        $scope.criticname1 = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function () {

            if ($scope.usT === 'admin') {
                $http
                    .get(localpath + url1 + localStorage.getItem("DirectedUserName") + url2)
                    .then(function (response) {
                        $scope.allCriticsHeading = "All Critics " + localStorage.getItem("DirectedUserName") + " Follows";
                        $scope.critics = response.data;
                        console.log(response);
                    })
            }
            else {

                $http
                    .get(localpath + url1 + localStorage.getItem("username") + url2)
                    .then(function (response) {
                        $scope.allCriticsHeading = "All Critics You Follow";
                        $scope.critics = response.data;
                        console.log(response);
                    })
            }
        });

        var unfollowURL;
        if ($scope.usT === 'admin') {
            unfollowURL = "api/delete/unfollow/fan/" + $scope.criticname1 + "/critic/";
        }
        else {
            unfollowURL = "api/delete/unfollow/fan/" + $scope.criticname + "/critic/";
        }

        vm.unfollowCritic = unfollowCritic;

        function unfollowCritic(criticUsername) {
            $http
                .post(localpath + unfollowURL + criticUsername)
                .then(function (response) {
                    $scope.allCriticsHeading = "All Critics You Follow";
                    $scope.critics = response.data;
                    alert("Critic unfollowed");
                    location.reload(true);
                })
        }

        vm.showRecommendedMovies = showRecommendedMovies;

        function showRecommendedMovies(criticUsername) {

            localStorage.setItem("criticClicked",criticUsername);
            window.location = "#!/showListOfRecommendedMovies"
        }

    }
})();