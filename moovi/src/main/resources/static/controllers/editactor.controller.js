(function () {
    angular
        .module("MooviApp")
        .controller("EditActorController", EditActorController);

    function EditActorController($http, $scope) {
        var vm  = this;
        var localpath = "http://localhost:8080/";
        $scope.$on('$viewContentLoaded', function()
        {
            var allActorUrl = "api/actor";
            $http
                .get(localpath+allActorUrl)
                .then(function (value) {
                    $scope.actors = value.data;
                })
        });

        vm.addActor = addActor;

        function addActor(actorId, actorName, actorPopularity, biography, dob, dod, imdbId, profilePicture) {
            var newActor;

            newActor = {
                "actorId":actorId,
                "actorName":actorName,
                "actorPopularity":actorPopularity,
                "biography":biography,
                "dob":dob,
                "dod":dod,
                "imdbId":imdbId,
                "profilePicture":profilePicture
            };

            var insertActorUrl = "api/actor";
            $http
                .post(localpath+insertActorUrl, newActor)
                .then(function (response) {
                    $scope.actor = response.data;
                    alert("movie added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(actor){
            $scope.eBox = true;
            $scope.aId = actor;
            console.log($scope.aId);
        }

        vm.deleteActor = deleteActor;

        function deleteActor(actorId) {
            var deleteActorUrl = "api/delete/actor/"+actorId;
            $http
                .delete(localpath+deleteActorUrl)
                .then(function () {
                    location.reload(true);
                });
        }

        vm.updateActor = updateActor;

        function updateActor(aId, actorName1, actorPopularity1, biography1, dob1, dod1, imdbId1, profilePicture1) {
            var updateActor;

            updateActor = {
                "actorName":actorName1,
                "actorPopularity":actorPopularity1,
                "biography":biography1,
                "dob":dob1,
                "dod":dod1,
                "imdbId":imdbId1,
                "profilePicture":profilePicture1
            };

            var updateActorurl = "api/edit/actor/"+aId;

            $http
                .put(localpath+updateActorurl, updateActor)
                .then(function (response) {
                    $scope.actor = response.data;
                    alert("actor updated");
                    location.reload(true);
                })
        }
    }
})();