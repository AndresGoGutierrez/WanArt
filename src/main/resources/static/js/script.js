let menuVisible = false;
//Función que oculta o muestra el menu
function mostrarOcultarMenu(){
    if(menuVisible){
        document.getElementById("nav").classList ="";
        menuVisible = false;
    } else {
        document.getElementById("nav").classList ="responsive";
        menuVisible = true;
    }
}

function seleccionar(){
    //Oculta el menu una vez que selecciono una opcion
    document.getElementById("nav").classList = "";
    menuVisible = false;
}

function confirmarEliminacionE(idExpo){
    if (confirm("¿Esta seguro de eliminar esta exposición? Esta acción no se puede deshacer")){
        window.location.href = "/eliminarExposicion/" + idExpo;
    }
}

function confirmarEliminacionO(idObra){
    if (confirm("¿Esta seguro de eliminar esta obra? Esta acción no se puede deshacer")){
        window.location.href = "/eliminarObra/" + idObra;
    }
}

function confirmarEliminacionA(idArtista){
    if (confirm("¿Esta seguro de eliminar este artista? Esta acción no se puede deshacer")){
        window.location.href = "/eliminarArtista/" + idArtista;
    }
}

function actualizarDimensiones() {
    var clasificacion = document.getElementById("clasificacionObra").value;
    var dimensionesSelect = document.getElementById("dimensionesObra");

    // Mostrar todas las opciones de dimensiones
    var opciones = dimensionesSelect.getElementsByTagName("option");

    // Ocultar todas las opciones
    for (var i = 0; i < opciones.length; i++) {
        opciones[i].style.display = "none";
    }

    // Mostrar solo las opciones que corresponden a la clasificación seleccionada
    for (var i = 0; i < opciones.length; i++) {
        if (opciones[i].classList.contains(clasificacion)) {
            opciones[i].style.display = "block";
        }
    }
}
