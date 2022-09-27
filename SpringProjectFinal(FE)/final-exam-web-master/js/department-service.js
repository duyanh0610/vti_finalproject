
var departments = []
var baseUrlDepartment = "http://localhost:8080/api/v1/departments"
let  currentPageDepartment = 1;
let sizePageDepartment = 10;
// sorting
let sortFieldDepartment = "totalMember";
let isAscDepartment = false;
function Department(id, name, totalMember, type, createdDate) {
    this.id = id;
    this.name = name;
    this.totalMember = totalMember;
    this.type = type;
    this.createdDate = createdDate;
}

function buildDepartmentTable() {
    $('tbody').empty();
    getDepartments();
}       
function getDepartments() {

    let url = "http://localhost:8080/api/v1/departments"  + '?page='
        + `${currentPageDepartment - 1}` + '&size=' + sizePageDepartment
        + "&sort=" + sortFieldDepartment + "," + (isAscDepartment ? "asc" : "desc");

    const searchValueDepartment = document.getElementById("search-department-input");
    if (searchValueDepartment?.value) {
        url += "&search.contains=" + searchValueDepartment.value;
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
            parseDepartmentData(data);
            fillDepartmentToTable();
            fillDepartmentPaging(data.numberOfElements, data.totalPages);
            fillDepartmentSorting();
        },
        error(jqXHR, textStatus, errorThrown) {
            // TODO
            alert("Error when loading data");
        }
    });
}
function parseDepartmentData(data){
    departments =[]
    data.content.forEach(function(item, index){
        departments.push(new Department(
            item.id, item.name,
            item.totalMember, item.type, 
            item.createdDate))

    })
    
   
}
function fillDepartmentToTable(){
    departments.forEach(function(item, index){
        var mem = item.totalMember == null ? 0 : item.totalMember
    
        $('tbody').append(
            '<tr>' +
            '<td style="width:45px;">' +
                '<span style="department-checkbox">' +
                    '<input type="checkbox" id="checkbox-' + index + '" type ="checkbox" onclick="onChangeDepartmentCheckboxItem()">' +
                '</span>' +
            '</td>'+
            '<td id = "departmentId">' + item.id + '</td>' +
            '<td id = "departmentName">' + item.name + '</td>' +
            '<td id = "departmentType">' + item.type + '</td>' +
            '<td id = "departmentTotalMember">' + mem + '</td>' +
            '<td id = "departmentCreatedcDate">' + item.createdDate + '</td>' +
            '<td>' +
            '<a class="edit" title="Edit" data-toggle="tooltip" onclick="openUpdateDepartmentModal('
            + item.id + ')">'
            + '<i class="material-icons">&#xE254;</i>'
            + '</a>' +
            '<a class="delete" title="Delete" data-toggle="tooltip" onclick="openConfirmDeleteDepartment('
            + item.id + ')">'
            + '<i class="material-icons">&#xE872;</i>'
            + '</a>' +
            '<a class="add" title="Add" data-toggle="tooltip" onclick="openAddAccount('
            + item.id + ')">'
            + '<i class="material-icons">&#xE872;</i>'
            + '</a>' +
            '</td>' +
            '</tr>'
        )
    })
}
function fillDepartmentPaging(numberOfElements, totalPages){
    
    if (currentPageDepartment > 1) {
        document.getElementById("department-previousPage-btn").disabled = false;
    } else {
        document.getElementById("department-previousPage-btn").disabled = true;
    }

    // next
    if (currentPageDepartment < totalPages ) {
        document.getElementById("department-nextPage-btn").disabled = false;
    } else {
        document.getElementById("department-nextPage-btn").disabled = true;
    }

    // text
    document.getElementById("department-page-info").innerHTML = numberOfElements
        + " phòng ban của trang " + currentPageDepartment + " / " + totalPages;

}
function fillDepartmentSorting(){
    const sortTypeClazz = isAscDepartment ? "fa-sort-up" : "fa-sort-down";
    const defaultSortType = "fa-sort";

    switch (sortFieldDepartment) {
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
function changeDepartmentSort(field) {
    if (field === sortFieldDepartment) {
        isAscDepartment = !isAscDepartment;
    } else {
        sortFieldDepartment = field;
        isAscDepartment = true;
    }
    buildDepartmentTable();
}
function changeIdSort(field) {
    if (field === sortFieldDepartment) {
        isAscDepartment = !isAscDepartment;
    } else {
        sortFieldDepartment = field;
        isAscDepartment = true;
    }
    buildDepartmentTable();
}
function prevDepartmentPage() {
    changeDepartmentPage(currentPageDepartment - 1);
}
function nextDepartmentPage() {
    changeDepartmentPage(currentPageDepartment + 1);
}
function changeDepartmentPage(page) {
    currentPageDepartment = page;
    buildDepartmentTable();
}
function openCreateDepartmentModal() {
    resetFormDepartment();
    openModalDepartment();
}
function openUpdateDepartmentModal(id) {

    // get index from employee's id
    const index = departments.findIndex(x => x.id === id);
    
    // fill data
    document.getElementById("name").value = departments[index].name;
    document.getElementById("totalMember").value = departments[index].totalMember;
    document.getElementById("type").value = departments[index].type;
   
    openModalDepartment();
}
function resetFormDepartment() {
    document.getElementById("name").value = "";
    document.getElementById("type").value = "";
    document.getElementById("totalMember").value = "";
}
function openModalDepartment() {
    $('#myCreateDepartmentModal').modal('show');
}
function createDepartment() {
    // get data
    let name = document.getElementById("name").value;
    let totalMember = document.getElementById("totalMember").value;
    let type = document.getElementById("type").value;
    
  
    // TODO validate
    // then fail validate ==> return;

    const department = {
        name: name,
        totalMember: totalMember,
        type: type
    };

    $.ajax({
        url: baseUrlDepartment,
        type: 'POST',
        data: JSON.stringify(department), // body
        contentType: "application/json", // type of body (json, xml, text)
        headers: headers,
        success: function (data, textStatus, xhr) {
            hideModalDepartment();
            showSuccessAlertDepartment();
            buildDepartmentTable();
        },
        error(jqXHR, textStatus, errorThrown) {
            alert("Có lỗi khi tạo phòng ban");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}
function updateDepartment() {
    const id = document.getElementById("id").value;
    const name = document.getElementById("name").value;
    const type = document.getElementById("type").value;
    const totalMember = document.getElementById("totalMember").value;
   
    // TODO validate
    // then fail validate ==> return;

    const department = {
        id: Number(id),
        name: name,
        type: type, 
        totalMember: totalMember
    };

    $.ajax({
        url: baseUrlDepartment + "/" + id,
        type: 'PUT',
        data: JSON.stringify(department),
        contentType: "application/json", // type of body (json, xml, text)
        headers: headers,
        success: function (result) {
            // error
            if (!result) {
                alert("Có lỗi khi cập nhật phòng ban");
                return;
            }

            // success
            hideModalDepartment();
            showSuccessAlertDepartment();
            buildDepartmentTable();
        }
    });
}
function deleteDepartment(id) {
    // TODO validate
    $.ajax({
        url: baseUrlDepartment + "/" + id,
        type: 'DELETE',
        headers: headers,
        success: function (result) {
            // error
            if (!result) {
                alert("Có lỗi khi xóa tài khoản");
                return;
            }

            // success
            showSuccessAlertDepartment();
            buildDepartmentTable();
        }
    });
}
function openConfirmDeleteDepartment(id) {
    // get index from department's id
   
    const index = departments.findIndex(x => x.id === id);

    const result = confirm("Bạn có muốn xóa " + departments[index].name + " không?");
    if (result) {
        deleteDepartment(id);
    }
}
function hideModalDepartment() {
    $('#myCreateDepartmentModal').modal('hide');
}
function showSuccessAlertDepartment() {
    $("#success-alert").fadeTo(2000, 500).slideUp(500, function () {
        $("#success-alert").slideUp(500);
    });
}
function saveDepartment() {
    const id = document.getElementById("id").value;

    if (!id) {
        createDepartment();
    } else {
        updateDepartment();
    }
}
function setupSearchDeaprtmentEvent() {
    buildDepartmentTable();
}


function onChangeDepartmentCheckboxAll() {
    var i = 0;
    while (true) {
        var checkboxItem = document.getElementById("checkbox-" + i);
        if (checkboxItem !== undefined && checkboxItem !== null) {
            // checkboxItem.checked = document.getElementById("checkbox-all").checked
                if (document.getElementById("checkbox-all").checked) {
                    checkboxItem.checked = true;
                } else {
                    checkboxItem.checked = false;
                }
            i++;
        } else {
            break;
        }
    }
}
function onChangeDepartmentCheckboxItem() {
    var i = 0;
    while (true) {
        var checkboxItem = document.getElementById("checkbox-" + i);
        if (checkboxItem !== undefined && checkboxItem !== null) {
            if (!checkboxItem.checked) {
                document.getElementById("checkbox-all").checked = false;
                return;
            }
            i++;
        } else {
            break;
        }
    }
    document.getElementById("checkbox-all").checked = true;
}

function showDeleteMultipleDepartmentsModal() {
    $('#deleteMultipleAccountsModal').modal('show');

    // get checked
    var ids = [];
    
    var i = 0;
    while (true) {
        var checkboxItem = document.getElementById("checkbox-" + i);
        if (checkboxItem !== undefined && checkboxItem !== null) {
            if (checkboxItem.checked) {
                ids.push(departments[i].id);
                
            }
            i++;
        } else {
            break;
        }
    }

    var result = confirm("Are you sure you want to delete");
    if(result){
        $.ajax({
            url: 'http://localhost:8080/api/v1/departments?ids=' + ids,
            type: 'DELETE',
            headers: headers,
            success: function(result) {
            //     // error
            //     if (result == undefined || result == null) {
            //         alert("Error when loading data");
            //         return;
            //     }
    
            //     // success
            //     showSuccessSnackBar("Success! Account deleted.");
            //     $('#deleteMultipleAccountsModal').modal('hide');
            refreshDeparmtentTable();
            }
        });
    }
}

function filterDepartment() {
    buildAccountTable();
}

function refreshDeparmtentTable() {
    // refresh paging
    currentPageDepartment = 1;
    // size = 10;

    // refresh sorting
    sortFieldDepartment = "id";
    isAscDepartment = false;

    document.getElementById("checkbox-all").checked = false
    // refresh search
    document.getElementById("search-department-input").value = "";

    // refresh filter
    // $("#filter-department-select").empty();
    // $('#filter-role-select').val('').trigger('change');

    // Get API
    buildDepartmentTable();
}

