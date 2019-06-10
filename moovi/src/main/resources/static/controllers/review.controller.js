(function () {
    angular
        .module("MooviApp")
        .controller("ReviewController", ReviewController);

    function ReviewController($http,$scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
        var url = "/api/review";
        var movieId = localStorage.getItem("movieId");
        var username = localStorage.getItem("username");
        var name = localStorage.getItem("movieName");
        $scope.mName = name;
        vm.writeReview = writeReview;

        function writeReview(field,reviewVal) {
            var reviewForMovie = {
                "rating":field,
                "review":reviewVal
            };
            $http
                .post(localpath+url,reviewForMovie)
                .then(function (response) {
                    window.location.href = localpath+"#!/searchMovie";

                    alert("Review Posted");

                    vm.linkCriticToReview = linkCriticToReview;

                    function linkCriticToReview() {
                        $scope.reviewResponse = angular.fromJson(response.data);

                        var criticLinkingURL = localpath+"api/reviews/review/"+$scope.reviewResponse.reviewId+"/critic/"+username+"/movie/"+movieId;

                        $http
                            .post(criticLinkingURL)
                            .then(function(response) {
                                $scope.review = response;
                            });
                    }
                    linkCriticToReview();
                });
        }
    }
})();