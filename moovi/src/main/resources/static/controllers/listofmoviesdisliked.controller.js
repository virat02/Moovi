(function () {
    angular
        .module("MooviApp")
        .controller("MovieDislikedController", MovieDislikedController);

    function MovieDislikedController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/dislike/fan/";
        var url2 = "/moviesdisliked";

        $scope.usT = localStorage.getItem("userType");
        $scope.d = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT ==='admin') {
                $http
                    .get(localpath+url1+localStorage.getItem("DirectedUserName")+url2)
                    .then(function (response) {
                        $scope.allMoviesLikedHeading = "All Movies "+$scope.d+" Dislikes";
                        $scope.movies = response.data;
                        console.log(response);
                    })
            }
            else {
                $http
                    .get(localpath+url1+localStorage.getItem("username")+url2)
                    .then(function (response) {
                        $scope.allMoviesDislikedHeading = "All Movies You Dislike";
                        $scope.movies = response.data;
                        console.log(response);
                    })
            }

        });

        var deleteURL;
        if ($scope.usT === 'admin') {
            deleteURL = "api/delete/dislike/fan/"+$scope.d+"/movie/";
        }
        else {
            deleteURL = "api/delete/dislike/fan/"+localStorage.getItem("username")+"/movie/";
        }

        vm.deleteMovie = deleteMovie;

        function deleteMovie(movieId) {
            $http
                .post(localpath+deleteURL+movieId)
                .then(function (response) {
                    $scope.allMoviesLikedHeading = "All Disliked Movies";
                    $scope.movies = response.data;
                    console.log(response);
                    alert("Movie removed from Dislike List");
                    location.reload(true);
                })
        }
    }
})();