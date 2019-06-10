(function () {
    angular
        .module("MooviApp")
        .controller("ActorDetailsController", ActorDetailsController);

    function ActorDetailsController($http,$scope) {
        $scope.aid = localStorage.getItem("actorId");
        alert($scope.aid);
        var url = "/api/actor/";

        $scope.$on('$viewContentLoaded', function()
        {

            $http
                .get(url+$scope.aid)
                .then(function (value) {
                    $scope.actor = value.data;
                });

            $http
                .get(url+$scope.aid+"/moviesActed")
                .then(function (value) {
                    $scope.movies = value.data;
                    console.log($scope.movies)
                });

        });


    }
})();