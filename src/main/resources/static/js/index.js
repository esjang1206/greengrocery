
$(document).ready(function() {
   getList();
});

var msg = {1 : "이름을 선택해주세요",
           2 : "CONNECTION FAILED !"};

function getList(){
    var selected = $("#category option:selected").val();
     $('#kinds').empty();
     $('#priceArea').empty();
    if(selected === "fruit"){
        getFruitList();
    } else {
        getVegetableList();
    }
}

function getPrice(){
    var selected = $("#category option:selected").val();
    var selectedOfKinds = $("#kinds option:selected").val();
    $('#priceArea').empty();

    if(selected === "fruit"){
        getFruitPrice(selectedOfKinds);
    } else {
        getVegetablePrice(selectedOfKinds);
    }
}

function getFruitList(){
    $.ajax({
        type : "GET",
        url : "/fruit/list",
        success : function(res){
            for(var i = 0; i < res.length; i++){
                var option = $("<option>"+res[i]+"</option>");
                $('#kinds').append(option);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(msg[2]);
        }
    });
}
function getVegetableList(){
    $.ajax({
        type : "GET",
        url : "/vegetable/list",
        success : function(res){
            for(var i = 0; i < res.length; i++){
                var option = $("<option>"+res[i]+"</option>");
                $('#kinds').append(option);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(msg[2]);
        }
    });
}

function getFruitPrice(selectedOfKinds){
    $.ajax({
        type : "GET",
        url : "/fruit/price",
        data : { "name" : selectedOfKinds},
        success : function(res){
            if(res.success){
                var price = $("<div> 가격 : " + JSON.parse(res.data).price + "</div>");
                $('#priceArea').append(price);
            } else {
                alert(msg[res.errorCode]);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(msg[2]);
        }
    });
}
function getVegetablePrice(selectedOfKinds){
    $.ajax({
        type : "GET",
        url : "/vegetable/price",
        data : { "name" : selectedOfKinds},
        success : function(res){
            $('#priceArea').append(price);
            if(res.success){
                var price = $("<div> 가격 : " + JSON.parse(res.data).price + "</div>");
                $('#priceArea').append(price);
            } else {
                alert(msg[res.errorCode]);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(msg[2]);
        }
    });
}