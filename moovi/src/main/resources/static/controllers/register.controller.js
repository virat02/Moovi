(function () {
    angular
        .module("MooviApp")
        .controller("RegisterController", RegisterController);

    function RegisterController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
        var url = "/api/";
        vm.registerUserInDb = registerUserInDb;

        function registerUserInDb(firstName, lastName, username, password, userType, email, dob, userDescription, websiteURL) {
            var newUser;

            if (userType === "fan")
            {
                newUser = {
                    "firstName":firstName,
                    "lastName":lastName,
                    "username":username,
                    "password":password,
                    "email": email,
                    "dob": dob,
                    "fanDescription": userDescription
                };
            }
            else if (userType === "critic")
            {
                newUser = {
                    "firstName":firstName,
                    "lastName":lastName,
                    "username":username,
                    "password":password,
                    "email": email,
                    "dob": dob,
                    "criticDescription": userDescription,
                    "websiteUrl": websiteURL
                };
            }
            else if (userType === "adrecruiter")
            {
                newUser = {
                    "firstName":firstName,
                    "lastName":lastName,
                    "username":username,
                    "password":password,
                    "email": email,
                    "dob": dob,
                    "recruiterDescription": userDescription
                };
            }
            else if (userType === "theatremanager")
            {
                newUser = {
                    "firstName":firstName,
                    "lastName":lastName,
                    "username":username,
                    "password":password,
                    "email": email,
                    "dob": dob,
                    "theatremanagerDescription": userDescription
                };
            }

            var redirectToURL = "#!/login";
            $http
                .post(localpath+url+userType, newUser)
                .then(function (response) {
                    $scope.user = response.data;
                    alert("Successfully registered!");
                    window.location = redirectToURL;
                }, function(error){
                alert("Please try registering again with different credentials!");
            });

            console.log(username);
            console.log(password);
            console.log(userType);
        }
    }
})();