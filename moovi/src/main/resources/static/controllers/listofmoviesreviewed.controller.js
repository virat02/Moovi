(function () {
    angular
        .module("MooviApp")
        .controller("MovieReviewedController", MovieReviewedController);

    function MovieReviewedController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/review/";
        var url3 = "/movie";
        var url2 = "api/critic/show/reviews/";

        $scope.$on('$viewContentLoaded', function()
        {
            $http
                .get(localpath+url2+localStorage.getItem('username'))
                .then(function (response) {
                    $scope.allMoviesReviewedHeading = "All Movies You Reviewed";
                    $scope.reviews = response.data;
                    $scope.reviewResponse = angular.fromJson(response.data);

                    var i;
                    $scope.movies = [];

                    for(i = 0; i < $scope.reviewResponse.length; i++)
                    {
                        $http
                            .get(localpath+url1+$scope.reviewResponse[i].reviewId+url3)
                            .then(function (movieresponse) {
                                $scope.allMoviesReviewedHeading = "All Movies You Reviewed";
                                $scope.movies.push(movieresponse.data);
                                console.log(movieresponse);
                            });
                    }
                });
        });
    }
})();