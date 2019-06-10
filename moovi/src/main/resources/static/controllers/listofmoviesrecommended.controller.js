(function () {
    angular
        .module("MooviApp")
        .controller("MovieRecommendedController", MovieRecommendedController);

    function MovieRecommendedController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/recommend/critic/";
        var url2 = "/recommendedmovies";

        $scope.usT = localStorage.getItem("userType");
        $scope.d = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT ==='admin') {
                $http
                    .get(localpath+url1+localStorage.getItem('DirectedUserName')+url2)
                    .then(function (response) {
                        $scope.allMoviesRecommendedHeading = "All Movies "+$scope.d+" Recommended";
                        $scope.movies = response.data;
                        console.log(response);
                    })
            }
            else {
                $http
                    .get(localpath+url1+localStorage.getItem('username')+url2)
                    .then(function (response) {
                        $scope.allMoviesRecommendedHeading = "All Movies You Recommended";
                        $scope.movies = response.data;
                        console.log(response);
                    })
            }
        });

        var deleteURL;
        if ($scope.usT === 'admin') {
            deleteURL = "api/delete/recommend/critic/"+$scope.d+"/movie/";
        }
        else {
            deleteURL = "api/delete/recommend/critic/"+localStorage.getItem("username")+"/movie/";
        }

        vm.deleteMovie = deleteMovie;

        function deleteMovie(movieId) {
            $http
                .post(localpath+deleteURL+movieId)
                .then(function (response) {
                    $scope.allMoviesLikedHeading = "All Recommended Movies";
                    $scope.movies = response.data;
                    console.log(response);
                    alert("Movie removed from Recommended List");
                    location.reload(true);
                })
        }
    }
})();