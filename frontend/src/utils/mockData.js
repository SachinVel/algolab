const sampleData = {
    'registerUser' : {
        path : '/user/register',
        method : 'POST',
        requestBody : {
            firstName: 'anurag',
            lastName: 'kumar',
            email: 'hey@gmail.com',
            username : 'kareena',
            password: 'kapoor',
            role: 'ADMIN', // ADMIN or STUDENT OR INSTRUCTOR
            bio: 'whatever',
        }
    },
    'updateUser' : {
        path : '/user/update',
        method : 'PUT',
        requestBody : {
            firstName: 'anurag',
            lastName: 'kumar',
            email: 'hey@gmail.com',
            username : 'kareena',
            password: 'kapoor',
            role: 'ADMIN', // ADMIN or STUDENT OR INSTRUCTOR
            bio: 'whatever',
        }
    },
    'deleteUser' : {
        path : '/delete/{userId}',
        method : 'DELETE'
    },
    'validateUserName' : {
        path : '/validate-username?username=anuragKumar',
        method : 'GET'
    }
}
