(function () {
    angular
        .module("MooviApp",["ngRoute"])
        .config(Config);

    function Config($routeProvider) {
        $routeProvider
            .when("/searchMovie", {
                templateUrl: "views/searchmovie.html",
                controller: "MovieSearchController",
                controllerAs: "model"
            })
            .when("/searchActor", {
                templateUrl: "views/searchactor.html",
                controller: "ActorSearchController",
                controllerAs: "ac"
            })
            .when("/searchFan", {
                templateUrl: "views/searchfan.html",
                controller: "FanSearchController",
                controllerAs: "fs"
            })
            .when("/searchCritic", {
                templateUrl: "views/searchcritic.html",
                controller: "CriticSearchController",
                controllerAs: "cr"
            })
            .when("/movieDetails/:movieName", {
                templateUrl: "views/moviedetails.html",
                controller: "MovieDetailsController",
                controllerAs: "md"
            })
            .when("/login", {
                    templateUrl: "views/login.html",
                    controller: "LoginController",
                    controllerAs: "login"
            })
            .when("/register", {
                    templateUrl: "views/register.html",
                    controller: "RegisterController",
                    controllerAs: "register"
            })
            .when("/manageThisTheatre", {
                templateUrl: "views/manageThisTheatre.html",
                controller: "ManageTheatreController",
                controllerAs: "theatre"
            })
            .when("/review", {
                templateUrl: "views/review.html",
                controller: "ReviewController",
                controllerAs: "rev"
            })
            .when("/mypage", {
                templateUrl: "views/mypage.html",
                controller: "MyPageController",
                controllerAs: "mp"
            })
            .when("/admin", {
                templateUrl: "views/admin.html",
                controller: "AdminController",
                controllerAs: "ad"
            })
            .when("/listOfFansYouFollow", {
                templateUrl: "views/listOfFansYouFollow.html",
                controller: "FanFollowingController",
                controllerAs: "ff"
            })
            .when("/listOfCriticsYouFollow", {
                templateUrl: "views/listOfCriticsYouFollow.html",
                controller: "CriticFollowingController",
                controllerAs: "cf"
            })
            .when("/listOfActorsYouFollow", {
                templateUrl: "views/listOfActorsYouFollow.html",
                controller: "ActorFollowingController",
                controllerAs: "af"
            })
            .when("/listOfMoviesYouLiked", {
                templateUrl: "views/listOfMoviesYouLiked.html",
                controller: "MovieLikedController",
                controllerAs: "ml"
            })
            .when("/listOfMoviesYouDisliked", {
                templateUrl: "views/listOfMoviesYouDisliked.html",
                controller: "MovieDislikedController",
                controllerAs: "mdl"
            })
            .when("/listOfFansWhoFollowYouOfCritic", {
                templateUrl: "views/listOfFansWhoFollowYouOfCritic.html",
                controller: "FansFollowedController",
                controllerAs: "ffb"
            })
            .when("/listOfFansWhoFollowYouOfFan", {
                templateUrl: "views/listOfFansWhoFollowYouOfFan.html",
                controller: "FansFollowedOfFanController",
                controllerAs: "fffb"
            })
            .when("/listOfMoviesYouRecommended", {
                templateUrl: "views/listOfMoviesYouRecommended.html",
                controller: "MovieRecommendedController",
                controllerAs: "mrc"
            })
            .when("/listOfMoviesYouReviewed", {
                templateUrl: "views/listOfMoviesYouReviewed.html",
                controller: "MovieReviewedController",
                controllerAs: "mrv"
            })
            .when("/listOfActorsYouRecruited", {
                templateUrl: "views/listOfActorsYouRecruited.html",
                controller: "ActorsRecruitedController",
                controllerAs: "acr"
            })
            .when("/listOfTheatresManaged", {
                templateUrl: "views/listOfTheatresManaged.html",
                controller: "TheatresManagedController",
                controllerAs: "tm"
            })
            .when("/editMovieForAdmin", {
                templateUrl: "views/editMovieForAdmin.html",
                controller: "EditMovieController",
                controllerAs: "em"
            })
            .when("/editActorForAdmin", {
                templateUrl: "views/editActorForAdmin.html",
                controller: "EditActorController",
                controllerAs: "ea"
            })
            .when("/editFanForAdmin", {
                templateUrl: "views/editFanForAdmin.html",
                controller: "EditFanController",
                controllerAs: "ef"
            })
            .when("/editCriticForAdmin", {
                templateUrl: "views/editCriticForAdmin.html",
                controller: "EditCriticController",
                controllerAs: "ec"
            })
            .when("/editAdRecruiterForAdmin", {
                templateUrl: "views/editAdRecruiterForAdmin.html",
                controller: "EditAdRecruiterController",
                controllerAs: "ead"
            })
            .when("/editReviewForAdmin", {
                templateUrl: "views/editReviewForAdmin.html",
                controller: "EditReviewController",
                controllerAs: "er"
            })
            .when("/editTheatreForAdmin", {
                templateUrl: "views/editTheatreForAdmin.html",
                controller: "EditTheatreController",
                controllerAs: "et"
            })
            .when("/editTheatreManagerForAdmin", {
                templateUrl: "views/editTheatreManagerForAdmin.html",
                controller: "EditTheatreManagerController",
                controllerAs: "etm"
            })
            .when("/showListOfRecommendedMovies", {
                templateUrl: "views/showListOfRecommendedMovies.html",
                controller: "MovieRecommendationsListingController",
                controllerAs: "mrcl"
            })
            .when("/movieDetails", {
                templateUrl: "views/movieDetails.html",
                controller: "MovieDetailsController",
                controllerAs: "mdc"
            })
            .when("/actorDetails", {
                templateUrl: "views/actorDetails.html",
                controller: "ActorDetailsController",
                controllerAs: "adc"
            })
            .otherwise({
                templateUrl : "views/landingpage.html"
            });
    }
})();