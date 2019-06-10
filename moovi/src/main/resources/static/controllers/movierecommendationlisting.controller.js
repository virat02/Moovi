(function () {
    angular
        .module("MooviApp")
        .controller("MovieRecommendationsListingController", MovieRecommendationsListingController);

    function MovieRecommendationsListingController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";

        $scope.userT = localStorage.getItem("userType");
        var showRecommendedMovieListingURL = "api/recommend/critic/";

        $scope.$on('$viewContentLoaded', function()
        {
            $http
                .get(localpath+showRecommendedMovieListingURL + localStorage.getItem("criticClicked") +"/recommendedmovies")
                .then(function (response) {
                    $scope.movieRecommendedHeading = "All movies recommended";
                    $scope.recommendedMovies = response.data;
                })
        });
    }
})();