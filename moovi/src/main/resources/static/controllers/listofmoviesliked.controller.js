(function () {
    angular
        .module("MooviApp")
        .controller("MovieLikedController", MovieLikedController);

    function MovieLikedController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/like/fan/";
        var url2 = "/moviesliked";

        $scope.usT = localStorage.getItem("userType");
        $scope.d = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT ==='admin') {
                $http
                    .get(localpath+url1+localStorage.getItem("DirectedUserName")+url2)
                    .then(function (response) {
                        $scope.allMoviesLikedHeading = "All Movies "+$scope.d+" Likes";
                        $scope.movies = response.data;
                        console.log(response);
                    })
            }
            else {
                $http
                    .get(localpath+url1+localStorage.getItem("username")+url2)
                    .then(function (response) {
                        $scope.allMoviesLikedHeading = "All Movies You Like";
                        $scope.movies = response.data;
                        console.log(response);
                    })
            }
        });

        var deleteURL;
        if ($scope.usT === 'admin') {
            deleteURL = "api/delete/like/fan/"+$scope.d+"/movie/";
        }
        else {
            deleteURL = "api/delete/like/fan/"+localStorage.getItem("username")+"/movie/";
        }

        vm.deleteMovie = deleteMovie;

        function deleteMovie(movieId) {
            $http
                .post(localpath+deleteURL+movieId)
                .then(function (response) {
                    $scope.allMoviesLikedHeading = "All Liked Movies";
                    $scope.movies = response.data;
                    console.log(response);
                    alert("Movie removed from Like List");
                    location.reload(true);
                })
        }
    }
})();