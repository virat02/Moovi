(function () {
    angular
        .module("MooviApp")
        .controller("EditReviewController", EditReviewController);

    function EditReviewController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
        vm.addReview = addReview;

        $scope.$on('$viewContentLoaded', function()
        {
            var allReviewUrl = "api/review";
            $http
                .get(localpath+allReviewUrl)
                .then(function (value) {
                    $scope.reviews = value.data;
                })

        });

        function addReview(reviewId, review, rating) {
            var newReview;

            newReview = {
                "reviewId":reviewId,
                "review":review,
                "rating":rating
            };

            var insertReviewUrl = "api/review";
            $http
                .post(localpath+insertReviewUrl, newReview)
                .then(function (response) {
                    $scope.review = response.data;
                    alert("Review Added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(review){
            $scope.eBox = true;
            $scope.rId = review;
            console.log($scope.cId);
        }

        vm.deleteReview = deleteReview;

        function deleteReview(rname) {
            var deleteReviewUrl = "api/delete/review/"+rname;
            $http
                .delete(localpath+deleteReviewUrl)
                .then(function () {
                    location.reload(true);
                })
        }

        vm.updateReview = updateReview;

        function updateReview(reviewId, review, rating) {
            var updateReview;
            console.log(reviewId);
            updateReview = {
                "reviewId":reviewId,
                "review":review,
                "rating":rating
            };

            var updateReviewUrl = "api/edit/review/"+reviewId;

            $http
                .put(localpath+updateReviewUrl, updateReview)
                .then(function (response) {
                    $scope.review = response.data;
                    alert("Review Updated");
                    location.reload(true);
                });
        }
    }
})();