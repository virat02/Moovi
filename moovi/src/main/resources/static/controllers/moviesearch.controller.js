(function () {
    angular
        .module("MooviApp")
        .controller("MovieSearchController", MovieSearchController);

    function MovieSearchController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
        var url = "/api/search/movie";
        vm.searchMovieByTitle = searchMovieByTitle;

        $scope.userT = localStorage.getItem("userType");

        $scope.loading = "Loading Movies...";

        $scope.$on('$viewContentLoaded', function () {
            var nowPlayingUrl = "?nowPlaying=true";
            $scope.myVal = false;
            $scope.myVal1 = false;
            $http
                .get(url + nowPlayingUrl)
                .then(function (value) {
                    $scope.myVal1 = true;
                    $scope.nowplayingheading = "Now Playing Movies";
                    $scope.movies = value.data;
                })

        });

        $scope.myKeyPress = function (keyEvent, title) {
            if (keyEvent.which === 13)
                searchMovieByTitle(title);
        };

        function searchMovieByTitle(title) {
            var findByTitle = "?movieName=" + title;
            $scope.myVal = true;
            $scope.myVal1 = false;
            $http
                .get(url + findByTitle)
                .then(function (response) {
                    console.log(response);
                    $scope.movies = response.data;
                });
            console.log(title);
            console.log(localStorage.getItem("username"));
        }

        vm.likeThisMovie = likeThisMovie;

        function likeThisMovie(movieId) {
            var username = localStorage.getItem("username");
            var url = localpath + "api/check/like/fan/" + localStorage.getItem("username") + "/movie/" + movieId;
            var likeUrl = localpath + "api/like/fan/" + username + "/movie/" + movieId;

            $http
                .get(url)
                .then(function (response) {
                    $scope.fansWhoLiked = response.data;

                    if (response.data.length === 0) {

                        $http
                            .post(likeUrl)
                            .then(function () {
                                alert("You now like this movie");
                            });
                    }
                    else {
                        alert("You already liked this movie!");
                    }

                });
        }

        vm.dislikeThisMovie = dislikeThisMovie;

        function dislikeThisMovie(movieId) {
            var username = localStorage.getItem("username");
            var url1 = localpath + "api/check/dislike/fan/"+username+"/movie/"+movieId;
            var dislikeUrl = localpath + "api/dislike/fan/" + username + "/movie/" + movieId;

            $http
                .get(url1)
                .then(function (response) {
                    $scope.fansWhoDisliked = response.data;

                    if (response.data.length === 0) {

                        $http
                            .post(dislikeUrl)
                            .then(function () {
                                alert("You now disliked this movie");
                            });
                    }
                    else {
                        alert("You already disliked this movie!");
                    }

                });
        }

        vm.recommendThisMovie = recommendThisMovie;

        function recommendThisMovie(movieId) {
            var username = localStorage.getItem("username");
            var recommendMovieURL = localpath + "/api/check/recommend/critic/" + username + "/movie/" + movieId;
            var postURL = localpath + "/api/recommend/movie/" + movieId + "/critic/" + username;

            $http
                .get(recommendMovieURL)
                .then(function (response) {
                    $scope.criticWhoRecommended = response.data;

                    if (response.data.length === 0) {

                        $http
                            .post(postURL)
                            .then(function () {
                                alert("You now recommended this movie");
                            });
                    }
                    else {
                        alert("You already recommended this movie!");
                    }

                });
            }

            vm.changeToReviewView = changeToReviewView;

            function changeToReviewView(movieName, movieId) {
                localStorage.setItem("movieName", movieName);
                localStorage.setItem("movieId", movieId);
                window.location = "#!/review";
            }

            vm.changeToDetailsView = changeToDetailsView;

            function changeToDetailsView(movieId) {
                localStorage.setItem("movieId", movieId);
                window.location = "#!/movieDetails";
            }
        }

})();