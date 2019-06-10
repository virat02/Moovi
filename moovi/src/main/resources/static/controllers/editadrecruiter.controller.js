(function () {
    angular
        .module("MooviApp")
        .controller("EditAdRecruiterController", EditAdRecruiterController);

    function EditAdRecruiterController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";

        vm.addAdRecruiter = addAdRecruiter;

        $scope.$on('$viewContentLoaded', function()
        {
            var allFanUrl = "api/adrecruiter";
            $http
                .get(localpath+allFanUrl)
                .then(function (value) {
                    $scope.recruiters = value.data;
                })
        });

        function addAdRecruiter(userId, firstName, lastName, username, password, email, dob, adrecruiterDescription) {
            var newAdRecruiter;

            newAdRecruiter = {
                "userId":userId,
                "firstName":firstName,
                "lastName":lastName,
                "username":username,
                "password":password,
                "email":email,
                "dob":dob,
                "recruiterDescription":adrecruiterDescription
            };

            var insertFanUrl = "api/adrecruiter";
            $http
                .post(localpath+insertFanUrl, newAdRecruiter)
                .then(function (response) {
                    $scope.r = response.data;
                    alert("AdRecruiter Added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(recruiter){
            $scope.eBox = true;
            $scope.adId = recruiter;
            console.log($scope.adId);
        }

        vm.deleteAdRecruiter = deleteAdRecruiter;

        function deleteAdRecruiter(adname) {
            var deleteAdRecruiterUrl = "api/delete/adrecruiter/"+adname;
            $http
                .delete(localpath+deleteAdRecruiterUrl)
                .then(function () {
                    location.reload(true);
                })
        }

        vm.updateAdRecruiter = updateAdRecruiter;

        function updateAdRecruiter(firstName1, lastName1, username1, password1, email1, dob1, rDescription1, oldusername) {
            var updateAdRecruiter;

            updateAdRecruiter = {
                "firstName":firstName1,
                "lastName":lastName1,
                "username":username1,
                "password":password1,
                "email":email1,
                "dob":dob1,
                "recruiterDescription":rDescription1
            };

            var updateAdRecruiterUrl = "api/edit/adrecruiter/"+oldusername;

            $http
                .put(localpath+updateAdRecruiterUrl, updateAdRecruiter)
                .then(function (response) {
                    $scope.r = response.data;
                    alert("AdRecruiter Updated");
                    location.reload(true);
                });
        }

        vm.changeViewToAdRecruiterMyPage = changeViewToAdRecruiterMyPage;

        function changeViewToAdRecruiterMyPage(recruiterUsername, recruiterDtype) {
            localStorage.setItem("DirectedUserName",recruiterUsername);
            localStorage.setItem("DirectedUserType", recruiterDtype);
            window.location = "#!/mypage";
        }
    }
})();