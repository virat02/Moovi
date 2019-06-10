(function () {
    angular
        .module("MooviApp")
        .controller("ActorSearchController", ActorSearchController);

    function ActorSearchController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
        var url = "/api/search/actor";
        vm.searchActorByName = searchActorByName;

        $scope.userT = localStorage.getItem("userType");

        $scope.$on('$viewContentLoaded', function()
        {
            $scope.myVal = false;
            $http
                .get(url)
                .then(function (value) {
                    $scope.mostPopularHeading = "Most Popular Actors";
                    $scope.actors = value.data;
                })

        });

        $scope.myKeyPress = function(keyEvent,name) {
            if (keyEvent.which === 13)
                searchActorByName(name);
        };

        function searchActorByName(name) {
            var findByName = "?actorName="+name;
            $scope.myVal = true;
            $http
                .get(url+findByName)
                .then(function (response) {
                    console.log(response);
                    $scope.actors = response.data;
                });
            console.log(name);
        }

        vm.followThisActor = followThisActor;

        function followThisActor(actorId) {
            var username = localStorage.getItem("username");
            var url1 = localpath+"api/check/follow/fan/"+username+"/actor/"+actorId;
            var followUrl = localpath+"api/follow/fan/"+username+"/actor/"+actorId;

            $http
                .get(url1)
                .then(function (response) {
                    $scope.fansWhoFollowedActor = response.data;

                    if (response.data.length === 0) {

                        $http
                            .post(followUrl)
                            .then(function () {
                                alert("You now follow this actor");
                            });
                    }
                    else {
                        alert("You already follow this actor!");
                    }

                });
        }

        vm.recruitThisActor = recruitThisActor;

        function recruitThisActor (actorId) {
            var username = localStorage.getItem("username");
            var url1 = localpath + "api/check/recruit/adrecruiter/"+username+"/actor/"+actorId;
            var recruitUrl = localpath+"api/recruit/adrecruiter/"+username+"/actor/"+actorId;

            $http
                .get(url1)
                .then(function (response) {
                    $scope.adrecruitersWhoRecruitedActor = response.data;

                    if (response.data.length === 0) {

                        $http
                            .post(recruitUrl)
                            .then(function () {
                                alert("You now recruited this actor");
                            });
                    }
                    else {
                        alert("You already have recruited this actor!");
                    }

                });
        }

        vm.changeToDetailsView = changeToDetailsView;

        function changeToDetailsView(actorId) {
            localStorage.setItem("actorId",actorId);
            window.location = "#!/actorDetails";
        }

    }
})();