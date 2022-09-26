var accounts = []
var baseUrl = "http://localhost:8080/api/v1/accounts"
let currentPage = 1;
const size = 10;
// sorting
let sortField = "id";
let isAsc = true;
function Account(id, username, lastName, firstName, role, deptId, deptName) {
    this.id = id;
    this.username = username;
    this.lastName = lastName;
    this.firstName = firstName;
    this.role = role;
    this.departmentId = deptId;
    this.departmentName = deptName;
}
function buildAccountTable() {
    $('tbody').empty();
    getaccounts();
}
function getaccounts() {

    let url = baseUrl  + '?page='
        + `${currentPage - 1}` + '&size=' + size
        + "&sort=" + sortField + "," + (isAsc ? "asc" : "desc");

    const searchValue = document.getElementById("search-account-input");
    if (searchValue?.value) {
        url += "&search.contains=" + searchValue.value;
    }

    // call API from server
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json",
        dataType: 'json', // datatype return
        headers: headers,
        success: function(data, textStatus, xhr) {
            // success
            
            parseAccountData(data);
            fillAccountToTable();
            fillAccountPaging(data.numberOfElements, data.totalPages);
            fillAccountSorting();
        },
        error(jqXHR, textStatus, errorThrown) {
            // TODO
            alert("Error when loading data");
        }
    });
}
function parseAccountData(data){
    
    accounts = []
    data.content.forEach(function(item, index){
        accounts.push(new Account(
            item.id, item.username,
            item.lastName, item.firstName, 
            item.role, 
            item.departmentId, item.departmentName))

    })
    // accounts = accounts.reverse()
   
}
function fillAccountToTable(){
    // console.log(accounts)
    accounts.forEach(function(item, index){
        $('tbody').append(
            '<tr>' +
            '<td>' + item.id + '</td>' +
            '<td>' + item.username + '</td>' +
            '<td>' + item.firstName +' ' + item.lastName + '</td>' +
            '<td>' + item.role + '</td>' +
            '<td>' + item.departmentName + '</td>' +
            '<td>' +
            '<a class="edit" title="Edit" data-toggle="tooltip" onclick="openUpdateAccountModal('
            + item.id + ')">'
            + '<i class="material-icons">&#xE254;</i>'
            + '</a>' +
            '<a class="delete" title="Delete" data-toggle="tooltip" onclick="openConfirmDelete('
            + item.id + ')">'
            + '<i class="material-icons">&#xE872;</i>'
            + '</a>' +
            '</td>' +
            '</tr>'
        )
    })
}
function fillAccountPaging(numberOfElements, totalPages){
    
    if (currentPage > 1) {
        document.getElementById("account-previousPage-btn").disabled = false;
    } else {
        document.getElementById("account-previousPage-btn").disabled = true;
    }

    // next
    if (currentPage < totalPages ) {
        document.getElementById("account-nextPage-btn").disabled = false;
    } else {
        document.getElementById("account-nextPage-btn").disabled = true;
    }

    // text
    document.getElementById("account-page-info").innerHTML = numberOfElements
        + " tài khoản của trang " + currentPage + " / " + totalPages;

}
function fillAccountSorting(){
    const sortTypeClazz = isAsc ? "fa-sort-up" : "fa-sort-down";
    const defaultSortType = "fa-sort";

    switch (sortField) {
        case 'username':
            // changeIconSort("#username-sort", sortTypeClazz);
            // changeIconSort("#fullname-sort", defaultSortType);
            // changeIconSort("#departmentName-sort", defaultSortType);
            break;
    }
}
function changeIconSort(id, sortTypeClazz) {
    
    document.getElementById(id).classList.remove("fa-sort", "fa-sort-up", "fa-sort-down");
    document.getElementById(id).classList.add(sortTypeClazz);
}
function changeAccountSort(field) {
    if (field === sortField) {
        isAsc = !isAsc;
    } else {
        sortField = field;
        isAsc = true;
    }
    buildAccountTable();
}
function changeIdSort(field) {
    if (field === sortField) {
        isAsc = !isAsc;
    } else {
        sortField = field;
        isAsc = true;
    }
    buildAccountTable();
}
function prevAccountPage() {
    changeAccountPage(currentPage - 1);
}
function nextAccountPage() {
    changeAccountPage(currentPage + 1);
}
function changeAccountPage(page) {
    currentPage = page;
    buildAccountTable();
}
function openCreateAccountModal() {
    resetForm();
    openModal();
}
function openUpdateAccountModal(id) {

    // get index from employee's id
    const index = accounts.findIndex(x => x.id === id);

    // fill data
    document.getElementById("id").value = accounts[index].id;
    document.getElementById("username").value = accounts[index].username;
    document.getElementById("firstName").value = accounts[index].firstName;
    document.getElementById("lastName").value = accounts[index].lastName;
    document.getElementById("role").value = accounts[index].role;
    document.getElementById(
        "departmentId").value = accounts[index].departmentId;

    openModal();
}
function resetForm() {
    document.getElementById("username").value = "";
    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("role").value = "";
    document.getElementById("departmentId").value = "";
}
function openModal() {
    $('#myCreateAccountModal').modal('show');
}
function createAccount() {
    // get data
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let lastName = document.getElementById("lastName").value;
    let firstName = document.getElementById("firstName").value;
    let role = document.getElementById("role").value;
    let departmentId = document.getElementById("departmentId").value;

    // TODO validate
    // then fail validate ==> return;

    const account = {
        username: username,
        password: password,
        lastName: lastName,
        firstName: firstName,
        role: role,
        departmentId: Number(departmentId),
    };

    $.ajax({
        url: baseUrl,
        type: 'POST',
        data: JSON.stringify(account), // body
        contentType: "application/json", // type of body (json, xml, text)
        headers: headers,
        success: function (data, textStatus, xhr) {
            hideModal();
            showSuccessAlert();
            buildAccountTable();
        },
        error(jqXHR, textStatus, errorThrown) {
            alert("Có lỗi khi tạo tài khoản");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}
function updateAccount() {
    const id = document.getElementById("id").value;
    const username = document.getElementById("username").value;
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const role = document.getElementById("role").value;
    const departmentId = document.getElementById("departmentId").value;

    // TODO validate
    // then fail validate ==> return;

    const account = {
        id: Number(id),
        username: username,
        firstName: firstName,
        lastName: lastName,
        role: role,
        departmentId: Number(departmentId)
    };

    $.ajax({
        url: url + "/" + id,
        type: 'PUT',
        data: JSON.stringify(account),
        contentType: "application/json", // type of body (json, xml, text)
        headers: headers,
        success: function (result) {
            // error
            if (!result) {
                alert("Có lỗi khi cập nhật tài khoản");
                return;
            }

            // success
            hideModal();
            showSuccessAlert();
            buildAccountTable();
        }
    });
}
function deleteAccount(id) {
    // TODO validate
    $.ajax({
        url: baseUrl + "/" + id,
        type: 'DELETE',
        headers: headers,
        success: function (result) {
            // error
            if (!result) {
                alert("Có lỗi khi xóa tài khoản");
                return;
            }

            // success
            showSuccessAlert();
            buildAccountTable();
        }
    });
}
function openConfirmDelete(id) {
    // get index from employee's id
    const index = accounts.findIndex(x => x.id === id);
    const name = accounts[index].name;

    const result = confirm("Bạn có muốn xóa " + name + " không?");
    if (result) {
        deleteAccount(id);
    }
}
function hideModal() {
    $('#myCreateAccountModal').modal('hide');
}
function showSuccessAlert() {
    $("#success-alert").fadeTo(2000, 500).slideUp(500, function () {
        $("#success-alert").slideUp(500);
    });
}
function saveAccount() {
    const id = document.getElementById("id").value;

    if (!id) {
        createAccount();
    } else {
        updateAccount();
    }
}
function setupSearchEvent() {
    buildAccountTable();
}