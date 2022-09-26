
var departments = []
var baseUrlDepartment = "http://localhost:8080/api/v1/departments"
 currentPageDepartment = 1;
 sizePageDepartment = 10;
// sorting
sortFieldDepartment = "id";
isAscDepartment = true;
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
    // console.log(departments)
    departments.forEach(function(item, index){
        var mem = item.totalMember == null ? 0 : item.totalMember
    
        $('tbody').append(
            '<tr>' +
            '<td id = "departmentId">' + item.id + '</td>' +
            '<td id = "departmentName">' + item.name + '</td>' +
            '<td id = "departmentType">' + item.type + '</td>' +
            '<td id = "departmentTotalMember">' + mem + '</td>' +
            '<td id = "departmentCreateDate">' + item.createdDate + '</td>' +
            '<td>' +
            '<a class="edit" title="Edit" data-toggle="tooltip" onclick="openUpdateDepartmentModal('
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
function fillDepartmentPaging(numberOfElements, totalPages){
    console.log(document.getElementById("department-previousPage-btn"))
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
    resetForm();
    openModal();
}
function openUpdateDepartmentModal(id) {

    // get index from employee's id
    const index = departments.findIndex(x => x.id === id);
    console.log(departments[index]);
    // fill data
    document.getElementById("name").value = departments[index].name;
    document.getElementById("totalMember").value = departments[index].totalMember;
    document.getElementById("type").value = departments[index].type;
   
    openModal();
}
function resetForm() {
    document.getElementById("departmentName").value = "";
    document.getElementById("departmentType").value = "";
    document.getElementById("departmentTotalMember").value = "";
}
function openModal() {
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
            hideModal();
            showSuccessAlert();
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
   
    // TODO validate
    // then fail validate ==> return;

    const department = {
        id: Number(id),
        name: name,
        type: type, 
    };

    $.ajax({
        url: url + "/" + id,
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
            hideModal();
            showSuccessAlert();
            buildDepartmentTable();
        }
    });
}
function deleteDepartment(id) {
    // TODO validate
    $.ajax({
        url: url + "/" + id,
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
            buildDepartmentTable();
        }
    });
}
function openConfirmDelete(id) {
    // get index from department's id
    console.log(departments)
    const index = departments.findIndex(x => x.id === id);

    const result = confirm("Bạn có muốn xóa " + departments[index].name + " không?");
    if (result) {
        deleteDepartment(id);
    }
}
function hideModal() {
    $('#myCreateDepartmentModal').modal('hide');
}
function showSuccessAlert() {
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
function setupSearchEvent() {
    buildDepartmentTable();
}
