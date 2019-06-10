(function () {
    angular
        .module("MooviApp")
        .controller("EditTheatreManagerController", EditTheatreManagerController);

    function EditTheatreManagerController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";

        vm.addManager = addManager;

        $scope.$on('$viewContentLoaded', function()
        {
            var allManagerUrl = "api/theatremanager";
            $http
                .get(localpath+allManagerUrl)
                .then(function (value) {
                    $scope.managers = value.data;
                })

        });

        function addManager(userId, firstName, lastName, username, password, email, dob) {
            var newManager;

            newManager = {
                "userId":userId,
                "firstName":firstName,
                "lastName":lastName,
                "username":username,
                "password":password,
                "email":email,
                "dob":dob
            };

            var insertManagerUrl = "api/theatremanager";
            $http
                .post(localpath+insertManagerUrl, newManager)
                .then(function (response) {
                    $scope.m = response.data;
                    alert("Manager Added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(manager){
            $scope.eBox = true;
            $scope.tmId = manager;
            console.log($scope.tmId);
        }

        vm.deleteManager = deleteManager;

        function deleteManager(tmname) {
            var deleteManagerUrl = "api/delete/theatremanager/"+tmname;
            $http
                .delete(localpath+deleteManagerUrl)
                .then(function () {
                    location.reload(true);
                })
        }

        vm.updateManager = updateManager;

        function updateManager(firstName1, lastName1, username1, password1, email1, dob1, oldusername) {
            var updatem;

            updatem = {
                "firstName":firstName1,
                "lastName":lastName1,
                "username":username1,
                "password":password1,
                "email":email1,
                "dob":dob1,
            };

            var updateManagerUrl = "api/edit/theatremanager/"+oldusername;

            $http
                .put(localpath+updateManagerUrl, updatem)
                .then(function (response) {
                    $scope.m = response.data;
                    alert("Manager Updated");
                    location.reload(true);
                });
        }

        vm.changeViewToTheatreManagerMyPage = changeViewToTheatreManagerMyPage;

        function changeViewToTheatreManagerMyPage(theatremanagerUsername, theatremanagerDtype) {
            localStorage.setItem("DirectedUserName",theatremanagerUsername);
            localStorage.setItem("DirectedUserType", theatremanagerDtype);
            window.location = "#!/mypage";
        }
    }
})();