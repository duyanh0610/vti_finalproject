
const langHeader = {
    "lang": storage.getItem("LANG") ? storage.getItem("LANG") : "vi"
}
const headers = {
    "Authorization": "Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")),
    "lang": langHeader.lang
}
