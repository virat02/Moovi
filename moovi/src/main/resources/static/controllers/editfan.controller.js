(function () {
    angular
        .module("MooviApp")
        .controller("EditFanController", EditFanController);
    
    function EditFanController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";

        vm.addFan = addFan;

        $scope.$on('$viewContentLoaded', function()
        {
            var allFanUrl = "api/fan";
            $http
                .get(localpath+allFanUrl)
                .then(function (value) {
                    $scope.fans = value.data;
                })

        });

        function addFan(userId, firstName, lastName, username, password, email, dob, fanDescription) {
            var newFan;

            newFan = {
                "userId":userId,
                "firstName":firstName,
                "lastName":lastName,
                "username":username,
                "password":password,
                "email":email,
                "dob":dob,
                "fanDescription":fanDescription
            };

            var insertFanUrl = "api/fan";
            $http
                .post(localpath+insertFanUrl, newFan)
                .then(function (response) {
                    $scope.fan = response.data;
                    alert("Fan Added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(fan){
            $scope.eBox = true;
            $scope.fId = fan;
            console.log($scope.fId);
        }

        vm.deleteFan = deleteFan;

        function deleteFan(fname) {
            var deleteFanUrl = "api/delete/fan/"+fname;
            $http
                .delete(localpath+deleteFanUrl)
                .then(function () {
                    location.reload(true);
                })
        }

        vm.updateFan = updateFan;

        function updateFan(firstName1, lastName1, username1, password1, email1, dob1, fanDescription1, oldusername) {
            var updateFan;

            updateFan = {
                "firstName":firstName1,
                "lastName":lastName1,
                "username":username1,
                "password":password1,
                "email":email1,
                "dob":dob1,
                "fanDescription":fanDescription1
            };

            var updateFanUrl = "api/edit/fan/"+oldusername;

            $http
                .put(localpath+updateFanUrl, updateFan)
                .then(function (response) {
                    $scope.fan = response.data;
                    alert("Fan Updated");
                    location.reload(true);
                });
        }

        vm.changeViewToFanMyPage = changeViewToFanMyPage;

        function changeViewToFanMyPage(fanUserName, fanDtype) {
            localStorage.setItem("DirectedUserName",fanUserName);
            localStorage.setItem("DirectedUserType", fanDtype);
            window.location = "#!/mypage";
        }
    }
})();