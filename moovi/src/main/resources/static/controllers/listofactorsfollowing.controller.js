(function () {
    angular
        .module("MooviApp")
        .controller("ActorFollowingController", ActorFollowingController);

    function ActorFollowingController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/follow/fan/";
        var url2 = "/actorfollowed";

        $scope.usT = localStorage.getItem("userType");
        $scope.fanname = localStorage.getItem("username");
        $scope.fanname1 = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT === 'admin') {
                $http
                    .get(localpath+url1+localStorage.getItem("DirectedUserName")+url2)
                    .then(function (response) {
                        $scope.allActorsHeading = "All Actors"+localStorage.getItem("DirectedUserName")+" Follows";
                        $scope.actors = response.data;
                        console.log(response);
                    })
            }
            else {
                $http
                    .get(localpath+url1+localStorage.getItem("username")+url2)
                    .then(function (response) {
                        $scope.allActorsHeading = "All Actors You Follow";
                        $scope.actors = response.data;
                        console.log(response);
                    })
            }

        });
        var unfollowURL;
        if ($scope.usT === 'admin') {
            unfollowURL = "api/delete/unfollow/fan/"+$scope.fanname1+"/actor/";
        }
        else {
            unfollowURL = "api/delete/unfollow/fan/"+$scope.fanname+"/actor/";
        }

        vm.unfollowActor = unfollowActor;

        function unfollowActor(actorId) {
            $http
                .post(localpath+unfollowURL+actorId)
                .then(function (response) {
                    $scope.allActorsHeading = "All Actors You Follow";
                    $scope.actors = response.data;
                    alert("Actor unfollowed");
                    location.reload(true);
                })
        }
    }
})();