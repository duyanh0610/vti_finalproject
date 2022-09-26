
const langHeader = {
    "lang": localStorage.getItem("LANG") ? localStorage.getItem("LANG") : "vi"
}
const headers = {
    "Authorization": "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")),
    "lang": langHeader.lang
}

const size = 10;
let currentPage = 1;
let sortField = "id";
let isAsc = true;