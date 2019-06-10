(function () {
    angular
        .module("MooviApp")
        .controller("EditTheatreController", EditTheatreController);

    function EditTheatreController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";

        vm.addTheatre = addTheatre;

        $scope.$on('$viewContentLoaded', function()
        {
            var allTheatreUrl = "api/theatre";
            $http
                .get("http://localhost:8080/"+allTheatreUrl)
                .then(function (value) {
                    $scope.theatres = value.data;
                })

        });

        function addTheatre(theatreId, theatreName, theatreLocation) {
            var newTheatre;

            newTheatre = {
                "theatreId":theatreId,
                "theatreName":theatreName,
                "location":theatreLocation
            };

            var insertTheatreUrl = "api/theatre";
            $http
                .post(localpath+insertTheatreUrl, newTheatre)
                .then(function (response) {
                    $scope.theatre = response.data;
                    alert("Theatre Added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(theatre){
            $scope.eBox = true;
            $scope.tId = theatre;
            console.log($scope.tId);
        }

        vm.deleteTheatre = deleteTheatre;

        function deleteTheatre(theatreId) {
            var deleteTheatreUrl = "api/delete/theatre/"+theatreId;
            $http
                .delete(localpath+deleteTheatreUrl)
                .then(function () {
                    location.reload(true);
                })
        }

        vm.updateTheatre = updateTheatre;

        function updateTheatre(tId, theatreName1, location1) {
            var updatedTheatre;

            updatedTheatre = {
                "theatreName":theatreName1,
                "location":location1
            };

            var updateTheatreUrl = "api/edit/theatre/"+tId;

            $http
                .put(localpath+updateTheatreUrl, updatedTheatre)
                .then(function (response) {
                    $scope.theatre = response.data;
                    alert("Theatre Updated");
                    location.reload(true);
                });
        }
    }
})();