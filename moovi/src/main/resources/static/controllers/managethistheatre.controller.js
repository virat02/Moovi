(function () {
    angular
        .module("MooviApp")
        .controller("ManageTheatreController", ManageTheatreController);

    function ManageTheatreController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
        var url = "api/theatre";

        $scope.$on('$viewContentLoaded',function (){
            var movieUrl = "/api/search/movie?nowPlaying=true";
            $http
                .get(movieUrl)
                .then(function (value) {
                    $scope.movies = value.data;
                })

        });

        vm.registerTheatreForTheatreManager = registerTheatreForTheatreManager;

        function registerTheatreForTheatreManager(theatreName, location, movieId) {
            var newTheatre = {
                "theatreName":theatreName,
                "location":location,
                "totalScreens": 1
            };

            var username = localStorage.getItem("username");
            var createTheatreURL = localpath+url;
            $http
                .post(createTheatreURL, newTheatre)
                .then(function(response) {
                    $scope.theatre = response;

                    vm.linkManagerToTheatreURL = linkManagerToTheatreURL;

                    function linkManagerToTheatreURL() {

                        $scope.theatreResponse = angular.fromJson(response.data);

                        var linkingURL = localpath+"/api/manage/theatre/"+$scope.theatreResponse.theatreId+"/manager/"+username;

                        $http
                            .post(linkingURL)
                            .then(function(response) {
                                $scope.theatre = response;
                            });

                    }

                    linkManagerToTheatreURL();

                    vm.createScreen = createScreen;

                    function createScreen() {

                        var createScreenURL = localpath + "api/screen";

                        var newScreen = {};

                        $http
                            .post(createScreenURL, newScreen)
                            .then(function (response2) {
                                $scope.screen = response2;

                                vm.linkScreenToTheatreURL = linkScreenToTheatreURL;

                                function linkScreenToTheatreURL() {

                                    $scope.screenResponse = angular.fromJson(response2.data);

                                    var screenId = $scope.screenResponse.screenId;

                                    var linkingScreenToTheatreURL = localpath + "api/screen/" + screenId + "/theatre/" + $scope.theatreResponse.theatreId;

                                    $http
                                        .post(linkingScreenToTheatreURL)
                                        .then(function () {

                                            vm.linkScreenToMovie = linkScreenToMovie;

                                            function linkScreenToMovie() {

                                                var linkScreenToMovieURL = localpath + "api/screen/" + screenId + "/movie/" + movieId;

                                                $http
                                                    .post(linkScreenToMovieURL)
                                                    .then(function () {
                                                        alert("Successfully registered a theatre!")
                                                    });
                                            }

                                            linkScreenToMovie();
                                        });
                                }

                                linkScreenToTheatreURL();
                        });
                    }

                    createScreen();

        });
        }
    }
})();