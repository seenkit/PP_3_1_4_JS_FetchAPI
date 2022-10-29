//Получение всех пользователей
function getAllUsers() {
    fetch("http://localhost:8080/api/users")
        .then(response => response.json())
        .then(users => {
            let html = ""
            users.forEach((user) => {
                html += `
                <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td> 
                <td>${user.lastName}</td> 
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(r => " " + r.role.substring(5))}</td>
                <td>
                <button class="btn btn-info btn-md" type="button" data-toggle="modal"
                data-target="#editModal" onclick="openModal(${user.id})">Edit</button>
                </td>
                <td>
                <button class="btn btn-danger btn-md" type="button" data-toggle="modal"
                data-target="#deleteModal" onclick="openModal(${user.id})">Delete</button>
                </td>
              </tr>`
            })
            document.getElementById("tbodyAllUsers").innerHTML = html
        })
}
getAllUsers()



function openModal(id) {
    fetch("http://localhost:8080/api/users/" + id)
        .then(response => {
            response.json().then(user => {
                document.getElementById("editId").value = user.id
                document.getElementById("editFirstName").value = user.firstName
                document.getElementById("editLastName").value = user.lastName
                document.getElementById("editAge").value = user.age
                document.getElementById("editEmail").value = user.email
                document.getElementById("editPassword").value = user.password

                document.getElementById("deleteId").value = user.id
                document.getElementById("deleteFirstName").value = user.firstName
                document.getElementById("deleteLastName").value = user.lastName
                document.getElementById("deleteAge").value = user.age
                document.getElementById("deleteEmail").value = user.email
            })
        })
}

function updateUser() {
    fetch("http://localhost:8080/api/update",
        {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            body: JSON.stringify({
                id: document.getElementById("editId").value,
                firstName: document.getElementById("editFirstName").value,
                lastName: document.getElementById("editLastName").value,
                age: document.getElementById("editAge").value,
                email: document.getElementById("editEmail").value,
                password: document.getElementById("editPassword").value,
                roles: setUserRoles(document.getElementById("editRoles").selectedOptions)
            })
        })
        .then(response => {
            if (response.ok) {
                document.getElementById("home-tab").click()
                getAllUsers()
            } else {
                response.json()
                    .then(result => {
                        alert(result.message)
                    })
            }
        })
}

function deleteUser() {
    fetch("http://localhost:8080/api/users/" + document.getElementById("deleteId").value, {
        method: "DELETE"
    })
        .then(() => {
            getAllUsers()
        })
}

function showAuthUserInfo() {
    fetch("http://localhost:8080/api/user/")
        .then(response => response.json())
        .then(user => {
            document.getElementById("navUserEmail").textContent = `${user.email}`
            document.getElementById("navUserRoles").textContent = `${user.roles.map(r => " " + r.role.substring(5))}`
            document.getElementById("tbodyUser").innerHTML =
                `<tr>
                                        <td>${user.id}</td>
                                        <td>${user.firstName}</td>
                                        <td>${user.lastName}</td>
                                        <td>${user.age}</td>
                                        <td>${user.email}</td>
                                        <td>${user.roles.map(r => " " + r.role.substring(5))}</td>
                                    </tr>`
        })
}

showAuthUserInfo()

function createUser() {
    fetch("http://localhost:8080/api/new",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            body: JSON.stringify({
                firstName: document.getElementById("newFirstName").value,
                lastName: document.getElementById("newLastName").value,
                age: document.getElementById("newAge").value,
                email: document.getElementById("newEmail").value,
                password: document.getElementById("newPassword").value,
                roles: setUserRoles(document.getElementById("newRoles").selectedOptions)
            })
        })
        .then(response => {
            if (response.ok) {
                    document.getElementById("home-tab").click()
                    getAllUsers()
                    document.getElementById("newUserForm").reset()
            } else {
                response.json()
                    .then(result => {
                        alert(result.message)
                    })
            }
        })
}

function setUserRoles(options) {
    let result = []
    for (let i = 0; i < options.length; i++) {
            result.push({id: options[i].value})
        }
    return result
}