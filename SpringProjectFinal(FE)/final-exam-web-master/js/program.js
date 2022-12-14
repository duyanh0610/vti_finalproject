$(function () {

    if (!isLogin()) {
        // redirect to login page
        window.location.replace("http://localhost:5501/html/loginform.html");
        return;
    }

    $(".header").load("header.html");
    $(".main").load("home.html");
    $(".footer").load("footer.html");

    // trick
    setTimeout(hasAuthorized, 50);
});


function isLogin() {
    return !!storage.getItem("ID");
}

function logout() {
    storage.removeItem("ID");
    storage.removeItem("FULL_NAME");
    storage.removeItem("FIRST_NAME");
    storage.removeItem("LAST_NAME");
    storage.removeItem("ROLE");
    storage.removeItem("USERNAME");
    storage.removeItem("PASSWORD");

    // redirect to login page
    window.location.replace("http://localhost:5501/html/loginform.html");

}

function clickNavHome() {
    $(".main").load("home.html");

    hello()
}
function clickNavAccountList(){
    $(".main").load("accountList.html")
   
   
    buildAccountTable()
   
   
}
function clickNavDepartmentList(){
    $(".main").load("departmentList.html") 
  
 
    buildDepartmentTable()

    
}

function showSuccessSnackBar(snackbarMessage) {
    // Get the snackbar DIV
    var x = document.getElementById("snackbar");
    x.innerHTML = snackbarMessage;

    // Add the "show" class to DIV
    x.className = "show";

    // After 3 seconds, remove the show class from DIV
    setTimeout(function () { x.className = x.className.replace("show", ""); }, 3000);
}

function hasAuthorized() {
    let accountListElem = document.getElementById("accountListId");
    let departmentListElem = document.getElementById("departmentListId");

    if (accountListElem && departmentListElem) {
        switch (storage.getItem("ROLE")) {
            case "ADMIN":
                accountListElem.style.display = "block";
                departmentListElem.style.display = "block";
                break;
            case "MANAGER":
                accountListElem.style.display = "none";
                departmentListElem.style.display = "none";
                break;
            case "EMPLOYEE":
                accountListElem.style.display = "none";
                departmentListElem.style.display = "none";
                break;
        }
    }
}

function handleSelectLang() {
    const lang = document.getElementById("langSwitchId").value;
    storage.setItem("LANG", lang);

    const homeLabel = document.getElementById("homeLabelId");
    const headerHomeLabelId = document.getElementById("headerHomeLabelId");
    const accountListId = document.getElementById("accountListId");
    const departmentListId = document.getElementById("departmentListId");

    switch (lang) {
        case "en":
            homeLabel.innerHTML = en.home;
            headerHomeLabelId.innerHTML = en.home;
            accountListId.innerHTML = en.accountList;
            departmentListId.innerHTML = en.departmentList;
            break;
        default:
            homeLabel.innerHTML = vi.home;
            headerHomeLabelId.innerHTML = vi.home;
            accountListId.innerHTML = vi.accountList;
            departmentListId.innerHTML = vi.departmentList;
            break;
    }
}

// function getListDepartments() {

//     var url = "http://localhost:8080/api/v1/departments";
//     // call API from server
//     $.ajax({
//         url: url,
//         type: 'GET',
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json'
//         },
    
//         beforeSend: function (xhr) {
//             xhr.setRequestHeader("Authorization", "Bearer " + storage.getItem("token"));
//         },
//         success: function (data, textStatus, xhr) {
//             // reset list employees
//             departments = data;
            
//             fillDepartmentToTable();
//             resetDeleteCheckbox();
//             pagingTable(data.totalPages);
//             renderSortUI();
//         },
//         error(jqXHR, textStatus, errorThrown) {
//             console.log(jqXHR);
//             console.log(textStatus);
//             console.log(errorThrown);
//         }
//     });
// }