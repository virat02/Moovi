(function () {
    angular
        .module("MooviApp")
        .controller("ActorsRecruitedController", ActorsRecruitedController);

    function ActorsRecruitedController($http, $scope) {

        var vm = this;
        var localpath = "http://localhost:8080/";
        var url1 = "api/recruit/adrecruiter/";
        var url2 = "/actorsrecruited";

        $scope.usT = localStorage.getItem("userType");
        $scope.d = localStorage.getItem("DirectedUserName");

        $scope.$on('$viewContentLoaded', function()
        {
            if ($scope.usT ==='admin') {
                $http
                    .get(localpath+url1+localStorage.getItem("DirectedUserName")+url2)
                    .then(function (response) {
                        $scope.allActorsHeading = "All Actors "+$scope.d+" Recruited";
                        $scope.actors = response.data;
                        console.log(response);
                    })
            }
            else {
                $http
                    .get(localpath+url1+localStorage.getItem("username")+url2)
                    .then(function (response) {
                        $scope.allActorsHeading = "All Actors You Recruited";
                        $scope.actors = response.data;
                        console.log(response);
                    })
            }
        });

        var deleteURL;
        if ($scope.usT === 'admin') {
            deleteURL = "api/delete/recruiter/"+$scope.d+"/actor/";
        }
        else {
            deleteURL = "api/delete/recruiter/"+localStorage.getItem("username")+"/actor/";
        }

        vm.deleteActor = deleteActor;

        function deleteActor(actorId) {
            $http
                .post(localpath+deleteURL+actorId)
                .then(function (response) {
                    $scope.allMoviesLikedHeading = "All Recruited Actors";
                    $scope.movies = response.data;
                    console.log(response);
                    alert("Actor removed from Recruit List");
                    location.reload(true);
                })
        }
    }
})();