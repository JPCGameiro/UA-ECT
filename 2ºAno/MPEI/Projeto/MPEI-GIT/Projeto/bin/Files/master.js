//alert("Viva!")

function sendFile(file) {
    var data = new FormData();
    data.append("myFile", file);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "upload");
    xhr.upload.addEventListener("progress", updateProgress, false);
    xhr.send(data);
}

function updateProgress(evt){
    if(evt.loaded == evt.total)
        alert("New image received!");
}


function updatePhoto(event){
    var reader = new FileReader();
    reader.onload = function(event){
        //Criar uma imagem
        var img = new Image();
        img.onload = function(){
            //Colocar a imagem no ecrã
            canvas = document.getElementById("photo");
            ctx = canvas.getContext("2d");
            ctx.drawImage(img,0,0,img.width,img.height,0,0,530, 400);
        }
        img.src = event.target.result;
    }

    //Obter o ficheiro
    reader.readAsDataURL(event.target.files[0]);
    sendFile(event.target.files[0]);

    //Libertar recursos da imagem seleccionada
    windowURL.revokeObjectURL(picURL);
}
