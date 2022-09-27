
let nameGreet = storage.getItem("USERNAME")



function hello(){
    $("#homeLabelId").append(
        "Hello " + nameGreet 
    )
}