(function () {
    angular
        .module("MooviApp")
        .controller("LoginController", LoginController);

    function LoginController($http, $scope) {
        var vm = this;
        var localpath = "http://localhost:8080/";
        var url = "api/";
        vm.checkIfValidUser = checkIfValidUser;

        function checkIfValidUser(username, password, userType) {

            $http
                .get(localpath+url+userType+"?username="+username+"&password="+password)
                .then(function (response) {
                    $scope.user = response.data;
                    if (response.data.length === 0){
                        alert ("Incorrect Login, Please register first!");
                        console.log(response);
                    }
                    else
                    {
                        localStorage.setItem('username',username);
                        alert("Successfully logged in as " + username);
                        console.log(response);
                        localStorage.setItem("userType",userType);
                        if(localStorage.getItem("userType")==='admin') {
                            window.location.href = localpath+"#!/admin";
                            location.reload(true);
                        }
                        else {
                            window.location.href = localpath+"#!/mypage";
                            location.reload(true);
                        }
                    }

                }, function(){
                    alert("Please register first!");
                });

            console.log(username);
            console.log(password);
            console.log(userType);
        }
    }
})();