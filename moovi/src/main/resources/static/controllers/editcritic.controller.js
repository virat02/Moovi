(function () {
    angular
        .module("MooviApp")
        .controller("EditCriticController", EditCriticController);
    
    function EditCriticController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";

        vm.addCritic = addCritic;

        $scope.$on('$viewContentLoaded', function()
        {
            var allCriticUrl = "api/critic";
            $http
                .get(localpath+allCriticUrl)
                .then(function (value) {
                    $scope.critics = value.data;
                })

        });

        function addCritic(userId, firstName, lastName, username, password, email, dob, criticDescription, websiteUrl) {
            var newCritic;

            newCritic = {
                "userId":userId,
                "firstName":firstName,
                "lastName":lastName,
                "username":username,
                "password":password,
                "email":email,
                "dob":dob,
                "criticDescription":criticDescription,
                "websiteUrl":websiteUrl
            };

            var insertCriticUrl = "api/critic";
            $http
                .post(localpath+insertCriticUrl, newCritic)
                .then(function (response) {
                    $scope.critic = response.data;
                    alert("Critic Added");
                    location.reload(true);
                });
        }

        vm.showEditBoxes = showEditBoxes;
        $scope.eBox = false;
        function showEditBoxes(critic){
            $scope.eBox = true;
            $scope.cId = critic;
            console.log($scope.cId);
        }

        vm.deleteCritic = deleteCritic;

        function deleteCritic(cname) {
            var deleteCriticUrl = "api/delete/critic/"+cname;
            $http
                .delete(localpath+deleteCriticUrl)
                .then(function () {
                    location.reload(true);
                })
        }

        vm.updateCritic = updateCritic;

        function updateCritic(firstName1, lastName1, username1, password1, email1, dob1, criticDescription1, websiteUrl, oldusername) {
            var updateCritic;

            updateCritic = {
                "firstName":firstName1,
                "lastName":lastName1,
                "username":username1,
                "password":password1,
                "email":email1,
                "dob":dob1,
                "criticDescription":criticDescription1,
                "websiteUrl":websiteUrl
            };

            var updateCriticUrl = "api/edit/critic/"+oldusername;

            $http
                .put(localpath+updateCriticUrl, updateCritic)
                .then(function (response) {
                    $scope.Critic = response.data;
                    alert("Critic Updated");
                    location.reload(true);
                });
        }

        vm.changeViewToCriticMyPage = changeViewToCriticMyPage;

        function changeViewToCriticMyPage(criticUsername, criticDtype) {
            localStorage.setItem("DirectedUserName",criticUsername);
            localStorage.setItem("DirectedUserType", criticDtype);
            window.location = "#!/mypage";
        }
    }
})();