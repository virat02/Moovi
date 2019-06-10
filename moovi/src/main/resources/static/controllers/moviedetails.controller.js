(function () {
    angular
        .module("MooviApp")
        .controller("MovieDetailsController", MovieDetailsController);
    
    function MovieDetailsController($http,$scope) {
        $scope.mid = localStorage.getItem("movieId");
        var vidUrl = "https://api.themoviedb.org/3/movie/"+$scope.mid+"/videos?api_key=878a88feb1d8acab0c9883e805657264&language=en-US";
        var url = "/api/movie/";

        $scope.$on('$viewContentLoaded', function()
        {
            $http
                .get(url+$scope.mid)
                .then(function (value) {
                    $scope.movie = value.data;
                });

            $http
                .get(url+$scope.mid+"/cast")
                .then(function (value) {
                    $scope.cast = value.data;
                    console.log($scope.cast)
                });
        });
    }
})();