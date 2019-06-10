(function () {
    angular
        .module("MooviApp")
        .controller("EditMovieController", EditMovieController);

    function EditMovieController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";

        vm.addMovie = addMovie;

        $scope.$on('$viewContentLoaded', function()
        {
            var allMovieUrl = "api/movie";
            $http
                .get(localpath+allMovieUrl)
                .then(function (value) {
                    $scope.movies = value.data;
                })

        });

        function addMovie(movieId, movieName, overview, posterSrc, releaseDate, imdbRating, revenue, runtime, releaseStatus, imdbId) {
            var newMovie;

            newMovie = {
                "movieId":movieId,
                "movieName":movieName,
                "imdbId":imdbId,
                "posterSRC":posterSrc,
                "runtime":runtime,
                "imdbRating":imdbRating,
                "releaseDate":releaseDate,
                "revenue":revenue,
                "releaseStatus":releaseStatus,
                "overview":overview
            };

            var insertMovieUrl = "api/movie";
            $http
                .post(localpath+insertMovieUrl, newMovie)
                .then(function (response) {
                    $scope.movie = response.data;
                    alert("Movie Added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(movie){
            $scope.eBox = true;
            $scope.moId = movie;
            console.log($scope.moId);
        }

        vm.deleteMovie = deleteMovie;

        function deleteMovie(movieId) {
            var deleteMovieUrl = "api/delete/movie/"+movieId;
            $http
                .delete(localpath+deleteMovieUrl)
                .then(function () {
                    location.reload(true);
                })
        }

        vm.updateMovie = updateMovie;

        function updateMovie(moId, movieName1, overview1, posterSrc1, releaseDate1, imdbRating1, revenue1, runtime1, releaseStatus1, imdbId1, oldImdb, oldrevenue, oldruntime) {
            var updateMovie;
            var imdbInput = document.getElementById('f1');
            var revenueInput = document.getElementById('f2');
            var runtimeInput = document.getElementById('f3');

            console.log(imdbInput.value + " " +revenueInput.value+" "+runtimeInput.value);

            if (imdbRating1 === undefined)
                imdbRating1 = imdbInput.value;

            if (revenue1 === undefined)
                revenue1 = revenueInput.value;

            if (runtime1 === undefined)
                runtime1 = runtimeInput.value;

            console.log("new:"+imdbRating1 + " " +revenue1+" "+runtime1);

            updateMovie = {
                "movieName":movieName1,
                "imdbId":imdbId1,
                "posterSRC":posterSrc1,
                "runtime":runtime1,
                "imdbRating":imdbRating1,
                "releaseDate":releaseDate1,
                "revenue":revenue1,
                "releaseStatus":releaseStatus1,
                "overview":overview1
            };

            console.log(oldImdb + " " +revenue1+" "+runtime1);

            var updateMovieUrl = "api/edit/movie/"+moId;

            $http
                .put(localpath+updateMovieUrl, updateMovie)
                .then(function (response) {
                    $scope.movie = response.data;
                    alert("Movie Updated");
                    location.reload(true);
                });
        }
    }
})();