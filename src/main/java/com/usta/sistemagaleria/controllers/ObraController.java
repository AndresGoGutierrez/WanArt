package com.usta.sistemagaleria.controllers;

import com.usta.sistemagaleria.entities.ArtistaEntity;
import com.usta.sistemagaleria.entities.ObraEntity;
import com.usta.sistemagaleria.models.services.ArtistaService;
import com.usta.sistemagaleria.models.services.ExposicionService;
import com.usta.sistemagaleria.models.services.ObraService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//******************************
import java.util.Map;
import java.util.stream.Collectors;
//*************

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ObraController {
    @Autowired
    private ObraService obraService;
    @Autowired
    private ArtistaService artistaService;
    @Autowired
    private ExposicionService exposicionService;

    @GetMapping(value = "/filtroObra")
    public String listarO(Model model) {
        model.addAttribute("title", "Listado de obras");
        model.addAttribute("obras", obraService.findAll());
        return "obras/clasificacionObra";
    }

    // *********************************************************** //
    @RequestMapping(value = "/clasificacionObra/{clasificacion}")
    public String listarPorClasificacion(Model model,
                                         @PathVariable("clasificacion") String clasificacion,
                                         @RequestParam(value = "filterType", required = false)String filterType,
                                         @RequestParam(value = "searchTerm", required = false)String searchTerm) {
//        // Obtener todas las obras
//        List<ObraEntity> obras = obraService.findAll();
//
//        // Filtrar las obras por la clasificación pasada en la URL
//        List<ObraEntity> obrasFiltradas = obras.stream()
//                .filter(obra -> obra.getClasificacionObra().equalsIgnoreCase(clasificacion)) // Filtra por clasificacion, ignorando mayúsculas/minúsculas
//                .collect(Collectors.toList());
//
//        model.addAttribute("obras", obrasFiltradas);
//        model.addAttribute("title", "Listado de obras");
//        return "obras/listarObra";

        model.addAttribute("urlRegistro", "/crearObra");

        List<ObraEntity> obrasL = obraService.findAll();
        List<ObraEntity> obrasAuxiliar = new ArrayList<>();
        for (ObraEntity obraE : obrasL) {
            if (clasificacion.equals("pintura") || clasificacion == "pintura") {
                if (obraE.getClasificacionObra() == "pintura" || obraE.getClasificacionObra().equals("pintura")) {
                    obrasAuxiliar.add(obraE);
                }
                model.addAttribute("title", "Pintura");
            } else if (clasificacion.equals("dibujo") || clasificacion == "dibujo") {
                if (obraE.getClasificacionObra() == "dibujo" || obraE.getClasificacionObra().equals("dibujo")) {
                    obrasAuxiliar.add(obraE);
                }
                model.addAttribute("title", "Dibujo");
            } else if (clasificacion.equals("fotografia") || clasificacion == "fotografia") {
                if (obraE.getClasificacionObra() == "fotografia" || obraE.getClasificacionObra().equals("fotografia")) {
                    obrasAuxiliar.add(obraE);
                }
                model.addAttribute("title", "Fotografía");
            }
        }

        if (searchTerm != null && !searchTerm.isEmpty()) {
            if ("obra".equals(filterType)) {
                obrasAuxiliar = obraService.findByObraTitulo(searchTerm);
            } else if ("artista".equals(filterType)) {
                obrasAuxiliar = obraService.findByArtistaNombre(searchTerm);
            } else if ("anno".equals(filterType)) {
                try {
                    long anno = Long.parseLong(searchTerm); // Conversión segura
                    obrasAuxiliar = obraService.findByObraAnno(anno);
                } catch (NumberFormatException e) {
                    obrasAuxiliar = new ArrayList<>(); // Evitar que devuelva resultados
                    model.addAttribute("error", "El año debe ser un número válido.");
                }
            } else if ("dimension".equals(filterType)) {
                obrasAuxiliar = obraService.findByObraDimension(searchTerm);
            }
        }

  //      model.addAttribute("obrasF", obrasF);
        model.addAttribute("filterType", filterType);
        model.addAttribute("searchTerm", searchTerm);


        model.addAttribute("clasificacion", clasificacion);
        model.addAttribute("obras", obrasAuxiliar);
        return "obras/listarObra";
    }

    // *********************************************************** //

    @RequestMapping(value = "/detalleObra/{id}")
    public String detalleObra(Model model, @PathVariable(value = "id") Long idObra) {
        model.addAttribute("title", "Obra");
        model.addAttribute("detalleO", obraService.viewDetail(idObra));
        model.addAttribute("artistas", artistaService.findByArtistaIdObra(idObra));
        model.addAttribute("exposiciones", exposicionService.findByObrasByExposicion(idObra));
        return "obras/detalleObra";
    }

    // *********************************************************** //


    @GetMapping(value = "/crearObra")
    public String ListarFormObra(Model model) {
        List<ArtistaEntity> listaArtistas = artistaService.findAll();
        if (listaArtistas == null) {
            listaArtistas = new ArrayList<>();
        }

        model.addAttribute("title", "Registrar Obra");
        model.addAttribute("artistas", listaArtistas);
        model.addAttribute("obra", new ObraEntity());
        return "obras/crearObra";
    }

    /*------------------------------------------------------------------------- */
    @PostMapping(value = "/crearObra")
    public String guardarObra(@Valid ObraEntity obra,
                              @RequestParam(value = "foto") MultipartFile foto,
                              BindingResult result) {

        if (result.hasErrors()) {
            return "error500";
        }

        String nombreImagen = guardarImagen(foto);
        obra.setFotoObra(nombreImagen);
        obraService.save(obra);
        return "redirect:/filtroObra";
    }

    /*------------------------------------------------------------------------- */

    private String guardarImagen(MultipartFile imagen) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.imgbb.com/1/upload");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("key", "4ba86690d1318c201abdbb8d2b72bfce", ContentType.TEXT_PLAIN);
            builder.addBinaryBody("image", imagen.getInputStream(),
                    ContentType.DEFAULT_BINARY, imagen.getOriginalFilename());
            HttpEntity multipart = (HttpEntity) builder.build();
            httpPost.setEntity(multipart);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                String resposeString = EntityUtils.toString(responseEntity);
                JSONObject jsonResponse = new JSONObject(resposeString);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    JSONObject data = jsonResponse.getJSONObject("data");
                    String imageUrl = data.getString("url");
                    return imageUrl;
                }else {
                    String errorMessage = jsonResponse.optString("Error desconocido");
                    System.out.println("Error al cargar la imagen ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*------------------------------------------------------------------------- */


    @GetMapping(value = "/editarObra/{id}")
    public String editarObra(Model model, @PathVariable("id") Long idObra) {
        model.addAttribute("title", "Editar Obra");
        model.addAttribute("obra", obraService.findById(idObra));
        model.addAttribute("imagen", obraService.viewDetail(idObra).getFotoObra());
        List<ArtistaEntity> artista = artistaService.findAll();
        if (artista == null) {
            artista = new ArrayList<>();
        }
        model.addAttribute("artistas", artista);
        return "obras/editarObra";
    }

    /*------------------------------------------------------------------------- */

    @PostMapping(value = "/editarObra/{id}")
    public String editObra(@ModelAttribute("obraEdit") ObraEntity obraEntity,
                           @PathVariable(value = "id") Long idObra,
                           @RequestParam(value = "foto") MultipartFile foto,
                           BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "error500";
        }
        ObraEntity obraAuxiliar = obraService.findById(idObra);
        obraAuxiliar.setIdObra(idObra);
        obraAuxiliar.setTituloObra(obraEntity.getTituloObra());
        obraAuxiliar.setClasificacionObra(obraEntity.getClasificacionObra());
        obraAuxiliar.setDescripcionObra(obraEntity.getDescripcionObra());
        obraAuxiliar.setAnoCreacion(obraEntity.getAnoCreacion());
        obraAuxiliar.setDimensionesObra(obraEntity.getDimensionesObra());
        obraAuxiliar.setIdArtista(obraEntity.getIdArtista());

        String imagen = obraAuxiliar.getFotoObra();
        String nombreImagen = guardarImagen(foto);
        if (nombreImagen.isBlank() || nombreImagen.isEmpty() || nombreImagen == null) {
            obraAuxiliar.setFotoObra(imagen);
        } else {
            obraAuxiliar.setFotoObra(nombreImagen);
        }
        obraService.actualizarObraEntity(obraAuxiliar);
        return "redirect:/filtroObra";
    }

    /*------------------------------------------------------------------------- */

    @GetMapping
    public String indexlindoUwU(Model model) {
        List<ObraEntity> todasObras = obraService.findAll();
        List<ObraEntity> obrasOrdenadas = todasObras.stream().sorted((o1, o2) -> Long.compare(o2.getAnoCreacion(), o1.getAnoCreacion())).toList();
        List<ObraEntity> obrasRecientesSuperior = new ArrayList<>();
        List<ObraEntity> obrasRecientesInferior = new ArrayList<>();
        Integer contador = 1;
        for (ObraEntity obraE : obrasOrdenadas) {
            if (contador <= 3) {
                obrasRecientesSuperior.add(obraE);
                contador = contador + 1;
            } else if (contador>3 & contador <=6) {
                obrasRecientesInferior.add(obraE);
                contador = contador + 1;
            }
        }
        model.addAttribute("obrasSup", obrasRecientesSuperior);
        model.addAttribute("obrasInf", obrasRecientesInferior);

        //*********************************************
        // contar las obras por artista
        Map<ArtistaEntity, Long> conteoObrasPorArtista = todasObras.stream().filter(obra -> obra.getIdArtista() != null).collect(Collectors.groupingBy(ObraEntity::getIdArtista, Collectors.counting()));

        //Ordenar los artistas
        List<Map.Entry<ArtistaEntity, Long>> artistasOrdenados = conteoObrasPorArtista.entrySet().stream().sorted((a1, a2) -> Long.compare(a2.getValue(), a1.getValue())).limit(4).toList();
        model.addAttribute("artistaPop", artistasOrdenados);

        return "index";
    }

    /*---------------------------------VEREMOS----------------------------------------*/
    @RequestMapping(value = "/eliminarObra/{id}")
    public String eliminarObra(Model model, @PathVariable(value = "id") long id) throws IOException {
        if (id>0){
            ObraEntity obra = obraService.findById(id);
            if (obra != null) {
                obra.getExposiciones().clear();
                obraService.save(obra);
                obraService.deleteById(id);
            }
        }else{
            return "redirect:/error500";
        }
        return "redirect:/filtroObra";
    }

}
